package com.example.work4me_app

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import kotlin.math.log

class RegisterActivity : AppCompatActivity() {

    private lateinit var activeBg: ImageView
    private lateinit var marginAnimator: ValueAnimator
    private lateinit var tvAplicant: TextView
    private lateinit var tvCompany: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        tvAplicant = findViewById(R.id.tvApplicant)
        tvCompany = findViewById(R.id.tvCompany)

        activeBg = findViewById<ImageView>(R.id.activeBg)
        marginAnimator = ValueAnimator.ofInt(220, -220)
        marginAnimator.duration = 200
        marginAnimator.addUpdateListener { newMargin: ValueAnimator ->
            val layout: FrameLayout.LayoutParams = activeBg.layoutParams as FrameLayout.LayoutParams
            layout.rightMargin = newMargin.animatedValue as Int
            activeBg.layoutParams = layout
        }
        marginAnimator.doOnEnd {
            if (marginAnimator.animatedValue as Int == 220) {
                tvCompany.setTextColor(ContextCompat.getColor(this, R.color.white))
                tvAplicant.setTextColor(ContextCompat.getColor(this, R.color.background))
            } else if (marginAnimator.animatedValue as Int == -220) {
                tvCompany.setTextColor(ContextCompat.getColor(this, R.color.background))
                tvAplicant.setTextColor(ContextCompat.getColor(this, R.color.white))
            }
        }

    }

    fun onTapCompany(view: View) {
        marginAnimator.start()
        val companyRegisterFragment: Fragment = RegisterCompanyFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_right, R.anim.exit_left)
        transaction.replace(R.id.fragmentContainerView, companyRegisterFragment, null)
        transaction.commit()

    }

    fun onTapApplicant(view: View) {
        marginAnimator.reverse()
        val applicantRegisterFragment: Fragment = RegisterApplicantFragment()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.setCustomAnimations(R.anim.enter_left, R.anim.exit_right)
        transaction.replace(R.id.fragmentContainerView, applicantRegisterFragment, null)
        transaction.commit()
    }

    fun onTapSignUp(view: View) {

        val currentFragment: Fragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as Fragment

        val etPassword: EditText =
            currentFragment.requireView().findViewById<EditText>(R.id.editTextPassword)
        val etConfirmPassword: EditText =
            currentFragment.requireView().findViewById<EditText>(R.id.editTextConfirmPass)

        if (etPassword.text.toString() == etConfirmPassword.text.toString()) {

            if (currentFragment is RegisterApplicantFragment) {
                val etName: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextName)
                val etLastName: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextLastname)
                val etId: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextId)
                val etCity: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextCity)
                val etBirthday: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextTextBirthday)
                val etPhone: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextPhone)
                val etEmail: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextTextEmail)



                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    etEmail.text.toString(),
                    etPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val currentUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                            val userData: HashMap<String, String> = hashMapOf(
                                "uid" to currentUser.uid,
                                "id" to etId.text.toString(),
                                "name" to etName.text.toString(),
                                "lastName" to etLastName.text.toString(),
                                "city" to etCity.text.toString(),
                                "birthday" to etBirthday.text.toString(),
                                "phoneNumber" to etPhone.text.toString(),
                                "type" to "applicant"
                            )

                            val db : FirebaseFirestore = Firebase.firestore
                            db.collection("users").document(userData["uid"]!!)
                                .set(userData)
                                .addOnSuccessListener { Toast.makeText(this, "User Created Successfully", Toast.LENGTH_LONG).show() }
                                .addOnFailureListener { Toast.makeText(this, "Database error", Toast.LENGTH_LONG).show() }

                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(this, "Email already in use", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                    }


            } else {

                val etLRName: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextLRName)
                val etLRLastName: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextLRLastName)
                val etTin: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextTin)
                val etCompanyName: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextCompanyName)
                val etCompanyPhone: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextCompanyNumber)
                val etCompanyEmail: EditText =
                    currentFragment.requireView().findViewById<EditText>(R.id.editTextCompanyEmail)

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    etCompanyEmail.text.toString(),
                    etPassword.text.toString()
                )
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val currentUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
                            val userData: HashMap<String, String> = hashMapOf(
                                "uid" to currentUser.uid,
                                "tin" to etTin.text.toString(),
                                "lrName" to etLRName.text.toString(),
                                "lrLastName" to etLRLastName.text.toString(),
                                "companyName" to etCompanyName.text.toString(),
                                "phoneNumber" to etCompanyPhone.text.toString(),
                                "type" to "company"
                            )

                            val db : FirebaseFirestore = Firebase.firestore
                            db.collection("users").document(userData["uid"]!!)
                                .set(userData)
                                .addOnSuccessListener { Toast.makeText(this, "User Created Successfully", Toast.LENGTH_LONG).show() }
                                .addOnFailureListener { Toast.makeText(this, "Database error", Toast.LENGTH_LONG).show() }


                        } else {
                            if (task.exception is FirebaseAuthUserCollisionException) {
                                Toast.makeText(this, "Email already in use", Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }

            }

        } else {
            Toast.makeText(this, "The passwords don't match", Toast.LENGTH_LONG).show()
        }
    }

}