package com.example.tmdb.ui.activity

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.INVISIBLE
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
            duration = 1000
            start()
        }

        val tvAppTitle = RotateView.generateRotateValueAnimatorHorizontal(tvAppTitle)
        tvAppTitle.start()

        tvAppTitle.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(animation: Animator?) {
            }
            override fun onAnimationEnd(animation: Animator?) {
                av_from_code.visibility = INVISIBLE
                startActivity(Intent(applicationContext,MainActivity::class.java))
                finish()

            }
            override fun onAnimationCancel(animation: Animator?) {
            }
            override fun onAnimationStart(animation: Animator?) {
            }
        })

        //Animation viewa
        val fadeInImage = AlphaAnimation(0f, 1f)
        fadeInImage.interpolator = DecelerateInterpolator() //add this
        fadeInImage.duration = 2000

        val fadeInButton = AlphaAnimation(0f, 1f)
        fadeInButton.interpolator = DecelerateInterpolator() //add this
        fadeInButton.duration = 1000
        fadeInButton.startOffset = 2000

        val fadeOutIcon = AlphaAnimation(1f, 0f)
        fadeOutIcon.interpolator = AccelerateInterpolator() //and this
        fadeOutIcon.startOffset = fadeInImage.duration
        fadeOutIcon.duration = 1000

        val animation = AnimationSet(false) //change to false
        animation.addAnimation(fadeInImage)

        val animationButton = AnimationSet(false)
        animationButton.addAnimation(fadeInButton)

        val animationIcon = AnimationSet(false)
        animationIcon.addAnimation(fadeOutIcon)

//        Start animations
        ivImageAnimated.animation = animation
        buttonAnimated.animation = animationButton
        av_from_code.animation = animationIcon

        ObjectAnimator.ofFloat(ivImageAnimated, "translationY", -150f).apply {
            duration = 2000
            start()
        }

        ivImageAnimated.setOnClickListener {
            recreate()
        }
    }
}

