package com.example.academy_tbc.screen.card_management

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemCardBinding

class CardAdapter(
    val onCardClick: (String) -> Unit
) : ListAdapter<CardItem, CardAdapter.CardViewHolder>(CardDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CardViewHolder {
        return CardViewHolder(
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: CardViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class CardViewHolder(private val binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: CardItem) = with(binding) {
            tvCardNumber.text = card.cardNumber.chunked(4).joinToString(" ")
            tvCardHolderName.text = card.cardHolder
            tvValidThruDate.text = card.validThru
            when (card.cardType) {
                CardItem.CardType.VISA -> ivCard.setImageResource(R.drawable.visa)
                CardItem.CardType.MASTERCARD -> ivCard.setImageResource(R.drawable.mastercard)
            }

            root.setOnLongClickListener {
                onCardClick(card.id)
                true
            }
        }
    }
}

class CardDiffUtil : DiffUtil.ItemCallback<CardItem>() {
    override fun areItemsTheSame(
        oldItem: CardItem, newItem: CardItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: CardItem, newItem: CardItem
    ): Boolean {
        return oldItem == newItem
    }

}