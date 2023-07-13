package com.ys.ysmvi.tools

import android.content.Context
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ys.ysmvi.R

object YsViewAnimation {
    enum class AnimationStatus {
        OnAnimationStart, OnAnimationEnd, OnAnimationRepeat
    }

    private fun getAnimationListener(result: ((AnimationStatus) -> Unit)?): Animation.AnimationListener {
        return object: Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { result?.let { it(AnimationStatus.OnAnimationStart) }  }
            override fun onAnimationEnd(animation: Animation?) { result?.let { it(AnimationStatus.OnAnimationEnd) }  }
            override fun onAnimationRepeat(animation: Animation?) { result?.let { it(AnimationStatus.OnAnimationRepeat) } }
        }
    }

    /**
     * 翻轉
     */
    fun rotationBack(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.rotation_back)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }
    fun rotationFont(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.rotation_font)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }

    /**
     * 升降1
     */
    fun slideUP(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.slid_up)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }
    fun slideDown(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.slid_down)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }

    /**
     * 升降2
     */
    fun rise(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.view_rise)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }
    fun drop(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.view_drop)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }

    /**
     * 淡入淡出
     */
    fun alphaEnter(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.view_alpha_enter)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }
    fun alphaExit(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.view_alpha_exit)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }
    fun customAlpha(view: View, alphaStart: Float, alphaEnd: Float, duration: Long, result: ((AnimationStatus) -> Unit)? = null) {
        val alpha = AlphaAnimation(alphaStart, alphaEnd)
        alpha.setAnimationListener(getAnimationListener(result))
        alpha.duration = duration
        view.animation = alpha
        view.clearAnimation()
        view.startAnimation(alpha)
    }

    /**
     * Toast 上升
     */
    fun toastSlideUp(context: Context, view: View, duration: Long? = null, result: ((AnimationStatus) -> Unit)? = null) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.toast_slide_up)
        anim.setAnimationListener(getAnimationListener(result))
        duration?.let { anim.duration = it }
        view.clearAnimation()
        view.startAnimation(anim)
    }

    /**
     * 波紋效果
     */
    fun ripple(context: Context, view: View, isLoop: Boolean = false) {
        val anim = AnimationUtils.loadAnimation(context, R.anim.view_ripple)

        if (isLoop) {
            anim.setAnimationListener(object: Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) { anim?.let { view.startAnimation(it) }  }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }

        view.clearAnimation()
        view.startAnimation(anim)
    }
    fun clearRippleAnimation(view: View) {
        view.animation = null
        view.clearAnimation()
    }
}