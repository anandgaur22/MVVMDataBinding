package com.anand.demo.ui.login

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.anand.demo.network.BackEndApi
import com.anand.demo.network.WebServiceClient
import com.anand.demo.utils.SingleLiveEvent
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    var btnSelected: ObservableBoolean? = null
    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var progressDialog: SingleLiveEvent<Boolean>? = null
    var userLogin: MutableLiveData<JSONObject>? = null
    var userLoginError: MutableLiveData<JSONObject>? = null


    init {
        btnSelected = ObservableBoolean(false)
        progressDialog = SingleLiveEvent<Boolean>()
        email = ObservableField("")
        password = ObservableField("")
        userLogin = MutableLiveData()
        userLoginError = MutableLiveData<JSONObject>()
    }

    fun login(email: String, password: String) {

        progressDialog?.value = true

        val service = WebServiceClient.client.create(BackEndApi::class.java)

        val jsonObject = JSONObject()
        jsonObject.put("fld_user_phone", email)
        jsonObject.put("fld_user_password", password)

        val jsonObjectString = jsonObject.toString()

        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        Log.d("TAG", "login: " + jsonObject.toString())


        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.Login(requestBody)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val jsonObj = JSONObject(response.body()!!.string())
                    progressDialog?.value = false
                    userLogin?.value = jsonObj

                } else {
                    val gson = JsonParser().parse(response.errorBody().toString()).asJsonObject
                    val jsonObj = JSONObject(gson.toString())
                    userLoginError?.value = jsonObj
                    progressDialog?.value = false

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }

            }
        }
    }
}


