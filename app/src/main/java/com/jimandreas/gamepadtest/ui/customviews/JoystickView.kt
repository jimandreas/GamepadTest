/*
* Copyright (C) 2019 Google Inc.
* Copyright (C) 2020 Jim Andreas
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

/*
 * derived from:
 * https://codelabs.developers.google.com/codelabs/advanced-andoid-kotlin-training-custom-views/
 * and
 * https://github.com/googlecodelabs/android-kotlin-drawing-custom-views
 *
 */
@file:Suppress("unused", "UNUSED_PARAMETER")

package com.jimandreas.gamepadtest.ui.customviews

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import kotlin.math.min


class JoystickView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var xCoord = 1f
    private var yCoord = 1f
    private var doRedIndicator = false

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        // Paint styles used for rendering are initialized here. This
        // is a performance optimization, since onDraw() is called
        // for every screen refresh.
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 55.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private var radius = 0.0f                  // Radius of the circle.

    //Point at which to draw label and indicator circle position. PointF is a point
    //with floating-point coordinates.
    private val pointPosition: PointF = PointF(0.0f, 0.0f)


    init {
        isClickable = false
    }

    /**
     * This is called during layout when the size of this view has changed. If
     * the view was just added to the view hierarchy, it is called with the old
     * values of 0. The code determines the drawing bounds for the custom view.
     *
     * @param width    Current width of this view.
     * @param height    Current height of this view.
     * @param oldWidth Old width of this view.
     * @param oldHeight Old height of this view.
     */
    override fun onSizeChanged(width: Int, height: Int, oldWidth: Int, oldHeight: Int) {
        // Calculate the radius from the smaller of the width and height.
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
        updatePosition(0f, 0f)
    }

    /**
     * Renders view content: an outer circle to serve as the "dial",
     * and a smaller blue circle to server as the indicator.
     *
     * @param canvas The canvas on which the background will be drawn.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the joytick background
        paint.color = Color.BLACK
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        if (doRedIndicator) {
            paint.color = Color.RED
        } else {
            paint.color = Color.BLUE
        }

        canvas.drawCircle(
            xCoord,
            yCoord,
            radius/2, paint)

    }

    fun updatePosition(x: Float, y: Float) {

        val centerX = width / 2
        val centerY = height / 2
        val maxDisplacement = radius - radius/4
        xCoord = x * maxDisplacement + centerX
        yCoord = y * maxDisplacement + centerY
        invalidate()
    }

    fun updateIndicator(doRedIn: Boolean) {
        doRedIndicator = doRedIn
    }


}