package com.example.work4me_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class profile_company : AppCompatActivity() {

    private val db = FirebaseFirestore.getInstance();
    private val auth = FirebaseAuth.getInstance();
    private val currentUser = auth.currentUser;
    private val uid = currentUser?.uid.toString();

    private lateinit var etNameCompany : EditText
    private lateinit var tvNameCompany : TextView
    private lateinit var viewNameCompany : View

    private lateinit var etNit : EditText
    private lateinit var tvNit : TextView
    private lateinit var viewNit : View

    private lateinit var etNumberCompany : EditText
    private lateinit var tvNumberCompany : TextView
    private lateinit var viewNumberCompany : View

    private lateinit var etLrNameCompany : EditText
    private lateinit var tvLrNameCompany : TextView
    private lateinit var viewLrNameCompany : View

    private lateinit var etLrLastnameCompany : EditText
    private lateinit var tvLrLastnameCompany : TextView
    private lateinit var viewLrLastnameCompany : View

    private lateinit var etEmailCompany : EditText
    private lateinit var tvEmailCompany : TextView
    private lateinit var viewEmailCompany : View

    private lateinit var etPassCompany : EditText
    private lateinit var tvPassCompany : TextView
    private lateinit var viewPassCompany : View

    private lateinit var etConfirmPassCompany : EditText
    private lateinit var tvConfirmPassCompany : TextView
    private lateinit var viewConfirmPassCompany : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_company)

        etNameCompany = findViewById<EditText>(R.id.editTextCompanyNamePr)
        tvNameCompany = findViewById<TextView>(R.id.textViewCompanyNamePr)
        viewNameCompany = findViewById<View>(R.id.viewBlueCompanyNamePr)

        etNit = findViewById<EditText>(R.id.editTextNITPrComp)
        tvNit = findViewById<TextView>(R.id.textViewNITPrComp)
        viewNit = findViewById<View>(R.id.viewBlueNITPrComp)

        etNumberCompany = findViewById<EditText>(R.id.editTextNumberComApp)
        tvNumberCompany = findViewById<TextView>(R.id.textViewNumberComApp)
        viewNumberCompany = findViewById<View>(R.id.viewBlueNumberComApp)

        etLrNameCompany = findViewById<EditText>(R.id.editTextLRCompApp)
        tvLrNameCompany = findViewById<TextView>(R.id.textViewLRCompApp)
        viewLrNameCompany = findViewById<View>(R.id.viewBlueLRCompApp)

        etLrLastnameCompany = findViewById<EditText>(R.id.editTextLRLastNameCompApp)
        tvLrLastnameCompany = findViewById<TextView>(R.id.textViewLRLastNameCompApp)
        viewLrLastnameCompany = findViewById<View>(R.id.viewBlueLRLastNameCompApp)

        etEmailCompany = findViewById<EditText>(R.id.editTextEmailPrComp)
        tvEmailCompany = findViewById<TextView>(R.id.textViewEmailPrComp)
        viewEmailCompany = findViewById<View>(R.id.viewBlueEmailPrComp)

        etPassCompany = findViewById<EditText>(R.id.editTextNewPassCompProf)
        tvPassCompany = findViewById<TextView>(R.id.textViewNewPassCompProf)
        viewPassCompany = findViewById<View>(R.id.viewBlueNewPassCompProf)

        etConfirmPassCompany = findViewById<EditText>(R.id.editTextConfirmNewPassPrComp)
        tvConfirmPassCompany = findViewById<TextView>(R.id.textViewConfirmNewPassPrComp)
        viewConfirmPassCompany = findViewById<View>(R.id.viewBluePassword)

        /*setData()
        InputAnimator.initializeAnimations(this, etNameCompany, tvNameCompany, viewNameCompany)
        InputAnimator.initializeAnimations(this, etNit, tvNit, viewNit)
        InputAnimator.initializeAnimations(this, etNumberCompany, tvNumberCompany, viewNumberCompany)
        InputAnimator.initializeAnimations(this, etLrNameCompany, tvLrNameCompany, viewLrNameCompany)
        InputAnimator.initializeAnimations(this, etLrLastnameCompany, tvLrLastnameCompany, viewLrLastnameCompany)
        InputAnimator.initializeAnimations(this, etEmailCompany, tvEmailCompany, viewEmailCompany)
        InputAnimator.initializeAnimations(this, etPassCompany, tvPassCompany, viewPassCompany)
        InputAnimator.initializeAnimations(this, etConfirmPassCompany, tvConfirmPassCompany, viewConfirmPassCompany)*/

    }

    /*fun setData(){
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
                findViewById<EditText>(R.id.editTextNewPassCompProf).setText(doc!!["password"].toString())
                findViewById<EditText>(R.id.editTextConfirmNewPassPrComp).setText(doc!!["confirmPasswordCompany"].toString())
        }

    }*/

}