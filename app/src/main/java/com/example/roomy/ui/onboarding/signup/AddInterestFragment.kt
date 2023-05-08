package com.example.roomy.ui.onboarding.signup

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.roomy.utils.PrefManager
import com.example.roomy.R
import com.example.roomy.databinding.FragmentAddInterestBinding
import com.example.roomy.databinding.ItemInterestChipBinding
import com.example.roomy.ui.onboarding.OnboardBaseFragment
import com.google.android.material.chip.Chip

class AddInterestFragment : OnboardBaseFragment() {

    private lateinit var addInterestBinding: FragmentAddInterestBinding
    private lateinit var itemInterestChipBinding: ItemInterestChipBinding
    private val inputPasswordFragment = InputPasswordFragment()
    private val interests = ArrayList<String>()

    private var chosenInterestList = ArrayList<String>()
    private var clicked: Boolean = false
    private val prefManager = PrefManager()
    private lateinit var  newChip : Chip

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        addInterestBinding = FragmentAddInterestBinding.inflate(inflater, container, false)
        itemInterestChipBinding = ItemInterestChipBinding.inflate(inflater, container, false)

        // Inflate the layout for this fragment
        return addInterestBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupActionBar(addInterestBinding.addInterestToolbar)

        //chip = layoutInflater.inflate(R.layout.item_interest_chip,R.layout.fragment_add_interest, false)
        ///chip = LayoutInflater.from(requireContext()).inflate(R.layout.item_interest_chip, null) as Chip

        interests.clear()
        prefManager.setInterestList(interests, requireContext())

        interests.add("Books")
        interests.add("Football")
        interests.add("Travel")
        interests.add("Cartoons")
        interests.add("Karaoke")
        interests.add("Messi")
        interests.add("Foreign Language")
        interests.add("Ronaldo")
        interests.add("Music")
        interests.add("Basketball")
        interests.add("Parties")
        interests.add("Politics")
        interests.add("Christianity")
        interests.add("Islam")

        setInterestChips(interests)
       // checkForPickedInterests()
    }

    //Configure the behaviour of picking the interests, adding it to an arrraylist
    private fun setInterestChips(interests : ArrayList<String>) {
        for(interest in interests) {
            val chip =
                LayoutInflater.from(requireContext()).inflate(R.layout.item_interest_chip, null) as Chip

            chip.text = interest
            newChip = chip

            chip.setOnClickListener {
                if (chip.isChecked == true){
                    chip.setTextColor(resources.getColor(R.color.custom_red))
                    chosenInterestList.add(chip.text.toString())
                    activateContinueButton()

                }else{
                    chip.setTextColor(resources.getColor(R.color.black))
                    chosenInterestList.remove(chip.text.toString())
                    activateContinueButton()
                }
            }
            //if(addInterestBinding.root > 0) addInterestBinding.interestChipGroup.removeAllViews()


            //addInterestBinding.root.addView(chip)
            addInterestBinding.interestChipGroup.addView(chip)
        }
    }

    private fun activateContinueButton(){
        if (chosenInterestList.size > 0) {
            resetContinueBtnToClickable2(addInterestBinding.tvContinue, inputPasswordFragment,
                prefManager, chosenInterestList ,requireContext())

        } else {
            Toast.makeText(requireContext(), "No chosen interest", Toast.LENGTH_LONG).show()
            revertContinueBtnToUnclickable(addInterestBinding.tvContinue)
        }
    }

/*
    private fun checkForPickedInterests(){
        if(prefManager.getInterestList(requireContext())!!.isNotEmpty()){
            for (i in prefManager.getInterestList(requireContext())!!){
                for(j in interests) {
                    if (i == j) {
                        Log.d("interests picked: ", i)

                        if (newChip.text == i) {
                            Log.d("interests matches: ", newChip.text.toString())

                            newChip.isChecked = true
                            newChip.setTextColor(resources.getColor(R.color.custom_red))
                            chosenInterestList.add(newChip.text.toString())
                        }

                    }
                }
            }
        }
    }
*/

}
