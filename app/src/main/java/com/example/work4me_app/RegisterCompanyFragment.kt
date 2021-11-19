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
 * Use the [RegisterCompanyFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterCompanyFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val view : View = inflater.inflate(R.layout.fragment_register_company, container, false)

        val etLRName: EditText = view.findViewById<EditText>(R.id.editTextLRName)
        val etLRLastName: EditText = view.findViewById<EditText>(R.id.editTextLRLastName)
        val etTin: EditText = view.findViewById<EditText>(R.id.editTextTin)
        val etCompanyName: EditText = view.findViewById<EditText>(R.id.editTextCompanyName)
        val etCompanyPhone: EditText = view.findViewById<EditText>(R.id.editTextCompanyNumber)
        val etCompanyEmail: EditText = view.findViewById<EditText>(R.id.editTextCompanyEmail)
        val etPassword: EditText = view.findViewById<EditText>(R.id.editTextPassword)
        val etConfirmPassword: EditText = view.findViewById<EditText>(R.id.editTextConfirmPass)

        InputAnimator.initializeAnimations(requireContext(), etLRName, view.findViewById(R.id.textViewLRName), view.findViewById(R.id.viewBlueLRName))
        InputAnimator.initializeAnimations(requireContext(), etLRLastName, view.findViewById(R.id.textViewLRLastName), view.findViewById(R.id.viewBlueLRLastName))
        InputAnimator.initializeAnimations(requireContext(), etTin, view.findViewById(R.id.textViewTin), view.findViewById(R.id.viewBluetin))
        InputAnimator.initializeAnimations(requireContext(), etCompanyName, view.findViewById(R.id.textViewCompanyName), view.findViewById(R.id.viewBlueCompanyName))
        InputAnimator.initializeAnimations(requireContext(), etCompanyPhone, view.findViewById(R.id.textViewCompanyNumber), view.findViewById(R.id.viewBlueCompanyNumber))
        InputAnimator.initializeAnimations(requireContext(), etCompanyEmail, view.findViewById(R.id.textViewCompanyEmail), view.findViewById(R.id.viewBlueCompanyEmail))
        InputAnimator.initializeAnimations(requireContext(), etPassword, view.findViewById(R.id.textViewPassword), view.findViewById(R.id.viewBluePassword))
        InputAnimator.initializeAnimations(requireContext(), etConfirmPassword, view.findViewById(R.id.textViewConfirmPass), view.findViewById(R.id.viewBlueConfirmPass))

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterCompanyFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterCompanyFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}