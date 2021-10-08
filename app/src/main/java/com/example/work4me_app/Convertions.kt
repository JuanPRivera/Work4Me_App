package com.example.work4me_app

import android.content.res.Resources

class Convertions {
    companion object {
        fun dpToPx(dp:Int):Int{
            return dp * Resources.getSystem().displayMetrics.density.toInt();
        }
    }
}