package com.example.academy_tbc.presentation.home.viewmodel

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.form.FieldType
import com.example.academy_tbc.domain.model.form.Form
import com.example.academy_tbc.domain.model.form.HintType
import com.example.academy_tbc.domain.usecase.form.GetFormsUseCase
import com.example.academy_tbc.presentation.base.BaseUnitTest
import com.example.academy_tbc.presentation.screen.home.HomeEvent
import com.example.academy_tbc.presentation.screen.home.HomeSideEffect
import com.example.academy_tbc.presentation.screen.home.HomeViewModel
import com.example.academy_tbc.presentation.util.toStringResId
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest : BaseUnitTest() {

    private val getFormsUseCase = mockk<GetFormsUseCase>()
    private val viewModel = HomeViewModel(getFormsUseCase)

    @Test
    fun `getForms updates state on success`() = runTest(testDispatcher) {
        val fakeField = Form.Field(
            fieldId = 1,
            hint = HintType.FULLNAME,
            fieldType = FieldType.INPUT,
            keyboard = "text",
            required = true,
            isActive = true,
            icon = "icon_url"
        )

        val fakeFormsFields: List<List<Form.Field>> = listOf(listOf(fakeField))

        coEvery { getFormsUseCase() } returns Resource.Success(fakeFormsFields)

        viewModel.onEvent(HomeEvent.GetForms)
        advanceUntilIdle()

        val state = viewModel.state.first()
        val firstField = state.groups.first().first()

        assertEquals(fakeField.fieldId, firstField.fieldId)
        assertEquals(fakeField.fieldType, firstField.fieldType)
    }

    @Test
    fun `getForms emits snackbar on failure`() = runTest(testDispatcher) {
        val error = DataError.Network.NO_CONNECTION
        coEvery { getFormsUseCase() } returns Resource.Failure(error)

        viewModel.onEvent(HomeEvent.GetForms)
        advanceUntilIdle()

        val sideEffect = viewModel.sideEffect.first()
        assertTrue(sideEffect is HomeSideEffect.ShowSnackBar)
        assertEquals(error.toStringResId(), (sideEffect as HomeSideEffect.ShowSnackBar).errorRes)
    }
}