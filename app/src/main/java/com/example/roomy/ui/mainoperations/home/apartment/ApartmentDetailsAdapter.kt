package com.example.roomy.ui.mainoperations.home.apartment

import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.example.roomy.R
import com.example.roomy.databinding.ItemApartmentDetailsBinding
import com.example.roomy.dataobject.Apartment
import com.example.roomy.ui.mainoperations.profile.HousePostedRVAdapter
import com.example.roomy.utils.SwipeToNextItemCallback

class ApartmentDetailsAdapter(private var apartments: List<Apartment>, private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val imageList: ArrayList<Int> = ArrayList() // Replace with your actual list of image resources or URLs
    private var currentIndex = 0
    private lateinit var childTouchListener: RecyclerView.OnItemTouchListener
    lateinit var apartmentImagesAdapter : ApartmentImagesAdapter
    private var onClickListener : OnClickListener? = null

    inner class ApartmentDetailsViewHolder(binding: ItemApartmentDetailsBinding) : RecyclerView.ViewHolder(binding.root) {
        var images = binding.imagesRecyclerView
        var rentPrice = binding.tvRentPrice
        var location = binding.tvLocation
        var description = binding.tvDescription
        var furnishStatus = binding.tvFurnishStatus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)  : ApartmentDetailsAdapter.ApartmentDetailsViewHolder {
        val binding = ItemApartmentDetailsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        // Register the OnGlobalLayoutListener when creating the ViewHolder
        ApartmentDetailsViewHolder(binding).itemView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // Remove the listener to avoid multiple callbacks
                ApartmentDetailsViewHolder(binding).itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // Retrieve the position of the first visible item
                val currentPosition = (ApartmentDetailsViewHolder(binding).images.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()

                // Use the currentPosition for further operations
                ApartmentDetailsViewHolder(binding).itemView.setOnClickListener {
                    Toast.makeText(context, "The apartment in Oncreate currently displayed is: $currentPosition", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return ApartmentDetailsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = apartments[position]

        if (holder is ApartmentDetailsAdapter.ApartmentDetailsViewHolder) {

            holder.location.text = "${model.area}, ${model.state}"
            holder.description.text = model.description
            holder.furnishStatus.text = model.furnishStatus
            holder.rentPrice.text = model.rentPrice

            apartmentImagesAdapter = ApartmentImagesAdapter(model.image, context, model.id)
            holder.images.adapter = apartmentImagesAdapter

            imagesSwipeListener(holder.images, apartmentImagesAdapter)


            //Ensures only one card display in the screen snapping out which ever is less positioned in it
            val snapHelper: SnapHelper = PagerSnapHelper()
            snapHelper.attachToRecyclerView(holder.images)

            holder.images.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            apartmentImagesAdapter.notifyDataSetChanged()


            holder.itemView.setOnClickListener {
                if (onClickListener != null){
                    onClickListener!!.onClick(position)
                }
            }


            holder.itemView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    // Remove the listener to avoid multiple callbacks
                    holder.itemView.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    // Retrieve the position of the first visible item
                    val currentPosition = (holder.images.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                    // Use the currentPosition for further operations
                    holder.itemView.setOnClickListener {
                        Toast.makeText(context, "The apartment currently displayed is: " +
                                "$currentPosition",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            })


        }
    }


    override fun getItemCount(): Int {
        return apartments.size
    }


    //It allows the images tied to
    fun imagesSwipeListener(recyclerView: RecyclerView, adapter: ApartmentImagesAdapter){
        itemTouchHelper = ItemTouchHelper(SwipeToNextItemCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        childTouchListener = object : RecyclerView.OnItemTouchListener {
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                when (e.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        // Disable touch interception for the parent RecyclerView when the touch event starts on the child RecyclerView
                        rv.parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
                return false
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                // No action needed
            }

            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                // No action needed
            }
        }

        recyclerView.addOnItemTouchListener(childTouchListener)
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener{
        fun onClick(cardPosition: Int)
    }
}