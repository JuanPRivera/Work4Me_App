package com.example.work4me_app

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.messaging.FirebaseMessaging
import org.w3c.dom.Text

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var lbEmail: TextView
    private lateinit var viewEmail: View
    private lateinit var etPassword: EditText
    private lateinit var lbPassword: TextView
    private lateinit var viewPassword: View
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("TokenFail", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            Log.d("token", task.result)
        })

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null) {
            startActivity(Intent(this, HomeApplicant::class.java))
        }

        etEmail = findViewById<EditText>(R.id.editTextTextEmail)
        lbEmail = findViewById<TextView>(R.id.textViewEmail)
        viewEmail = findViewById<View>(R.id.viewBlueEmail)

        etPassword = findViewById<EditText>(R.id.editTextPassword)
        lbPassword = findViewById<TextView>(R.id.textViewPassword)
        viewPassword = findViewById<View>(R.id.viewBluePassword)

        InputAnimator.initializeAnimations(this, etEmail, lbEmail, viewEmail)
        InputAnimator.initializeAnimations(this, etPassword, lbPassword, viewPassword)

    }

    fun onClickSignIn(view:View){
        auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
            .addOnCompleteListener(this) { task: Task<AuthResult> ->
                println(task.exception)
                if(task.isSuccessful){
                    val user = auth.currentUser
                    var uid = user?.uid.toString()
                    start(uid)
                    /*startActivity(Intent(this, HomeApplicant::class.java))*/
                }else{
                    if(task.exception!!.javaClass == FirebaseAuthInvalidCredentialsException::class.java){
                        Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_LONG).show()
                    }else if(task.exception!!.javaClass == FirebaseAuthInvalidUserException::class.java){
                        Toast.makeText(this, "The user doesn`t exists", Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    private fun start(uid:String){
        val db : FirebaseFirestore = Firebase.firestore
        var type : String
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc: DocumentSnapshot ->
                type = doc!!["type"].toString()
                if(type.equals("applicant")){
                    startActivity(Intent(this, HomeApplicant::class.java))
                    finish()
                }
            }
    }

    fun onTapHere(view: View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

}