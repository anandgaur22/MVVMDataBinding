package com.anand.demo.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.anand.demo.R
import com.anand.demo.databinding.ActivityLoginBinding
import com.anand.demo.prefrences.PrefrenceManager
import com.anand.demo.ui.home.HomeActivity
import com.anand.demo.ui.signup.SignupActivity
import com.anand.demo.utils.CustomeProgressDialog
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var prefrenceManager: PrefrenceManager? = null

    @Inject
    lateinit var viewmodel: LoginViewModel
    var customeProgressDialog: CustomeProgressDialog? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewmodel = ViewModelProvider(this).get(LoginViewModel::class.java)
        binding.vm = viewmodel

        init()
    }

    fun init() {
        prefrenceManager = PrefrenceManager(applicationContext)

        customeProgressDialog = CustomeProgressDialog(this)
        initObservables()


        binding.createAccount.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        }


        binding.btnLogin.setOnClickListener {
            val email: String? = binding.emailEt.text?.toString()
            val password: String? = binding.passwordEt.text?.toString()

            if (email?.isEmpty()!!) {
                binding.emailEt.setError("Phone should not be blank")
                binding.emailEt.requestFocus()

            } else if (password?.isEmpty()!!) {
                binding.passwordEt.setError("Password should not be blank")
                binding.passwordEt.requestFocus()

            } else {
                viewmodel.login(email, password)
            }
        }
    }

    private fun initObservables() {
        viewmodel.progressDialog?.observe(this, Observer {
            if (it!!) customeProgressDialog?.show() else customeProgressDialog?.dismiss()

        })


        viewmodel.userLogin?.observe(this, Observer { user ->

            Log.e("responce==", user.toString())

            if (user.getString("status").equals("true")) {

                val msg = user.getString("message")

                val jsonObject = user.getJSONObject("login_data")

                val fld_user_name = jsonObject.getString("fld_user_name")
                val fld_user_email = jsonObject.getString("fld_user_email")
                val fld_user_phone = jsonObject.getString("fld_user_phone")
                val fld_user_id = jsonObject.getString("fld_user_id")

                prefrenceManager?.saveResponseDetails(
                        fld_user_name,
                        fld_user_email,
                        fld_user_phone,
                        fld_user_id)

                prefrenceManager?.saveSessionLogin()
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)

                val toast = Toast.makeText(applicationContext, "Login Sucessfully", Toast.LENGTH_SHORT)
                toast.show()

            } else {
                val msg = user.getString("message")

                val toast = Toast.makeText(applicationContext, "" + msg, Toast.LENGTH_SHORT)
                toast.show()
            }
        })
    }
}