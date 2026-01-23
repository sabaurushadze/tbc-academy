package com.example.academy_tbc.presentation.designsystem

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import com.example.academy_tbc.presentation.theme.AppColor
import com.example.academy_tbc.presentation.theme.AppRadius
import com.example.academy_tbc.presentation.theme.AppTextStyle

@Composable
fun TextInputField(
    value: String,
    onTextChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String? = null,
    enabled: Boolean = true,
    errorText: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    imeAction: ImeAction = ImeAction.None,
    keyboardType: KeyboardType = KeyboardType.Unspecified,
    leadingIcon: (@Composable (() -> Unit))? = null,
) {
    TextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = AppColor.onBackground,
            unfocusedTextColor = AppColor.onBackground,
            focusedContainerColor = AppColor.neutral4,
            unfocusedContainerColor = AppColor.neutral4,
        ),
        label = label?.let {
            {
                Text(
                    text = it,
                    style = AppTextStyle.body14Medium
                )
            }
        },
        textStyle = AppTextStyle.body14Medium,
        onValueChange = { onTextChanged(it) },
        supportingText = errorText?.let {
            {
                Text(
                    text = it,
                    style = AppTextStyle.body14Medium,
                    color = AppColor.error
                )
            }
        },
        visualTransformation = visualTransformation,
        singleLine = true,
        isError = errorText != null,
        shape = AppRadius.radius20,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        leadingIcon = leadingIcon
    )
}
