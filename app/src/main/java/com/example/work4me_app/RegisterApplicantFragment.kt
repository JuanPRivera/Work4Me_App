package com.example.work4me_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [RegisterApplicantFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterApplicantFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var etName : EditText
    private lateinit var etLastName : EditText
    private lateinit var etId : EditText
    private lateinit var etCity : EditText
    private lateinit var etBirthday : EditText
    private lateinit var etPhone : EditText
    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var etConfirmPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_register_applicant, container, false)


        etName = view.findViewById<EditText>(R.id.editTextName)
        etLastName = view.findViewById<EditText>(R.id.editTextLastname)
        etId = view.findViewById<EditText>(R.id.editTextId)
        etCity = view.findViewById<EditText>(R.id.editTextCity)
        etBirthday = view.findViewById<EditText>(R.id.editTextTextBirthday)
        etPhone = view.findViewById<EditText>(R.id.editTextPhone)
        etEmail = view.findViewById<EditText>(R.id.editTextTextEmail)
        etPassword = view.findViewById<EditText>(R.id.editTextPassword)
        etConfirmPassword = view.findViewById<EditText>(R.id.editTextConfirmPass)

        // Inflate the layout for this fragment
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterApplicantFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterApplicantFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}