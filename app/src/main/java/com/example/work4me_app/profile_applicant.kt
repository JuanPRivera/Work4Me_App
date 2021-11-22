package com.example.work4me_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

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

        setData()
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

    fun setData(){
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc: DocumentSnapshot ->
                findViewById<EditText>(R.id.editTextNamePrApp).setText(doc!!["name"].toString())
                findViewById<EditText>(R.id.editTextLastnamePrApp).setText(doc!!["lastName"].toString())
                findViewById<EditText>(R.id.editTextIdPrApp).setText(doc!!["id"].toString())
                findViewById<EditText>(R.id.editTextCityPrApp).setText(doc!!["city"].toString())
                findViewById<EditText>(R.id.editTextBirthdayPrApp).setText(doc!!["birthday"].toString())
                findViewById<EditText>(R.id.editTextPhonePrApp).setText(doc!!["phoneNumber"].toString())
                findViewById<EditText>(R.id.editTextEmailPrApp).setText(doc!!["email"].toString())
                findViewById<EditText>(R.id.editTextPasswordPrApp).setText(doc!!["password"].toString())
                findViewById<EditText>(R.id.editTextConfirmNewPassPrApp).setText(doc!!["confirmPassword"].toString())
        }

    }

    fun onClickBack(view: View){
        finish()
    }

    fun onTapUpdate(view: View){
        db.collection("users").whereEqualTo("uid", auth.currentUser!!.uid).get().addOnSuccessListener {
            documents : QuerySnapshot->

        }
    }
}