package com.example.work4me_app

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import org.w3c.dom.Text

class RegisterActivity : AppCompatActivity() {

    private lateinit var activeBg : ImageView
    private lateinit var marginAnimator : ValueAnimator
    private lateinit var tvAplicant : TextView
    private lateinit var tvCompany : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        tvAplicant = findViewById(R.id.tvApplicant)
        tvCompany = findViewById(R.id.tvCompany)

        activeBg = findViewById<ImageView>(R.id.activeBg)
        marginAnimator = ValueAnimator.ofInt(220, -220)
        marginAnimator.duration = 200
        marginAnimator.addUpdateListener { newMargin : ValueAnimator ->
            val layout : FrameLayout.LayoutParams = activeBg.layoutParams as FrameLayout.LayoutParams
            layout.rightMargin = newMargin.animatedValue as Int
            activeBg.layoutParams = layout
        }
        marginAnimator.doOnEnd {
            if(marginAnimator.animatedValue as Int == 220){
                tvCompany.setTextColor(ContextCompat.getColor(this, R.color.white))
                tvAplicant.setTextColor(ContextCompat.getColor(this, R.color.background))
            }else if(marginAnimator.animatedValue as Int == -220){
                tvCompany.setTextColor(ContextCompat.getColor(this, R.color.background))
                tvAplicant.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }

    }

    fun onTapCompany(view: View){
        marginAnimator.start()
        val companyRegisterFragment : Fragment = RegisterCompanyFragment()
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left)
        transaction.replace(R.id.fragmentContainerView, companyRegisterFragment, null)
        transaction.commit()

    }

    fun onTapApplicant(view: View){
        marginAnimator.reverse()
        val applicantRegisterFragment : Fragment = RegisterApplicantFragment()
        val transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right)
        transaction.replace(R.id.fragmentContainerView, applicantRegisterFragment, null)
        transaction.commit()
    }
}