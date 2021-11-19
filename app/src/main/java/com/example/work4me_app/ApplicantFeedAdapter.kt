package com.example.work4me_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ApplicantFeedAdapter(context: Context, jobs: ArrayList<Job>) : RecyclerView.Adapter<ApplicantFeedAdapter.ViewHolder>() {

    private var _context : Context = context
    private var _jobs : ArrayList<Job> = jobs

    class ViewHolder(view:View):RecyclerView.ViewHolder(view){

        val jobTitle : TextView = view.findViewById<TextView>(R.id.tvJobTitle)
        val jobCity : TextView = view.findViewById<TextView>(R.id.tvCity)
        val jobSalary : TextView = view.findViewById<TextView>(R.id.tvSalary)
        val jobDescription : TextView = view.findViewById<TextView>(R.id.tvDescription)
        val companyName : TextView = view.findViewById<TextView>(R.id.tvCompanyName)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.feed_applicant_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return this._jobs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.jobTitle.text = this._jobs[position].getTitle()
        holder.jobCity.text = this._jobs[position].getCity()
        holder.jobSalary.text = formatPrice(this._jobs[position].getSalary())
        holder.jobDescription.text = this._jobs[position].getDescription()
        holder.companyName.text = this._jobs[position].getCompany().getCompanyName()
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

}