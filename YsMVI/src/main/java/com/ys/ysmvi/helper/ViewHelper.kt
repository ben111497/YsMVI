package com.ys.ysmvi.helper

import android.content.Context
import android.view.View
import com.ys.ysmvi.tools.YsViewAnimation

fun View.flop(context: Context, result: () -> Boolean) {
    YsViewAnimation.rotationBack(context, this) { status ->
        if (status == YsViewAnimation.AnimationStatus.OnAnimationEnd) {
            if (result()) YsViewAnimation.rotationFont(context, this)
        }
    }
}