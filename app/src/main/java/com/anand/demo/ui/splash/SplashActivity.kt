package com.anand.demo.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.anand.demo.R
import com.anand.demo.databinding.ActivitySplashBinding
import com.anand.demo.prefrences.PrefrenceManager
import com.anand.demo.ui.home.HomeActivity
import com.anand.demo.ui.login.LoginActivity
import com.anand.demo.utils.SPLASH_DELAY
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var viewmodel: SplashViewModel

    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        viewmodel = ViewModelProvider(this).get(SplashViewModel::class.java)
        init()
    }

    fun init() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            val prefManager = PrefrenceManager(applicationContext)

            if (prefManager.validateSession()) {

                val i = Intent(this@SplashActivity, HomeActivity::class.java)
                startActivity(i)
                finish()

            } else {
                val i = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(i)
                finish()
            }

        }, SPLASH_DELAY.toLong())

    }

}