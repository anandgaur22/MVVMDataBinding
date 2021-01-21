package com.anand.demo.utils

import android.R.attr.duration
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import com.anand.demo.R
import kotlinx.android.synthetic.main.dialog_progress.*


class CustomeProgressDialog(context: Context?) : Dialog(context!!) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_progress)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        val rotate = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        rotate.duration = 1000
        rotate.repeatCount = Animation.INFINITE
        iv_logo.startAnimation(rotate)
    }

}
