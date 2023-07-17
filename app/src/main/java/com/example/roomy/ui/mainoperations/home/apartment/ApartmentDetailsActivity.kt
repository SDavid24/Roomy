package com.example.roomy.ui.mainoperations.home.apartment

import android.app.Activity
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.roomy.R
import com.example.roomy.databinding.ActivityApartmentDetailsBinding
import com.example.roomy.dataobject.Apartment

class ApartmentDetailsActivity : AppCompatActivity() {
    private lateinit var apartmentDetailsBinding: ActivityApartmentDetailsBinding
    lateinit var adapter : ApartmentDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        apartmentDetailsBinding = ActivityApartmentDetailsBinding.inflate(layoutInflater)
        setContentView(apartmentDetailsBinding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView(){
        val list = ArrayList<Apartment>()

        val apartImages1 = ArrayList<Int>()
        apartImages1.add(R.drawable.apartment6)
        apartImages1.add(R.drawable.apartment4)
        apartImages1.add(R.drawable.apartment5)
        apartImages1.add(R.drawable.apartment3)
        apartImages1.add(R.drawable.apartment2)
        val apartImage2 = ArrayList<Int>()
        apartImage2.add(R.drawable.apartment5)
        val apartImage3 = ArrayList<Int>()
        apartImage3.add(R.drawable.apartment4)

        list.add(
            Apartment(1,apartImages1, "Two bedroom office space",  "Semi-furnished","Ketu",
                "Lagos state", "₦500,000")
        )
        list.add(
            Apartment(2,apartImage2, "One bedroom studio apartment ",  "Semi-furnished","Lekki",
                "Lagos state", "₦1,200,000")
        )
        list.add(
            Apartment(3, apartImage3, "Three bedroom apartment",  "Fully-furnished","Bodija",
                "Oyo state", "₦800,000")
        )
       /* list.add(
            Apartment(4, apartImage1, "Duplex apartment building",  "Not-furnished","Mowe",
                "Ogun state", "₦300,000")
        )
        list.add(
            Apartment(5, apartImage2, "Self-contain",  "Semi-furnished","Jericho",
                "Oyo state", "₦250,000")
        )*/


        val adapter = ApartmentDetailsAdapter(list, this)
        apartmentDetailsBinding.rvApartmentDetails.adapter = adapter
        apartmentDetailsBinding.rvApartmentDetails.layoutManager = LinearLayoutManager(this)

        //Ensures only one card display in the screen snapping out which ever is less positioned in it
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(apartmentDetailsBinding.rvApartmentDetails)

        apartmentDetailsBinding.rvApartmentDetails.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        adapter.setOnClickListener(object : ApartmentDetailsAdapter.OnClickListener {
            override fun onClick(cardPosition: Int) {
              /*  if (ApartmentDetailsActivity) {
                }*/
            }

        })

      /*  // Register the OnGlobalLayoutListener when creating the ViewHolder
        adapter.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remove the listener to avoid multiple callbacks
                adapter.ApartmentDetailsViewHolder(binding).itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Retrieve the position of the first visible item
                val currentPosition = (ApartmentDetailsViewHolder(binding).images.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                // Use the currentPosition for further operations
                ApartmentDetailsViewHolder(binding).itemView.setOnClickListener {
                    Toast.makeText(this, "The apartment in Oncreate currently displayed is: $currentPosition", Toast.LENGTH_SHORT).show()
                }
            }
        })*/


        adapter.notifyDataSetChanged()
    }

}