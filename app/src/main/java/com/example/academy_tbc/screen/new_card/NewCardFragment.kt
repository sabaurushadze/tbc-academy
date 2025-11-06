package com.example.academy_tbc.screen.new_card

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.academy_tbc.R
import com.example.academy_tbc.common.BaseFragment
import com.example.academy_tbc.databinding.FragmentNewCardBinding
import com.example.academy_tbc.screen.card_management.CardItem
import com.example.academy_tbc.utils.Validations
import com.example.academy_tbc.utils.Validations.KEY_CARD_HOLDER
import com.example.academy_tbc.utils.Validations.KEY_CARD_NUMBER
import com.example.academy_tbc.utils.Validations.KEY_CCV
import com.example.academy_tbc.utils.Validations.KEY_EXPIRED_CARD
import com.example.academy_tbc.utils.Validations.KEY_EXPIRE_DATE
import com.example.academy_tbc.viewmodel.CardViewModel
import kotlinx.coroutines.launch


class NewCardFragment : BaseFragment<FragmentNewCardBinding>(FragmentNewCardBinding::inflate) {

    private val viewModel: CardViewModel by activityViewModels()

    override fun bind() {
        observe()
    }

    override fun listeners() {
        setupCardTypeRadioButtons()
        setupExpiryFormatter()
        addNewCard()
    }

    private fun observe() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                val imageRes = when (state.selectedCardType) {
                    CardItem.CardType.VISA -> R.drawable.visa
                    CardItem.CardType.MASTERCARD -> R.drawable.mastercard
                }
                binding.ivCard.setImageResource(imageRes)

                binding.rbVisa.isChecked = state.selectedCardType == CardItem.CardType.VISA
                binding.rbMastercard.isChecked =
                    state.selectedCardType == CardItem.CardType.MASTERCARD
            }
        }
    }

    private fun setupCardTypeRadioButtons() {
        binding.rbVisa.setOnClickListener {
            binding.ivCard.setImageResource(R.drawable.visa)
            viewModel.changeCardType(CardItem.CardType.VISA)
        }
        binding.rbMastercard.setOnClickListener {
            binding.ivCard.setImageResource(R.drawable.mastercard)
            viewModel.changeCardType(CardItem.CardType.MASTERCARD)
        }
    }

    private fun addNewCard() = with(binding) {
        btnAddCard.setOnClickListener {
            val cardHolder = etCardHolderName.text.toString()
            val cardNumber = etCardNumber.text.toString()
            val cardExpireDate = etExpiresDate.text.toString()
            val cardCvv = etCvv.text.toString()

            val errors = Validations.validateCardDetails(
                context = requireContext(),
                cardHolder = cardHolder,
                cardNumber = cardNumber,
                expireDate = cardExpireDate,
                ccv = cardCvv
            )
            clearInputs()

            if (errors.isEmpty()) {
                viewModel.addNewCard(
                    cardNumber = etCardNumber.text.toString(),
                    cardHolder = etCardHolderName.text.toString(),
                    validThru = etExpiresDate.text.toString(),
                    cardType = viewModel.uiState.value.selectedCardType
                )
                viewModel.changeCardType(CardItem.CardType.MASTERCARD)
                findNavController().popBackStack()
            } else {
                errors.forEach { (field, message) ->
                    when (field) {
                        KEY_CARD_HOLDER -> etCardHolderName.error = message
                        KEY_CARD_NUMBER -> etCardNumber.error = message
                        KEY_EXPIRE_DATE -> etExpiresDate.error = message
                        KEY_EXPIRED_CARD -> etExpiresDate.error = message
                        KEY_CCV -> etCvv.error = message
                    }
                }
            }

        }
    }

    private fun setupExpiryFormatter() {
        binding.etExpiresDate.addTextChangedListener(object : TextWatcher {
            private var isEditing = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                if (isEditing) return
                isEditing = true

                var text = s.toString().replace("/", "")
                if (text.length > 4) text = text.take(4)

                val formatted = StringBuilder()
                for (i in text.indices) {
                    if (i == 2) formatted.append("/")
                    formatted.append(text[i])
                }

                if (formatted.length >= 2) {
                    val month = formatted.substring(0, 2).toIntOrNull()
                    if (month != null) {
                        if (month == 0) formatted.replace(0, 2, "01")
                        else if (month > 12) formatted.replace(0, 2, "12")
                    }
                }

                binding.etExpiresDate.setText(formatted)
                binding.etExpiresDate.setSelection(formatted.length)
                isEditing = false
            }
        })
    }

    private fun clearInputs() = with(binding) {
        etCardHolderName.error = null
        etCardNumber.error = null
        etExpiresDate.error = null
        etCvv.error = null
    }

}
