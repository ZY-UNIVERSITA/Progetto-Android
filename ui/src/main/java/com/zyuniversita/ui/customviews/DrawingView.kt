package com.zyuniversita.ui.customviews

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DrawingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {
    // Saving last stroke
    private val path: Path = Path()

    // Saving all strokes
    private val paths = mutableListOf<Path>()

    // Stroke paint
    private val strokePaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 12f
        isAntiAlias = true
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
    }

    // Grid paint
    private val gridLines: Int = 4
    private val gridPaint = Paint().apply {
        color = Color.LTGRAY
        style = Paint.Style.STROKE
    }

    // Border paint
    private val borderPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    override fun onDraw(canvas: Canvas) {
        // draw grid
        this.drawGrid(canvas)

        // draw border
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), borderPaint)

        // draw saved path
        paths.forEach { path ->
            canvas.drawPath(path, strokePaint)
        }

        canvas.drawPath(path, strokePaint)
    }

    private fun drawGrid(canvas: Canvas) {
        val stepX = width / gridLines.toFloat()
        val stepY = height / gridLines.toFloat()
        for (i in 1 until gridLines) {
            // vertical
            canvas.drawLine(i * stepX, 0f, i * stepX, height.toFloat(), gridPaint)
            // horizontal
            canvas.drawLine(0f, i * stepY, width.toFloat(), i * stepY, gridPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            val x = event.x
            val y = event.y

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // begin drawing
                    path.moveTo(x, y)
                    invalidate()
                    return true
                }

                MotionEvent.ACTION_MOVE -> {
                    // move drawing
                    path.lineTo(x, y)
                    invalidate()
                }

                MotionEvent.ACTION_UP -> {
                    // end drawing
                    // save path and begin another stroke
                    paths.add(Path(path))
                    path.reset()
                    invalidate()
                }
            }
        }

        return super.onTouchEvent(event)
    }

    // remove last stroke
    fun undoStroke() {
        if (paths.isNotEmpty()) {
            paths.removeAt(paths.lastIndex)
            invalidate()
        }
    }

    // clear all strokes
    fun clearAllStrokes() {
        if (paths.isNotEmpty()) {
            paths.clear()
            path.reset()
            invalidate()
        }
    }

    private fun createBitmap(): Bitmap {
        val backgroundColor = Color.WHITE
        val bmp = createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bmp)

        canvas.drawColor(backgroundColor)
        draw(canvas)

        return bmp
    }

    private fun now(): String {
        return SimpleDateFormat("yyyyMMdd_HHmmss", Locale.UK).format(Date())
    }

    suspend fun saveAsWebp(): Uri? {
        val format = Bitmap.CompressFormat.WEBP_LOSSLESS
        val quality = 100
        val mimeType = "image/webp"
        val appName = "BambooQuiz"

        val fileName = "char_${now()}.webp"

        return withContext(Dispatchers.IO) {
            val bmp = createBitmap()

            val values = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, fileName)
                put(MediaStore.Images.Media.MIME_TYPE, mimeType)
                put(
                    MediaStore.Images.Media.RELATIVE_PATH,
                    "${Environment.DIRECTORY_PICTURES}/${appName}"
                )
                // The file is not ready to be read immediately, so the user and other app will not see this file while the file is not ready
                put(MediaStore.Images.Media.IS_PENDING, 1)
                put(MediaStore.Images.Media.DATE_ADDED  , System.currentTimeMillis() / 1000)
            }

            // system object used to access file
            val resolver = context.contentResolver
            val uri = resolver.insert(
                // Use media store to access media storage in the main external volume
                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY),
                values
            )

            // if the resolver got a valid uri to the file then save the webp image
            if (uri != null) {
                resolver.openOutputStream(uri)?.use { os ->
                    bmp.compress(format, quality, os)
                }

                values.clear()
                // the file is ready to be read by other entities
                values.put(MediaStore.Images.Media.IS_PENDING, 0)
                // update the file
                resolver.update(uri, values, null, null)

                // remove bmp from memory
                bmp.recycle()
            }

            // finally return the uri of the file
            uri
        }
    }
}