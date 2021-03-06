package com.example.work4me_app

import android.animation.ValueAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class HomeApplicant : AppCompatActivity() {

    private var _jobs : ArrayList<Job> = ArrayList<Job>()
    private lateinit var recycler : RecyclerView
    private lateinit var positionAnim : ValueAnimator
    private lateinit var drawer : FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_applicant)

        drawer = findViewById(R.id.drawer)

        positionAnim = ValueAnimator.ofFloat(-250f,0f)
        positionAnim.duration = 200
        positionAnim.addUpdateListener { newPos : ValueAnimator ->
            drawer.translationX = Convertions.dpToPx(newPos.animatedValue as Float)
        }

        val qrButton : LinearLayout = findViewById<LinearLayout>(R.id.qrButton)
        qrButton.setOnClickListener(
            View.OnClickListener {
                val integrator : IntentIntegrator = IntentIntegrator(this)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                integrator.setPrompt("Coloca el código a la vista")
                integrator.setCameraId(0)
                integrator.setBeepEnabled(true)
                integrator.initiateScan()
            }
        );

        val db : FirebaseFirestore = Firebase.firestore

        db.collection("users")
            .document(Firebase.auth.currentUser!!.uid)
            .addSnapshotListener{ doc: DocumentSnapshot?, _: FirebaseFirestoreException? ->
                findViewById<TextView>(R.id.nameAppbar).text = doc!!["name"]
                    .toString()
                    .replaceFirstChar { it.uppercase() }
            }

        recycler = findViewById<RecyclerView>(R.id.lvFeed)

        getData()
    }

    private fun getData() {
        val db: FirebaseFirestore = Firebase.firestore

        db.collection("jobs")
            .get()
            .addOnCompleteListener { task: Task<QuerySnapshot> ->
                if (task.isSuccessful && !task.result!!.isEmpty) {
                    for (i: Int in 0..task.result!!.size()) {
                        if (i != task.result!!.size()) {
                            val doc: DocumentSnapshot = task.result!!.documents[i]
                            db.collection("users").document(doc["companyUid"].toString())
                                .addSnapshotListener{company : DocumentSnapshot?, exc : FirebaseFirestoreException? ->
                                    _jobs.add(
                                        Job(
                                            doc.id,
                                            doc["city"].toString(),
                                            doc["job"].toString(),
                                            doc["description"].toString(),
                                            doc["salary"].toString().toInt(),
                                            Company(company!!["companyName"].toString())
                                        )
                                    )
                                    if(i == (task.result!!.size()-1)){
                                        val adapter : ApplicantFeedAdapter = ApplicantFeedAdapter(this, this._jobs)
                                        recycler.layoutManager = LinearLayoutManager(this)
                                        recycler.adapter = adapter
                                    }
                                }
                        }
                    }

                }
            }
    }

    private fun getApplications () {

    }


    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        val result : IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if(result != null){
            if(result.contents == null){
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, result.contents, Toast.LENGTH_SHORT).show()
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    fun showDrawer(view : View){
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

    fun signOut(view : View){
        Firebase.auth.signOut()
        finish()
    }

}