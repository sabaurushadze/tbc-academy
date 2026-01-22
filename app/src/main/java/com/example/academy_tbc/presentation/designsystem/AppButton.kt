package com.example.academy_tbc.presentation.designsystem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import com.example.academy_tbc.presentation.theme.AppColor
import com.example.academy_tbc.presentation.theme.AppDimens
import com.example.academy_tbc.presentation.theme.AppRadius
import com.example.academy_tbc.presentation.theme.AppTextStyle

@Composable
fun AppButtonOutlined(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    border: BorderStroke = BorderStroke(width = AppDimens.size1, color = AppColor.onBackground),
    shape: Shape = AppRadius.radius8,
    textStyle: TextStyle = AppTextStyle.body14Medium,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier.height(AppDimens.size36),
        onClick = onClick,
        border = border,
        enabled = enabled,
        shape = shape,
        contentPadding = contentPadding
    ) {
        Text(text = text, style = textStyle)
    }

}
