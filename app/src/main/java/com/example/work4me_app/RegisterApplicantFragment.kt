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

        val etName : EditText = view.findViewById<EditText>(R.id.editTextName)
        val etLastName : EditText = view.findViewById<EditText>(R.id.editTextLastname)
        val etId : EditText = view.findViewById<EditText>(R.id.editTextId)
        val etCity : EditText = view.findViewById<EditText>(R.id.editTextCity)
        val etBirthday : EditText = view.findViewById<EditText>(R.id.editTextTextBirthday)
        val etPhone : EditText = view.findViewById<EditText>(R.id.editTextPhone)
        val etEmail : EditText = view.findViewById<EditText>(R.id.editTextTextEmail)
        val etPassword : EditText = view.findViewById<EditText>(R.id.editTextPassword)
        val etConfirmPassword : EditText = view.findViewById<EditText>(R.id.editTextConfirmPass)

        InputAnimator.initializeAnimations(requireContext(), etName, view.findViewById(R.id.textViewName), view.findViewById(R.id.viewBlueName))
        InputAnimator.initializeAnimations(requireContext(), etLastName, view.findViewById(R.id.textViewLastname), view.findViewById(R.id.viewBlueLastName))
        InputAnimator.initializeAnimations(requireContext(), etId, view.findViewById(R.id.textViewId), view.findViewById(R.id.viewBlueId))
        InputAnimator.initializeAnimations(requireContext(), etCity, view.findViewById(R.id.textViewCity), view.findViewById(R.id.viewBlueCity))
        InputAnimator.initializeAnimations(requireContext(), etBirthday, view.findViewById(R.id.textViewBirthday), view.findViewById(R.id.viewBlueBirthday))
        InputAnimator.initializeAnimations(requireContext(), etPhone, view.findViewById(R.id.textViewPhone), view.findViewById(R.id.viewBluePhone))
        InputAnimator.initializeAnimations(requireContext(), etEmail, view.findViewById(R.id.textViewEmail), view.findViewById(R.id.viewBlueEmail))
        InputAnimator.initializeAnimations(requireContext(), etPassword, view.findViewById(R.id.textViewPassword), view.findViewById(R.id.viewBluePassword))
        InputAnimator.initializeAnimations(requireContext(), etConfirmPassword, view.findViewById(R.id.textViewConfirmPass), view.findViewById(R.id.viewBlueConfirmPass))

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