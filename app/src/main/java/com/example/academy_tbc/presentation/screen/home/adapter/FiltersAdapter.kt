package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemFilterCheckboxGroupBinding
import com.example.academy_tbc.databinding.ItemFilterPriceRangeBinding
import com.example.academy_tbc.presentation.screen.home.model.FilterUi



class FiltersAdapter :
    ListAdapter<FilterUi, RecyclerView.ViewHolder>(ItemFilterDiffUtil()) {

    companion object {
        private const val TYPE_CHECKBOX_GROUP = 0
        private const val TYPE_PRICE_RANGE = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is FilterUi.CheckboxGroup -> TYPE_CHECKBOX_GROUP
            is FilterUi.PriceRange -> TYPE_PRICE_RANGE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CHECKBOX_GROUP ->
                CheckboxGroupViewHolder(
                    ItemFilterCheckboxGroupBinding.inflate(inflater, parent, false)
                )

            TYPE_PRICE_RANGE ->
                PriceRangeViewHolder(
                    ItemFilterPriceRangeBinding.inflate(inflater, parent, false)
                )

            else -> error("Unknown viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FilterUi.CheckboxGroup ->
                (holder as CheckboxGroupViewHolder).bind(item)

            is FilterUi.PriceRange ->
                (holder as PriceRangeViewHolder).bind(item)
        }
    }
    class CheckboxGroupViewHolder(
        private val binding: ItemFilterCheckboxGroupBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterUi.CheckboxGroup) {
            binding.tvTitle.text = item.title

            binding.container.removeAllViews()

            item.options.forEach { option ->
                val checkBox = CheckBox(binding.root.context).apply {
                    text = option.label
                    isChecked = option.isChecked
                }
                binding.container.addView(checkBox)
            }
        }
    }

    class PriceRangeViewHolder(
        private val binding: ItemFilterPriceRangeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterUi.PriceRange) {
            binding.etMin.setText(item.minPrice.orEmpty())
            binding.etMax.setText(item.maxPrice.orEmpty())
        }
    }


}

class ItemFilterDiffUtil : DiffUtil.ItemCallback<FilterUi>() {
    override fun areItemsTheSame(
        oldItem: FilterUi, newItem: FilterUi,
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: FilterUi, newItem: FilterUi,
    ): Boolean {
        return oldItem == newItem
    }

}