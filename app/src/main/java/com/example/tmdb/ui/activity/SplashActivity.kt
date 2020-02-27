package com.example.tmdb.ui.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.widget.Toast
import com.example.splashscreen.RotateView
import com.example.tmdb.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        av_from_code.setAnimation("screenmovie.json")
        av_from_code.playAnimation()
        av_from_code.loop(true)

        ObjectAnimator.ofFloat(buttonAnimated, "translationY", -120f).apply {
            duration = 2000
            start()
        }

        val tvAppTitle = RotateView.generateRotateValueAnimatorHorizontal(tvAppTitle)
        tvAppTitle.start()

        tvAppTitle.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
//                startActivity(Intent(applicationContext,MainActivity::class.java))
//                finish()
            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })

        //Animation Image
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.interpolator = DecelerateInterpolator() //add this
        fadeIn.duration = 6000

//        val fadeOut = AlphaAnimation(1f, 0f)
//        fadeOut.interpolator = AccelerateInterpolator() //and this
//        fadeOut.startOffset = 3000
//        fadeOut.duration = 3000

        val animation = AnimationSet(false) //change to false
        animation.addAnimation(fadeIn)
//        animation.addAnimation(fadeOut)
        ivImageAnimated.animation = animation
        ObjectAnimator.ofFloat(ivImageAnimated, "translationY", -200f).apply {
            duration = 6000
            start()
        }

        ivImageAnimated.setOnClickListener {
            recreate()
        }

    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(applicationContext, "Paused", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(applicationContext, "Stoped", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext, "Destroied", Toast.LENGTH_SHORT).show()

    }
}

