package com.example.work4me_app

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class status_jobs_applicant : AppCompatActivity() {

    private lateinit var positionAnim : ValueAnimator
    private lateinit var drawer : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status_jobs_applicant)

        drawer = findViewById(R.id.drawer)

        positionAnim = ValueAnimator.ofFloat(-250f,0f)
        positionAnim.duration = 200
        positionAnim.addUpdateListener { newPos : ValueAnimator ->
            drawer.translationX = Convertions.dpToPx(newPos.animatedValue as Float)
        }

}

    fun showDrawerStatus(view : View){
        positionAnim.start()
        drawer.isClickable = true
        drawer.isFocusable = true
        drawer.setOnClickListener{
            print("click")
            if(drawer.translationX == 0f){
                positionAnim.reverse()
                drawer.isClickable = false
                drawer.isFocusable = false
            }
        }
    }

    fun getMyProfileStatus(view : View){
        startActivity(Intent(this, profile_applicant::class.java))
    }
}