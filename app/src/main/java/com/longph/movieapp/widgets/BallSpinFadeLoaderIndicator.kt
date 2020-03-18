package com.longph.movieapp.widgets

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint

class BallSpinFadeLoaderIndicator : BaseIndicatorController() {

    val SCALE = 1.0f

    val ALPHA = 255
    /**
     * The proportion of dots
     */
    internal var scaleFloats = floatArrayOf(SCALE, SCALE, SCALE, SCALE, SCALE, SCALE, SCALE, SCALE)
    /**
     * Dot set transparency
     */
    internal var alphas = intArrayOf(ALPHA, ALPHA, ALPHA, ALPHA, ALPHA, ALPHA, ALPHA, ALPHA)

    override fun draw(canvas: Canvas, paint: Paint?) {
        val radius = (getWidth() / 10).toFloat()
        for (i in 0..7) {
            canvas.save()
            val point = circleAt(getWidth(), getHeight(), getWidth() / 2 - radius, i * (Math.PI / 4))
            canvas.translate(point.x, point.y)
            canvas.scale(scaleFloats[i], scaleFloats[i])
            paint?.alpha = alphas[i]
            canvas.drawCircle(0f, 0f, radius, paint)
            canvas.restore()
        }
    }

    /**
     * circularOThe center for(a,b),Radius asR,spotAAnd toXAngle of axisα.
     * PointACoordinate for(a+R*cosα,b+R*sinα)
     *
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @return
     */
    internal fun circleAt(width: Int, height: Int, radius: Float, angle: Double): Point {
        val x = (width / 2 + radius * Math.cos(angle)).toFloat()
        val y = (height / 2 + radius * Math.sin(angle)).toFloat()
        return Point(x, y)
    }

    override fun createAnimation() {
        val delays = intArrayOf(0, 120, 240, 360, 480, 600, 720, 780, 840)
        for (i in 0..7) {
            val scaleAnim = ValueAnimator.ofFloat(1f, 0.4f, 1f)//EstablishValueAnimatorobject
            scaleAnim.duration = 1000//Set the duration of the animation
            scaleAnim.repeatCount = -1//Set animation to repeat
            scaleAnim.startDelay = delays[i].toLong()//Delayed start animation
            scaleAnim.addUpdateListener { animation ->
                //ValueAnimatorOnly
// responsible for the first time,Therefore,
                // it is necessary to monitor the relevant attributes of the object to be updated.
                scaleFloats[i] = animation.animatedValue as Float//Gets the value of the current frame
                postInvalidate()
            }
            scaleAnim.start()//Start attribute animation

            val alphaAnim = ValueAnimator.ofInt(255, 77, 255)//Transparency animation
            alphaAnim.duration = 1000//
            alphaAnim.repeatCount = -1
            alphaAnim.startDelay = delays[i].toLong()
            alphaAnim.addUpdateListener { animation ->
                alphas[i] = animation.animatedValue as Int
                postInvalidate()
            }
            alphaAnim.start()
        }
    }

    internal inner class Point(var x: Float, var y: Float)
}