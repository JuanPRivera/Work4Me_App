package com.example.work4me_app

import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.mapbox.navigation.ui.utils.internal.extensions.getBitmap
import java.io.InputStream
import java.net.URL


class ApplicantFeedAdapter(context: HomeApplicant, jobs: ArrayList<Job>) : RecyclerView.Adapter<ApplicantFeedAdapter.ViewHolder>() {

    private var _context : HomeApplicant = context
    private var _jobs : ArrayList<Job> = jobs

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        val jobTitle : TextView = view.findViewById<TextView>(R.id.tvJobTitle)
        val jobCity : TextView = view.findViewById<TextView>(R.id.tvCity)
        val jobSalary : TextView = view.findViewById<TextView>(R.id.tvSalary)
        val jobDescription : TextView = view.findViewById<TextView>(R.id.tvDescription)
        val companyName : TextView = view.findViewById<TextView>(R.id.tvCompanyName)
        val applyButton : TextView = view.findViewById<TextView>(R.id.applyBtn)
        val profilePic : ImageView = view.findViewById<ImageView>(R.id.companyProfilePicture)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.feed_applicant_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this._jobs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.applyButton.setOnClickListener {

            showUploadCvDialog(this._jobs[position].getCompany().getCompanyUid())
        }
        holder.jobTitle.text = this._jobs[position].getTitle()
        holder.jobCity.text = this._jobs[position].getCity()
        holder.jobSalary.text = formatPrice(this._jobs[position].getSalary())
        holder.jobDescription.text = this._jobs[position].getDescription()
        holder.companyName.text = this._jobs[position].getCompany().getCompanyName()
        if(this._jobs[position].getCompany().getPictureUrl() != null){
            AsyncTask.execute {
                val url: URL = URL(this._jobs[position].getCompany().getPictureUrl())

                val content: InputStream = url.content as InputStream
                val drawable: Drawable = Drawable.createFromStream(content, "src")

                _context.runOnUiThread {
                   holder.profilePic.setImageDrawable(BitmapDrawable(Convertions.getRoundedCroppedBitmap(drawable.getBitmap())))
                }
            }
        }

        holder.profilePic.setOnClickListener{_:View ->
            val intent : Intent = Intent(_context, profile_company::class.java)
            intent.putExtra("companyUid",this._jobs[position].getCompany().getCompanyUid())
            _context.startActivity(intent)
        }
    }

    private fun formatPrice(price:Int):String{

        val format : String = price.toString()

        var formatted : String = "";

        for(i in format.indices){

            if (format.length > 3 && i == format.length-3){
                formatted += "."
            }
            if (format.length > 6 && i == format.length-6){
                formatted += "'"
            }

            formatted += format[i]
        }

        formatted = "$$formatted"

        return formatted

    }

    private fun showUploadCvDialog(companyUid : String){

        val dialog : Dialog = Dialog(_context)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)

        dialog.setContentView(R.layout.upload_application)

        val browseBtn : ImageView = dialog.findViewById<ImageView>(R.id.browseCvBtn)

        browseBtn.setOnClickListener{
            val intent : Intent = Intent(_context, FileIntermidiateActivity::class.java)
            intent.putExtra("companyUid", companyUid)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            _context.startActivityForResult(intent, 2)

        }



        dialog.show()
    }



}