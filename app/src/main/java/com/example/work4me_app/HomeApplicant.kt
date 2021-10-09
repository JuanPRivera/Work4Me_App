package com.example.work4me_app

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ListView
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeApplicant : AppCompatActivity() {

    private var _jobs : ArrayList<Job> = ArrayList<Job>();
    private lateinit var listView : ListView ;
    private lateinit var positionAnim : ValueAnimator;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_applicant)

        val drawer : LinearLayout = findViewById(R.id.drawer)
        val feed : LinearLayout = findViewById(R.id.mainView)

        positionAnim = ValueAnimator.ofFloat(-250f,0f)
        positionAnim.duration = 500
        positionAnim.addUpdateListener { newPos : ValueAnimator ->
            println(drawer.translationX)
            drawer.translationX = Convertions.dpToPx(newPos.animatedValue as Float)
        }

        feed.setOnClickListener{
            positionAnim.reverse()
        }

        listView = findViewById(R.id.lvFeed)

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
                            _jobs.add(
                                Job(
                                    doc.id,
                                    doc["city"].toString(),
                                    doc["job"].toString(),
                                    doc["description"].toString(),
                                    doc["salary"].toString().toInt()
                                )
                            )
                        }
                    }
                    val adapter : ApplicantFeedAdapter = ApplicantFeedAdapter(this, this._jobs)
                    listView.adapter = adapter
                }
            }
    }

    fun showDrawer(view: View){
        positionAnim.start()
    }
}