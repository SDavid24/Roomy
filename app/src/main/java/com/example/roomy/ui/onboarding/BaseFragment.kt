package com.example.roomy.ui.onboarding

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.roomy.R
import com.example.roomy.databinding.FragmentBaseBinding
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {
    lateinit var mProgressDialog : Dialog
    lateinit var baseBinding: FragmentBaseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        baseBinding = FragmentBaseBinding.inflate(inflater, container, false)

        return baseBinding.root
    }

    /**Method to show progress dialog that something is loading*/
    fun showProgressDialog(text: String){
        mProgressDialog = Dialog(requireActivity())

        //set layout
        val view: View = requireActivity().layoutInflater.inflate(R.layout.dialog_progress, null)
        mProgressDialog.setContentView(view)
        val dialogText = view.findViewById<View>(R.id.tv_progress_text) as TextView

        //set text
        dialogText.text = text

        mProgressDialog.show()
    }

    //Method to dismiss dialog
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    /**Method for the snack bar that'll display throughout the app*/
    fun showSnackBar(message: String){
        val snackBar = Snackbar.make(requireActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)

        val snackBarView = snackBar.view
        snackBarView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.error_red))

        snackBar.show()
    }
}