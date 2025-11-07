package com.example.academy_tbc.box

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemBoxBinding
import com.example.academy_tbc.field.Field
import com.example.academy_tbc.field.FieldAdapter
import com.example.academy_tbc.field.FieldsViewModel

class BoxAdapter(private val boxes: List<FieldBox>, private val viewModel: FieldsViewModel) :
    RecyclerView.Adapter<BoxAdapter.BoxViewHolder>() {

    private val adapters = mutableListOf<FieldAdapter>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoxViewHolder {
        return BoxViewHolder(
            ItemBoxBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: BoxViewHolder, position: Int) {
        val fieldBox = boxes[position]
        holder.bind(fieldBox)
    }

    override fun getItemCount(): Int = boxes.size

    inner class BoxViewHolder(private val binding: ItemBoxBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(box: FieldBox) {
            val innerAdapter = FieldAdapter(viewModel)
            innerAdapter.submitList(box.fields)
            adapters.add(innerAdapter)

            binding.rvBox.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = innerAdapter
            }
        }
    }

    fun getAllFields(): List<Field> = boxes.flatMap { it.fields }
}