package com.shbj.customview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

//    private fun funQQView() {
//        qqView.setMaxStep(3000)
//        val animator = ValueAnimator.ofInt(0, 3000)
//        animator.setDuration(3000)
//        animator.interpolator = DecelerateInterpolator()
//        animator.addUpdateListener {
//            var value: Int = it.animatedValue as Int
//            qqView.setCurrentStep(value)
//        }
//        animator.start()
//    }
}
