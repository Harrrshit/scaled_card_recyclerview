package com.example.viewpager.adapter

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.viewpager.R
import com.example.viewpager.model.DataModel

class RecyclerViewAdapter(private val context: Context,
    private val list: ArrayList<DataModel>): RecyclerView.Adapter<RecyclerViewAdapter.CustomViewHolder>() {
    //private var hasInitParentDimensions = false
    //private var maxImageWidth: Int = 0
    //private var maxImageHeight: Int = 0
    //private var maxImageAspectRatio: Float = 1f
    class CustomViewHolder(view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.activity_imageFrame_image)
        val text: TextView = view.findViewById(R.id.activity_imageFrame_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.acitivity_image_frame, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val model = list[position]
        Glide
            .with(context)
            .load(model.image)
            .into(holder.image)
        holder.text.text = model.imageName
    }

    override fun getItemCount(): Int {
        return list.size
    }
}