package com.example.academy_tbc.field

import android.app.DatePickerDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemEditTextBinding
import com.example.academy_tbc.databinding.ItemSpinnerBinding
import java.util.Calendar

class FieldAdapter(private val viewModel: FieldsViewModel) :
    ListAdapter<Field, RecyclerView.ViewHolder>(FieldDiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == EDIT_TEXT) {
            EdiTextViewHolder(
                ItemEditTextBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            SpinnerViewHolder(
                ItemSpinnerBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val field = getItem(position)
        if (holder is EdiTextViewHolder) {
            holder.bind(field)
        } else if (holder is SpinnerViewHolder) {
            holder.bind(field)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].fieldType == "input") EDIT_TEXT else SPINNER
    }

    inner class EdiTextViewHolder(private val binding: ItemEditTextBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(field: Field) {
            binding.et.hint = field.hint
            binding.et.inputType =
                if (field.keyboard == "number") InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT

            Glide.with(binding.root.context).load(field.icon).placeholder(R.drawable.ic_placeholder)
                .into(binding.iv)

            binding.et.addTextChangedListener {
                viewModel.updateFieldValue(field.fieldId, it?.toString())
            }

            binding.et.setText(viewModel.fieldValues.value[field.fieldId] ?: "")
        }
    }

    inner class SpinnerViewHolder(private val binding: ItemSpinnerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(field: Field) {

            if (field.hint == "Birthday") {
                binding.spinner.visibility = View.GONE
                binding.datePickerButton.visibility = View.VISIBLE

                binding.datePickerButton.text =
                    viewModel.fieldValues.value[field.fieldId] ?: "Select Date"

                binding.datePickerButton.setOnClickListener {
                    val calendar = Calendar.getInstance()
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val month = calendar.get(Calendar.MONTH)
                    val year = calendar.get(Calendar.YEAR)

                    val datePickerDialog = DatePickerDialog(
                        binding.root.context, { _, selectedYear, selectedMonth, selectedDay ->
                            val selectedDate = binding.root.context.getString(
                                R.string.dd_mm_yy, selectedDay, selectedMonth + 1, selectedYear
                            )
                            binding.datePickerButton.text = selectedDate
                            viewModel.updateFieldValue(field.fieldId, selectedDate)
                        }, year, month, day
                    )
                    datePickerDialog.show()
                }
            } else {
                binding.datePickerButton.visibility = View.GONE
                binding.spinner.visibility = View.VISIBLE

                binding.spinner.prompt = field.hint
                val spinnerAdapter = ArrayAdapter(
                    binding.root.context,
                    android.R.layout.simple_spinner_dropdown_item,
                    listOf("Select Gender", "Male", "Female")
                )
                binding.spinner.adapter = spinnerAdapter

                val previousValue = viewModel.fieldValues.value[field.fieldId]
                val index =
                    if (previousValue != null) spinnerAdapter.getPosition(previousValue) else 0
                binding.spinner.setSelection(if (index >= 0) index else 0)

                binding.spinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>, view: View?, position: Int, id: Long
                        ) {
                            val selected = parent.getItemAtPosition(position).toString()
                            viewModel.updateFieldValue(
                                field.fieldId, if (selected != "Select Gender") selected else null
                            )
                        }

                        override fun onNothingSelected(parent: AdapterView<*>) {
                            viewModel.updateFieldValue(field.fieldId, null)
                        }
                    }
            }
        }
    }

    companion object {
        const val EDIT_TEXT = 1
        const val SPINNER = 2
    }
}

class FieldDiffUtil : DiffUtil.ItemCallback<Field>() {
    override fun areItemsTheSame(oldItem: Field, newItem: Field): Boolean =
        oldItem.fieldId == newItem.fieldId

    override fun areContentsTheSame(oldItem: Field, newItem: Field): Boolean = oldItem == newItem
}