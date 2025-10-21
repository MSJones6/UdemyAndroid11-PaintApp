package de.msjones.paintapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class DrawingView(context: Context, attributes: AttributeSet) : View(context, attributes) {
    private var drawPath: CustomPath? = null
    private var canvasBitmap: Bitmap? = null
    private var drawPaint: Paint? = null
    private var canvasPaint: Paint? = null
    private var brushSize: Float = 0.toFloat()
    private var color = Color.BLACK
    private var canvas: Canvas? = null

    init {
        setupDrawing()
    }

    private fun setupDrawing() {
        brushSize = 20.toFloat()

        drawPaint = Paint()
        drawPaint!!.color = color
        drawPaint!!.style = Paint.Style.STROKE
        drawPaint!!.strokeJoin = Paint.Join.ROUND
        drawPaint!!.strokeCap = Paint.Cap.ROUND

        drawPath = CustomPath(color, brushSize)

        canvasPaint = Paint(Paint.DITHER_FLAG)

    }

    internal inner class CustomPath(var color: Int, var brushThickness: Float) : Path() {

    }

}



