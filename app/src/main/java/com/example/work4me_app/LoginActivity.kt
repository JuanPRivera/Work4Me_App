package com.example.work4me_app

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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

        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null) {
            Toast.makeText(this, "Already signed in", Toast.LENGTH_LONG).show()
            auth.signOut()
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
                if(task.isSuccessful){
                    Toast.makeText(this, "Signed In", Toast.LENGTH_LONG).show()

                }else{
                    if(task.isComplete ){

                    }
                }
            }
    }

}