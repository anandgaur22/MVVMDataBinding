package com.anand.demo.ui.signup

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.Settings.Secure
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anand.demo.R
import com.anand.demo.databinding.ActivitySignupBinding
import com.anand.demo.prefrences.PrefrenceManager
import com.anand.demo.utils.CustomeProgressDialog
import javax.inject.Inject


class SignupActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignupBinding
    private var prefrenceManager: PrefrenceManager? = null

    @Inject
    lateinit var viewmodel: SignupViewModel
    var customeProgressDialog: CustomeProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewmodel = ViewModelProvider(this).get(SignupViewModel::class.java)
        binding.vm = viewmodel

        init()
    }

    @SuppressLint("HardwareIds")
    fun init() {
        prefrenceManager = PrefrenceManager(applicationContext)
        val m_deviceId = Secure.getString(contentResolver, Secure.ANDROID_ID)

        customeProgressDialog = CustomeProgressDialog(this)
        initObservables()


        binding.btnRegister.setOnClickListener {


            val name: String? = binding.nameEt.text?.toString()
            val mobile: String? = binding.mobileEt.text?.toString()
            val email: String? = binding.emailEt.text?.toString()
            val password: String? = binding.passwordEt.text?.toString()

            if (name?.isEmpty()!!) {
                binding.nameEt.setError("Name should not be blank")
                binding.nameEt.requestFocus()

            } else if (mobile?.isEmpty()!!) {
                binding.mobileEt.setError("Phone Number should not be blank")
                binding.mobileEt.requestFocus()

            } else if (email?.isEmpty()!!) {
                binding.emailEt.setError("Email should not be blank")
                binding.emailEt.requestFocus()

            } else if (password?.isEmpty()!!) {
                binding.passwordEt.setError("Password should not be blank")
                binding.passwordEt.requestFocus()

            } else {
                viewmodel.signup(m_deviceId, name, mobile, email, password)
            }

        }


    }

    private fun initObservables() {
        viewmodel.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()

        })


        viewmodel.userSignup?.observe(this, Observer { user ->

            Log.e("responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")

                val jsonObject = user.getJSONObject("signup_data")

                val fld_user_name = jsonObject.getString("fld_user_name")
                val fld_user_email = jsonObject.getString("fld_user_email")
                val fld_user_phone = jsonObject.getString("fld_user_phone")
                val fld_user_id = jsonObject.getString("fld_user_id")

                val toast = Toast.makeText(applicationContext, "Register Sucessfully", Toast.LENGTH_SHORT)
                toast.show()


                onBackPressed()


            } else {
                val msg = user.getString("message")
                val toast = Toast.makeText(applicationContext, ""+msg, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
}