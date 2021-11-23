package com.example.work4me_app

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask

class FileIntermidiateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent : Intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "application/pdf"


        startActivityForResult(Intent.createChooser(intent, "selectCv"), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {

            val uri: Uri? = data?.data

            Log.d("myUri", "${data?.data}")

            val intent: Intent = Intent()
            intent.data = uri
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            intent.putExtra("companyUid", getIntent().getStringExtra("companyUid"))
            setResult(2, intent)
            finish()


        }
    }
}