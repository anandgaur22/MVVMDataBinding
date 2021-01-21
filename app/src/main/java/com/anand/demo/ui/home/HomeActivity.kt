package com.anand.demo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.anand.demo.R
import com.anand.demo.databinding.ActivityHomeBinding
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    @Inject
    lateinit var viewmodel: HomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        viewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.vm = viewmodel

        init()
    }

    fun init() {

        binding.btnResult.setOnClickListener {
            val n1: String? = binding.n1Et.text?.toString()
            val n2: String? = binding.n2Et.text?.toString()

            if (n1?.isEmpty()!!) {
                binding.n1Et.setError("Number 1 should not be blank")
                binding.n1Et.requestFocus()

            } else if (n2?.isEmpty()!!) {
                binding.n2Et.setError("Number 2 should not be blank")
                binding.n2Et.requestFocus()

            } else {

                var first = n1
                var second = n2

                val temporary = first
                // Value of second is assigned to first
                first = second
                // Value of temporary (which contains the initial value of first) is assigned to second
                second = temporary

                println("--Before swap--")
                println("First number = $first")
                println("Second number = $second")

                println("--After swap--")
                println("First number = $first")
                println("Second number = $second")

                binding.afterResult.text="First number = $first"+"\n"+"Second number = $second"

            }
        }

    }
}