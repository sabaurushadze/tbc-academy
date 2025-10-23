package com.example.academy_tbc.screen.outfit_shop.outfits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.OutfitCardItemBinding

class OutfitAdapter() :
    ListAdapter<OutfitItem, OutfitAdapter.OutfitsViewHolder>(OutfitDiffUtils()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitsViewHolder {
        return OutfitsViewHolder(
            OutfitCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: OutfitsViewHolder, position: Int) {
        val category = getItem(position)
        holder.bind(category)
    }

    inner class OutfitsViewHolder(
        val binding: OutfitCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(outfitItem: OutfitItem) = with(binding) {

            ivPerson.setImageResource(outfitItem.image)
            tvTitle.text = outfitItem.title
            tvPrice.text = "$${outfitItem.price}"


            ibHeart.setOnClickListener {
                if (bindingAdapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                if (!ibHeart.isActivated) {
                    ibHeart.imageTintList = ContextCompat.getColorStateList(root.context, R.color.heart)
                } else {
                    ibHeart.imageTintList = ContextCompat.getColorStateList(root.context, R.color.disabled)
                }
                ibHeart.isActivated = !ibHeart.isActivated
            }
        }
    }

}

class OutfitDiffUtils() : DiffUtil.ItemCallback<OutfitItem>() {
    override fun areItemsTheSame(oldItem: OutfitItem, newItem: OutfitItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OutfitItem, newItem: OutfitItem): Boolean {
        return oldItem == newItem
    }
}