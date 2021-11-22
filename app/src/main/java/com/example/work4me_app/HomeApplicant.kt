package com.example.work4me_app

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
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
        qrButton.setOnClickListener {
                val integrator : IntentIntegrator = IntentIntegrator(this)
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                integrator.setPrompt("Place the code in the screen")
                integrator.setCameraId(0)
                integrator.setBeepEnabled(true)
                integrator.initiateScan()

                //startActivity(Intent(this, MapActivity::class.java))
            }


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
                                            Company(company!!["companyName"].toString(), company!!["companyUid"].toString())
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


    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?){
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 2){

            Log.d("companyUidNew", "${data?.getStringExtra("companyUid")}")

            val application = hashMapOf(
                "applicant_uid" to FirebaseAuth.getInstance().currentUser!!.uid,
                "company_uid" to data?.getStringExtra("companyUid"),
                "application_data" to hashMapOf("status" to "")
            )

            FirebaseFirestore
                .getInstance()
                .collection("applications")
                .add(application)
                .addOnSuccessListener { doc : DocumentReference ->
                    val uri : Uri? = data?.data;

                    val cvsReference : StorageReference = FirebaseStorage.getInstance().reference.child("cvs/${doc.id}")

                    val uploadTask : UploadTask = cvsReference.putFile(uri!!)

                    uploadTask.addOnSuccessListener{ _: UploadTask.TaskSnapshot ->
                        cvsReference.downloadUrl.addOnSuccessListener { uri : Uri ->

                            doc.set(hashMapOf(
                                "application_data" to hashMapOf(
                                    "status" to "",
                                    "cv" to uri.toString()
                                )), SetOptions.merge())
                        }
                    }
                }



        }else{
            val result : IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if(result != null){

                if(result.contents == null){
                    Toast.makeText(this, "Code reader was cancel", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, result.contents, Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()

            }
        }



    }

    fun showDrawer(view : View){
        positionAnim.start()
        drawer.isClickable = true
        drawer.isFocusable = true
        drawer.setOnClickListener{
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

    fun getMyProfile(view : View){
        startActivity(Intent(this, profile_applicant::class.java))
    }

}