package com.example.academy_tbc.presentation.home.form.usecase

import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.common.errorOrNull
import com.example.academy_tbc.domain.common.getOrNull
import com.example.academy_tbc.domain.common.isFailure
import com.example.academy_tbc.domain.common.isSuccess
import com.example.academy_tbc.domain.model.form.FieldType
import com.example.academy_tbc.domain.model.form.Form
import com.example.academy_tbc.domain.model.form.HintType
import com.example.academy_tbc.domain.repository.form.FormRepository
import com.example.academy_tbc.domain.usecase.form.GetFormsUseCase
import com.example.academy_tbc.presentation.base.BaseUnitTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetFormsUseCaseTest : BaseUnitTest() {

    private val repository = mockk<FormRepository>()
    private val getFormsUseCase = GetFormsUseCase(repository)

    @Test
    fun `invoke returns forms successfully`() = runTest(testDispatcher) {
        val fakeForm = Form(
            form = listOf(
                listOf(
                    Form.Field(
                        fieldId = 1,
                        hint = HintType.FULLNAME,
                        fieldType = FieldType.INPUT,
                        keyboard = "text",
                        required = true,
                        isActive = true,
                        icon = "icon_url"
                    )
                )
            )
        )

        coEvery { repository.getForms() } returns Resource.Success(fakeForm)

        val result = getFormsUseCase()

        assertTrue(result.isSuccess())
        assertEquals(fakeForm.form, result.getOrNull())
    }

    @Test
    fun `invoke returns failure`() = runTest(testDispatcher) {
        val error = DataError.Network.NO_CONNECTION
        coEvery { repository.getForms() } returns Resource.Failure(error)

        val result = getFormsUseCase()

        assertTrue(result.isFailure())
        assertEquals(error, result.errorOrNull())
    }
}