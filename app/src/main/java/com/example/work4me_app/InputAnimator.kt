package com.example.work4me_app

import android.animation.ValueAnimator
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class InputAnimator {

    companion object{
        fun initializeAnimations(context:Context,et: EditText, lb: TextView, vw: View){
            val scaleAnimation : ValueAnimator = ValueAnimator.ofFloat(0f,1f)
            scaleAnimation.duration = 200
            scaleAnimation.addUpdateListener { scale : ValueAnimator ->
                vw.scaleX = scale.animatedValue as Float
            }

            val sizeEmailAnimator : ValueAnimator = ValueAnimator.ofFloat(22f, 18f)
            sizeEmailAnimator.duration = 200
            sizeEmailAnimator.addUpdateListener { size : ValueAnimator ->
                lb.textSize = size.animatedValue as Float
            }

            val moveEmailAnimator : ValueAnimator = ValueAnimator.ofInt(0,40)
            moveEmailAnimator.duration = 200
            moveEmailAnimator.addUpdateListener { move : ValueAnimator ->
                (lb.layoutParams as FrameLayout.LayoutParams).apply {
                    setMargins(0,0,0,Convertions.dpToPx(move.animatedValue as Int))
                }
            }

            Log.d("isempty", et.text.toString())
            if(!et.text.isEmpty()){

                lb.setTextColor(ContextCompat.getColor(context, R.color.dark_font))
                scaleAnimation.start()
            }

            et.onFocusChangeListener = View.OnFocusChangeListener(){ view: View, isFocused: Boolean ->
                if(isFocused){
                    lb.setTextColor(ContextCompat.getColor(context, R.color.blue))
                    scaleAnimation.start()
                    if(et.text.isEmpty()){
                        sizeEmailAnimator.start()
                        moveEmailAnimator.start()
                    }
                }else{
                    lb.setTextColor(ContextCompat.getColor(context, R.color.dark_font))
                    scaleAnimation.reverse()
                    if(et.text.isEmpty()){
                        sizeEmailAnimator.reverse()
                        moveEmailAnimator.reverse()
                    }
                }
            }
        }
    }

}