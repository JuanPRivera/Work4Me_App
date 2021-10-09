package com.example.work4me_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView

class ApplicantFeedAdapter(context: Context, jobs: ArrayList<Job>) : BaseAdapter() {

    private var _context : Context = context
    private var _jobs : ArrayList<Job> = jobs

    companion object{
        private var inflater : LayoutInflater? = null;
    }

    init {
        inflater = _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getCount(): Int {
        return this._jobs.size
    }

    override fun getItem(position: Int): Job {
        return _jobs[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        var view : View? = convertView

        if(view == null) {
            view = inflater!!.inflate(R.layout.feed_applicant_item, null)
        }

        val jobTitle : TextView = view!!.findViewById<TextView>(R.id.tvJobTitle)
        val jobCity : TextView = view.findViewById<TextView>(R.id.tvCity)
        val jobSalary : TextView = view.findViewById<TextView>(R.id.tvSalary)
        val jobDescription : TextView = view.findViewById<TextView>(R.id.tvDescription)

        jobTitle.text = this._jobs[position].getTitle()
        jobCity.text = this._jobs[position].getCity()
        jobSalary.text = this._jobs[position].getSalary().toString()
        jobDescription.text = this._jobs[position].getDescription()

        return view
    }

}