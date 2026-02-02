package com.tbc.home.presentation.screen.home

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tbc.core.presentation.base.BaseAsyncImage
import com.tbc.core.presentation.compositionlocal.LocalSnackbarHostState
import com.tbc.core.presentation.extension.collectEvent
import com.tbc.core_ui.theme.AppColor
import com.tbc.core_ui.theme.AppRadius
import com.tbc.core_ui.theme.AppTextStyle
import com.tbc.core_ui.theme.Dimen
import com.tbc.home.domain.model.form.FieldType
import com.tbc.home.presentation.model.form.UiForm
import com.tbc.resource.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(HomeEvent.GetForms)
    }

    viewModel.sideEffect.collectEvent { sideEffect ->
        when (sideEffect) {
            is HomeSideEffect.ShowSnackBar -> {
                val error = context.getString(sideEffect.errorRes)
                snackbarHostState.showSnackbar(message = error)
            }
        }
    }
    HomeContent(state = state, context = context)
}

@Composable
private fun HomeContent(
    state: HomeState,
    context: Context,
) {
    val fieldValues = remember { mutableStateMapOf<Int, String>() }
    val fieldErrors = remember { mutableStateMapOf<Int, String?>() }

    LazyColumn(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize()
            .background(AppColor.background),
        contentPadding = PaddingValues(Dimen.size16),
        verticalArrangement = Arrangement.spacedBy(Dimen.size16)
    ) {
        state.groups.forEach { field ->
            item {
                FieldGroupCard(
                    fields = field,
                    fieldValues = fieldValues,
                    fieldErrors = fieldErrors
                ) { fieldId, newValue ->
                    fieldValues[fieldId] = newValue
                    fieldErrors[fieldId] = null
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(Dimen.size8))
            Button(
                onClick = {
                    fieldErrors.clear()
                    state.groups.flatten().forEach { field ->
                        if (field.required && (fieldValues[field.fieldId].isNullOrBlank())) {
                            fieldErrors[field.fieldId] = context.getString(
                                R.string.field_not_filled_in,
                                context.getString(field.hint)
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(Dimen.size12),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppColor.primary
                )
            ) {
                Text(
                    text = stringResource(R.string.register),
                    style = AppTextStyle.body16Normal,
                    color = AppColor.onPrimary,
                    modifier = Modifier.padding(vertical = Dimen.size8)
                )
            }
        }
    }
}

@Composable
private fun FieldGroupCard(
    fields: List<UiForm.UiField>,
    fieldValues: Map<Int, String>,
    fieldErrors: Map<Int, String?>,
    onValueChange: (Int, String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(Dimen.size16),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = Dimen.size2)
    ) {
        Column(
            modifier = Modifier.padding(Dimen.size16),
            verticalArrangement = Arrangement.spacedBy(Dimen.size12)
        ) {
            fields.forEach { field ->
                FieldItem(
                    field = field,
                    value = fieldValues[field.fieldId] ?: "",
                    error = fieldErrors[field.fieldId]
                ) { newValue ->
                    onValueChange(field.fieldId, newValue)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FieldItem(
    field: UiForm.UiField,
    value: String,
    error: String?,
    onValueChange: (String) -> Unit
) {
    Column {
        when (field.fieldType) {
            FieldType.INPUT -> {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    isError = error != null,
                    placeholder = {
                        Text(
                            text = stringResource(field.hint),
                            style = AppTextStyle.body16Normal,
                            color = AppColor.onBackground
                        )
                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = AppColor.surface,
                        unfocusedContainerColor = AppColor.surface,
                        focusedIndicatorColor = AppColor.surface,
                        unfocusedIndicatorColor = AppColor.surface
                    ),
                    shape = AppRadius.radius8,
                    singleLine = true,
                    trailingIcon = {
                        BaseAsyncImage(
                            url = field.icon,
                            modifier = Modifier.size(Dimen.size24),
                            contentDescription = stringResource(field.hint)
                        )
                    }
                )
            }

            FieldType.CHOOSER -> {
                ChooserField(
                    field = field,
                    value = value,
                    onValueChange = onValueChange
                )
            }
        }

        error?.let {
            Spacer(modifier = Modifier.height(Dimen.size4))
            Text(
                text = error,
                color = AppColor.error,
                style = AppTextStyle.body16Bold,
                modifier = Modifier.padding(Dimen.size4)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooserField(
    field: UiForm.UiField,
    value: String,
    onValueChange: (String) -> Unit
) {
    val context = LocalContext.current

    TextField(
        value = value,
        onValueChange = { },
        readOnly = true,
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                when (field.chooserType) {
                    UiForm.UiField.ChooserType.DATE -> {
                        showDatePicker(context) { date ->
                            onValueChange(date)
                        }
                    }

                    UiForm.UiField.ChooserType.SELECTION -> {
                        showSelectionDialog(
                            context, listOf(
                                context.getString(R.string.male),
                                context.getString(R.string.female)
                            )
                        ) { selection ->
                            onValueChange(selection)
                        }
                    }

                    else -> {}
                }
            },
        placeholder = {
            Text(
                text = stringResource(field.hint),
                style = AppTextStyle.body16Normal,
                color = AppColor.onBackground
            )
        },
        trailingIcon = {
            BaseAsyncImage(
                url = field.icon,
                modifier = Modifier.size(Dimen.size24),
                contentDescription = stringResource(field.hint)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = AppColor.surface,
            unfocusedContainerColor = AppColor.surface,
            focusedIndicatorColor = AppColor.surface,
            unfocusedIndicatorColor = AppColor.surface,
            disabledContainerColor = AppColor.surface
        ),
        shape = AppRadius.radius8,
        singleLine = true
    )
}


@OptIn(ExperimentalTime::class)
fun showDatePicker(context: Context, onDateSelected: (String) -> Unit) {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

    android.app.DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate.toString())
        },
        today.year,
        today.month.number - 1,
        today.day
    ).show()
}

fun showSelectionDialog(context: Context, options: List<String>, onSelected: (String) -> Unit) {
    AlertDialog.Builder(context)
        .setItems(options.toTypedArray()) { _, which ->
            onSelected(options[which])
        }
        .show()
}