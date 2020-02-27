package com.example.splashscreen

import android.animation.ValueAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator

object RotateView {

    fun generateRotateValueAnimatorVertical(animatedView: View) : ValueAnimator {

        // construct the value animator and define the range
        val valueAnimator = ValueAnimator.ofFloat(0f, 360f)

        //repeats the animation 2 times
        valueAnimator.repeatCount = 1

        // increase the speed first and then decrease
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()

        // animate over the course of 700 milliseconds
        valueAnimator.duration = 3000

        // define how to update the view at each "step" of the animation
        valueAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            animatedView.rotationX = progress
        }
        return valueAnimator
    }

    fun generateRotateValueAnimatorHorizontal(animatedView: View) : ValueAnimator {

        // construct the value animator and define the range. This value is set in degrees
        val valueAnimator = ValueAnimator.ofFloat(0f, 360f)

        //repeats the animation 2 times
        valueAnimator.repeatCount = 1

        // increase the speed first and then decrease
        valueAnimator.interpolator = AccelerateDecelerateInterpolator()

        // animate over the course of 700 milliseconds
        valueAnimator.duration = 2400

        // define how to update the view at each "step" of the animation
        valueAnimator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Float
            animatedView.rotationY = progress
        }
        return valueAnimator
    }
}
