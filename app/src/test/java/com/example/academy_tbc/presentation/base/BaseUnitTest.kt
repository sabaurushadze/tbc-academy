package com.example.academy_tbc.presentation.base

import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@ExperimentalCoroutinesApi
abstract class BaseUnitTest {

    protected val testDispatcher = StandardTestDispatcher()

    @Before
    fun baseSetUp() {
        MockKAnnotations.init(this, relaxed = true)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun baseTearDown() {
        clearAllMocks()
        Dispatchers.resetMain()
    }

}