package com.example.work4me_app

import android.content.res.Resources
import android.graphics.*

class Convertions {
    companion object {
        fun dpToPx(dp:Int):Int{
            return dp * Resources.getSystem().displayMetrics.density.toInt()
        }
        fun dpToPx(dp:Float):Float{
            return dp * Resources.getSystem().displayMetrics.density
        }
        fun getRoundedCroppedBitmap(bitmap: Bitmap): Bitmap? {
            val widthLight = bitmap.width
            val output = Bitmap.createBitmap(
                bitmap.width, bitmap.height,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(output)
            val paintColor = Paint()
            paintColor.setFlags(Paint.ANTI_ALIAS_FLAG)
            val rectF = RectF(Rect(0, 0, widthLight, widthLight))
            canvas.drawRoundRect(rectF, widthLight / 2f, widthLight / 2f, paintColor)
            val paintImage = Paint()
            paintImage.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP))
            canvas.drawBitmap(bitmap, 0f, 0f, paintImage)
            return output
        }
    }
}