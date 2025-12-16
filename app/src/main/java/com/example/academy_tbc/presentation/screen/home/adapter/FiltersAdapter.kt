package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemFilterCheckboxGroupBinding
import com.example.academy_tbc.databinding.ItemFilterPriceRangeBinding
import com.example.academy_tbc.presentation.extension.hideKeyboard
import com.example.academy_tbc.presentation.screen.home.model.FilterUi
import com.example.academy_tbc.presentation.screen.home.model.SelectedFilters


class FiltersAdapter(
    private val onFilterChanged: (SelectedFilters) -> Unit,
) :
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

            else ->
                PriceRangeViewHolder(
                    ItemFilterPriceRangeBinding.inflate(inflater, parent, false)
                )

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

    inner class CheckboxGroupViewHolder(
        private val binding: ItemFilterCheckboxGroupBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterUi.CheckboxGroup) = with(binding) {
            tvTitle.text = item.titleRes?.let { root.context.getString(it) }
            container.removeAllViews()

            item.options.forEach { option ->
                val checkBox = CheckBox(root.context).apply {
                    text = option.labelRes?.let { root.context.getString(it) } ?: option.label
                    isChecked = option.isChecked

                    setOnCheckedChangeListener { _, checked ->
                        option.isChecked = checked
                        onFilterChanged(getCurrentFilters())
                    }
                }
                container.addView(checkBox)
            }
        }
    }

    inner class PriceRangeViewHolder(
        private val binding: ItemFilterPriceRangeBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: FilterUi.PriceRange) = with(binding) {
            etMin.setText(item.minPrice.orEmpty())
            etMax.setText(item.maxPrice.orEmpty())

            etMin.isFocusableInTouchMode = true
            etMin.imeOptions = EditorInfo.IME_ACTION_DONE
            etMax.isFocusableInTouchMode = true
            etMax.imeOptions = EditorInfo.IME_ACTION_DONE

            etMin.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    etMin.clearFocus()
                    root.hideKeyboard()
                    true
                } else false
            }
            etMax.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    etMax.clearFocus()
                    root.hideKeyboard()
                    true
                } else false
            }

            etMin.addTextChangedListener {
                item.minPrice = it?.toString()
                onFilterChanged(getCurrentFilters())
            }

            etMax.addTextChangedListener {
                item.maxPrice = it?.toString()
                onFilterChanged(getCurrentFilters())
            }
        }
    }

    private fun getCurrentFilters(): SelectedFilters {
        var minPrice: Float? = null
        var maxPrice: Float? = null
        val selectedOptions = mutableMapOf<String, List<String>>()

        currentList.forEach { filter ->
            when (filter) {
                is FilterUi.PriceRange -> {
                    minPrice = filter.minPrice?.toFloatOrNull()
                    maxPrice = filter.maxPrice?.toFloatOrNull()
                }

                is FilterUi.CheckboxGroup -> {
                    val checkedIds = filter.options
                        .filter { it.isChecked }
                        .map { it.id }
                    if (checkedIds.isNotEmpty()) selectedOptions[filter.filterKey] = checkedIds
                }
            }
        }

        return SelectedFilters(minPrice, maxPrice, selectedOptions)
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