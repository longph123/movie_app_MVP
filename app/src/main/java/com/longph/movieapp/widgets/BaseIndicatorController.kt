package com.longph.movieapp.widgets

import android.graphics.Canvas
import android.graphics.Paint
import android.view.View

abstract class BaseIndicatorController {
    private var mTarget: View? = null

    fun setTarget(target: View) {
        this.mTarget = target
    }

    fun getTarget(): View? {
        return mTarget
    }

    /**
     * obtainViewWidth
     *
     * @return
     */
    fun getWidth(): Int {
        return mTarget!!.width
    }

    /**
     * obtainviewHeight
     *
     * @return
     */
    fun getHeight(): Int {
        return mTarget!!.height
    }

    /**
     * Refreshview
     */
    fun postInvalidate() {
        mTarget!!.postInvalidate()
    }

    /**
     * draw indicator what ever
     * you want to draw
     * Drawindicate
     *
     * @param canvas
     * @param paint
     */
    abstract fun draw(canvas: Canvas, paint: Paint?)

    /**
     * create animation or animations
     * ,and add to your indicator.
     * Create an animation or animation set,add toindcator
     */
    abstract fun createAnimation()
}