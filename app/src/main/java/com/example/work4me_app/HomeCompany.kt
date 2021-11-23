package com.example.work4me_app

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
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
import com.mapbox.navigation.ui.utils.internal.extensions.getBitmap
import java.io.InputStream
import java.net.URL

class HomeCompany : AppCompatActivity() {

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

        val db : FirebaseFirestore = Firebase.firestore

        db.collection("users")
            .document(Firebase.auth.currentUser!!.uid)
            .addSnapshotListener{ doc: DocumentSnapshot?, _: FirebaseFirestoreException? ->
                findViewById<TextView>(R.id.nameAppbar).text = doc!!["companyName"]
                    .toString()
                    .replaceFirstChar { it.uppercase() }

                if(doc["profile_picture"] != null){
                    AsyncTask.execute {
                        val url: URL = URL(doc["profile_picture"].toString())

                        val content: InputStream = url.content as InputStream
                        val drawable: Drawable = Drawable.createFromStream(content, "src")

                        runOnUiThread {

                            findViewById<ImageView>(R.id.userPreview).setImageDrawable(BitmapDrawable(Convertions.getRoundedCroppedBitmap(drawable.getBitmap())))
                        }
                    }
                }
            }

        recycler = findViewById<RecyclerView>(R.id.lvFeed)

        getData()
    }

    private fun getData() {
        val db: FirebaseFirestore = Firebase.firestore
        val uid = Firebase.auth.currentUser?.uid

        db.collection("jobs")
            .whereEqualTo("companyUid", uid).get()
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
                                        val adapter : CompanyFeedAdapter = CompanyFeedAdapter(this, this._jobs)
                                        recycler.layoutManager = LinearLayoutManager(this)
                                        recycler.adapter = adapter
                                    }
                                }
                        }
                    }

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
        startActivity(Intent(this, profile_company::class.java))
    }
}