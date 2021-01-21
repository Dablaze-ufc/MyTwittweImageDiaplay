package com.example.mytwittweimagediaplay.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mytwittweimagediaplay.databinding.ListItemImage1Binding
import com.example.mytwittweimagediaplay.databinding.ListItemImage2Binding
import com.example.mytwittweimagediaplay.databinding.ListItemImage3Binding

class ImagePreviewAdapter: ListAdapter<Uri, RecyclerView.ViewHolder>(DiffUtilCallback()) {

    class DiffUtilCallback: DiffUtil.ItemCallback<Uri>() {
        override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
            return oldItem == newItem
        }
    }

    override fun getItemCount() = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(currentList.size){
            1 -> OneImagePreviewViewHolder.from(parent)
            2 -> TwoImagePreviewViewHolder.from(parent)
            else ->ThreeImagePreviewViewHolder.from(parent)
        }
    }
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(currentList.size){
            1 ->{
                holder as OneImagePreviewViewHolder
                holder.bind(currentList)
            }

            2 ->{
                holder as TwoImagePreviewViewHolder
                holder.bind(currentList)
            }
            else -> {
                holder as ThreeImagePreviewViewHolder
                holder.bind(currentList)
            }
        }
    }

}

class OneImagePreviewViewHolder private constructor(private val binding : ListItemImage1Binding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: List<Uri>) = Glide.with(itemView.context).asBitmap().load(item[0]).into(binding.imagePreview)

    companion object{
        fun from(parent: ViewGroup): OneImagePreviewViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemImage1Binding.inflate(layoutInflater,parent,false)
            return OneImagePreviewViewHolder(binding)
        }
    }
}

class TwoImagePreviewViewHolder private constructor(private val binding : ListItemImage2Binding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: List<Uri>) = with(itemView){
        Glide.with(itemView.context).asBitmap().load(item[0]).into(binding.imagePreview1)
        Glide.with(itemView.context).asBitmap().load(item[1]).into(binding.imagePreview2)
    }

    companion object{
        fun from(parent: ViewGroup): TwoImagePreviewViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemImage2Binding.inflate(layoutInflater,parent,false)
            return TwoImagePreviewViewHolder(binding)
        }
    }
}



class ThreeImagePreviewViewHolder private constructor(private val binding : ListItemImage3Binding) : RecyclerView.ViewHolder(binding.root){

    fun bind(item: MutableList<Uri>) = with(itemView){
        Glide.with(itemView.context).asBitmap().load(item[0]).into(binding.imagePreview1)
        Glide.with(itemView.context).asBitmap().load(item[1]).into(binding.imagePreview2)
        Glide.with(itemView.context).asBitmap().load(item[2]).into(binding.imagePreview3)
    }

    companion object{
        fun from(parent: ViewGroup): ThreeImagePreviewViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemImage3Binding.inflate(layoutInflater,parent,false)
            return ThreeImagePreviewViewHolder(binding)
        }
    }
}