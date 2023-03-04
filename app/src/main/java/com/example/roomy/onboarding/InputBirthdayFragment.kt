package com.example.roomy.onboarding

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.annotation.RequiresApi
import com.example.roomy.utils.PrefManager
import com.example.roomy.databinding.FragmentInputBirthdayBinding
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class InputBirthdayFragment : OnboardBaseFragment() {
    private lateinit var inputBirthdayBinding: FragmentInputBirthdayBinding
    private lateinit var datePicker : MaterialDatePicker<Long>
    private val addInterestFragment = AddInterestFragment()
    private val prefManager = PrefManager()
    private lateinit var day : EditText
    private lateinit var month : EditText
    private lateinit var year : EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        inputBirthdayBinding = FragmentInputBirthdayBinding.inflate(inflater, container, false)

        //Inflate the layout for this fragment
        return inputBirthdayBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(inputBirthdayBinding.inputBirthdayToolbar)

        day = inputBirthdayBinding.etDay
        month = inputBirthdayBinding.etMonth
        year = inputBirthdayBinding.etYear

        configureTextFonts()
        selectDate()
        clickListeners()
    }

    private fun selectDate() {
        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select birthday")
            //.setCalendarConstraints()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        datePicker.addOnPositiveButtonClickListener { selectedDate ->
            val selectedMonth =
                SimpleDateFormat("MM", Locale.getDefault()).format(Date(selectedDate)).toInt()

            calendar.timeInMillis = selectedDate

            //Setting the month
            if (calendar.get(Calendar.MONTH) < 10) {
                month.setText("0$selectedMonth")
            } else {
                month.setText("$selectedMonth")
            }

            //Setting the day
            if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
                day.setText(
                    "0" + calendar.get(Calendar.DAY_OF_MONTH).toString())
            } else {
                day.setText(calendar.get(Calendar.DAY_OF_MONTH).toString())
            }

            //Setting the year
            year.setText(calendar.get(Calendar.YEAR).toString())

            prefManager.setBirthday("${day.text}-${month.text}-${year.text}" , requireContext() )
            //Making the button clickable
            resetContinueBtnToClickable(inputBirthdayBinding.tvContinue, addInterestFragment)

        }
    }

    //Click listeners for the views which opens up the calendar dialog
    private fun clickListeners(){
        inputBirthdayBinding.llMonth.setOnClickListener {
            if(!datePicker.isVisible) {
                datePicker.show(parentFragmentManager, "tag")
            }
        }
        inputBirthdayBinding.llDay.setOnClickListener {
            if(!datePicker.isVisible) {
                datePicker.show(parentFragmentManager, "tag")
            }
        }
        inputBirthdayBinding.llYear.setOnClickListener {
            if(!datePicker.isVisible) {
                datePicker.show(parentFragmentManager, "tag")
            }
        }

    }

    private fun configureTextFonts(){
        //resetting the font of the text to the appropriate one
        inputBirthdayBinding.birthdayQuestion.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.tvMonth.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.etMonth.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.tvDay.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.etDay.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.etYear.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.tvYear.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.studentQuestion.typeface = initializeDMSansTypeFace(requireActivity())
        inputBirthdayBinding.tvContinue.typeface = initializeDMSansTypeFace(requireActivity())
    }

}