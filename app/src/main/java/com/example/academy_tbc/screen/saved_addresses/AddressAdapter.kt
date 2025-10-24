package com.example.academy_tbc.screen.saved_addresses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemAddressBinding
import java.util.UUID

class AddressAdapter(
    private val onClick: (UUID) -> Unit,
    private val onAddressDelete: (UUID) -> Unit,
) : ListAdapter<AddressItem, AddressAdapter.AddressViewHolder>(AddressDiffUtils()) {

    private var selectedId: UUID? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        return AddressViewHolder(
            ItemAddressBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class AddressViewHolder(
        val binding: ItemAddressBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(addressItem: AddressItem) = with(binding) {
            tvAddress.text = addressItem.address
            tvAddressType.text = addressItem.addressType
            icAddress.setImageResource(addressItem.icon.resId)
            rbAddress.isChecked = addressItem.id == selectedId

            rbAddress.setOnClickListener {
                selectedId = if (rbAddress.isChecked) addressItem.id else null
                notifyDataSetChanged()
            }

            tvEdit.setOnClickListener {
                if (rbAddress.isChecked) {
                    onClick(addressItem.id)
                }
            }
            root.setOnLongClickListener {
                onAddressDelete(addressItem.id)
                true
            }
        }
    }
}

class AddressDiffUtils() : DiffUtil.ItemCallback<AddressItem>() {
    override fun areItemsTheSame(oldItem: AddressItem, newItem: AddressItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AddressItem, newItem: AddressItem): Boolean {
        return oldItem == newItem
    }
}