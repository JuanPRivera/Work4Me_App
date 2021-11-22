package com.example.work4me_app

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.io.InputStream
import java.net.URL

class profile_applicant : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance();
    private val auth = FirebaseAuth.getInstance();
    private val currentUser = auth.currentUser;
    private val uid = currentUser?.uid.toString();

    private lateinit var etName : EditText
    private lateinit var tvName : TextView
    private lateinit var viewName : View

    private lateinit var etLastName : EditText
    private lateinit var tvLastName : TextView
    private lateinit var viewLastName : View

    private lateinit var etId : EditText
    private lateinit var tvId : TextView
    private lateinit var viewId : View

    private lateinit var etCity : EditText
    private lateinit var tvCity : TextView
    private lateinit var viewCity : View

    private lateinit var etBirthday : EditText
    private lateinit var tvBirthday : TextView
    private lateinit var viewBirthday : View

    private lateinit var etPhone : EditText
    private lateinit var tvPhone : TextView
    private lateinit var viewPhone : View

    private lateinit var etEmail : EditText
    private lateinit var tvEmail : TextView
    private lateinit var viewEmail : View

    private lateinit var etNewPass : EditText
    private lateinit var tvNewPass : TextView
    private lateinit var viewNewPass : View

    private lateinit var etConfirmPass : EditText
    private lateinit var tvConfirmPass : TextView
    private lateinit var viewConfirmPass : View

    private lateinit var profileImage : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_applicant2)

        etName = findViewById<EditText>(R.id.editTextNamePrApp)
        tvName = findViewById<TextView>(R.id.textViewNamePrApp)
        viewName = findViewById<View>(R.id.viewBlueNamePrApp)

        etLastName = findViewById<EditText>(R.id.editTextLastnamePrApp)
        tvLastName = findViewById<TextView>(R.id.textViewLastnamePrApp)
        viewLastName = findViewById<View>(R.id.viewBlueLastNamePrApp)

        etId = findViewById<EditText>(R.id.editTextIdPrApp)
        tvId = findViewById<TextView>(R.id.textViewIdPrApp)
        viewId = findViewById<View>(R.id.viewBlueIdPrApp)

        etCity = findViewById<EditText>(R.id.editTextCityPrApp)
        tvCity = findViewById<TextView>(R.id.textViewCityPrApp)
        viewCity = findViewById<View>(R.id.viewBlueCityPrApp)

        etBirthday = findViewById<EditText>(R.id.editTextBirthdayPrApp)
        tvBirthday = findViewById<TextView>(R.id.textViewBirthdayPrApp)
        viewBirthday = findViewById<View>(R.id.viewBlueBirthdayPrApp)

        etPhone = findViewById<EditText>(R.id.editTextPhonePrApp)
        tvPhone = findViewById<TextView>(R.id.textViewPhonePrApp)
        viewPhone = findViewById<View>(R.id.viewBluePhonePrApp)

        etEmail = findViewById<EditText>(R.id.editTextEmailPrApp)
        tvEmail = findViewById<TextView>(R.id.textViewEmailPrApp)
        viewEmail = findViewById<View>(R.id.viewBlueEmailPrApp)

        etNewPass = findViewById<EditText>(R.id.editTextPasswordPrApp)
        tvNewPass = findViewById<TextView>(R.id.textViewPasswordPrApp)
        viewNewPass = findViewById<View>(R.id.viewBluePassword)

        etConfirmPass = findViewById<EditText>(R.id.editTextConfirmNewPassPrApp)
        tvConfirmPass = findViewById<TextView>(R.id.textViewConfirmNewPassPrApp)
        viewConfirmPass = findViewById<View>(R.id.viewBlueConfirmNewPassPrApp)

        profileImage = findViewById<ImageView>(R.id.profilePicture)

        setData()
    }

    fun setData(){
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc: DocumentSnapshot ->
                findViewById<EditText>(R.id.editTextNamePrApp)
                    .apply {
                        setText(doc!!["name"].toString())
                        isEnabled = false
                    }

                findViewById<EditText>(R.id.editTextLastnamePrApp)
                    .apply {
                        setText(doc!!["lastName"].toString())
                        isEnabled = false
                    }

                findViewById<EditText>(R.id.editTextIdPrApp)
                    .apply {
                        setText(doc!!["name"].toString())
                        isEnabled = false
                    }
                findViewById<EditText>(R.id.editTextCityPrApp)
                    .apply {
                        setText(doc!!["city"].toString())
                        isEnabled = false
                    }
                findViewById<EditText>(R.id.editTextBirthdayPrApp)
                    .apply {
                        setText(doc!!["birthday"].toString())
                        isEnabled = false
                    }
                findViewById<EditText>(R.id.editTextPhonePrApp)
                    .apply {
                        setText(doc!!["phoneNumber"].toString())
                        isEnabled = false
                    }
                findViewById<EditText>(R.id.editTextEmailPrApp)
                    .apply {
                        setText(auth.currentUser!!.email)
                        isEnabled = false
                    }

                AsyncTask.execute(object : Runnable {
                    override fun run() {
                        val url : URL = URL(doc!!["profile_picture"].toString())

                        val content : InputStream = url.content as InputStream
                        val drawable : Drawable = Drawable.createFromStream(content, "src")
                        profileImage.setImageDrawable(drawable)
                    }

                })




                InputAnimator.initializeAnimations(this, etName, tvName, viewName)
                InputAnimator.initializeAnimations(this, etLastName, tvLastName, viewLastName)
                InputAnimator.initializeAnimations(this, etId, tvId, viewId)
                InputAnimator.initializeAnimations(this, etCity, tvCity, viewCity)
                InputAnimator.initializeAnimations(this, etBirthday, tvBirthday, viewBirthday)
                InputAnimator.initializeAnimations(this, etPhone, tvPhone, viewPhone)
                InputAnimator.initializeAnimations(this, etEmail, tvEmail, viewEmail)
                InputAnimator.initializeAnimations(this, etNewPass, tvNewPass, viewNewPass)
                InputAnimator.initializeAnimations(this, etConfirmPass, tvConfirmPass, viewConfirmPass)
        }

    }

    fun onClickBack(view: View){
        finish()
    }

    fun onTapUpdate(view: View){
        //if(et)
    }
}