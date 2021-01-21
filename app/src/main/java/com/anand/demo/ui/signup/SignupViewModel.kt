package com.anand.demo.ui.signup

import android.app.Application
import android.util.ArrayMap
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anand.demo.network.BackEndApi
import com.anand.demo.network.WebServiceClient
import com.anand.demo.utils.SingleLiveEvent
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject


class SignupViewModel(application: Application) : AndroidViewModel(application) {


    var btnSelected: ObservableBoolean? = null
    var name: ObservableField<String>? = null
    var mobile: ObservableField<String>? = null
    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var userSignup: MutableLiveData<JSONObject>? = null
    var userSignupError: MutableLiveData<JSONObject>? = null


    init {
        btnSelected = ObservableBoolean(false)
        progressDialog = SingleLiveEvent<Boolean>()
        name = ObservableField("")
        mobile = ObservableField("")
        email = ObservableField("")
        password = ObservableField("")
        userSignup = MutableLiveData()
        userSignupError = MutableLiveData<JSONObject>()
    }

    fun signup(device_id: String, user_name: String, email: String, phone: String, password: String) {

        progressDialog?.value = true

        val service = WebServiceClient.client.create(BackEndApi::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("fld_device_id", device_id)
        jsonObject.put("fld_user_name", user_name)
        jsonObject.put("fld_user_email", email)
        jsonObject.put("fld_user_phone", phone)
        jsonObject.put("fld_user_password", password)

        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        Log.d("TAG", "signup: " + jsonObject.toString())


        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.Signup(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val jsonObj = JSONObject(response.body()!!.string())
                    progressDialog?.value = false
                    userSignup?.value = jsonObj

                } else {
                    val gson = JsonParser().parse(response.errorBody().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    userSignupError?.value = jsonObj
                    progressDialog?.value = false
                    Log.e("RETROFIT_ERROR", response.code().toString())

                }

            }
        }
    }
}