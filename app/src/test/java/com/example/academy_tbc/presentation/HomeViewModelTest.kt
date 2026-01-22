package com.example.academy_tbc.presentation


import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.order.BorderDelayDetails
import com.example.academy_tbc.domain.model.order.Order
import com.example.academy_tbc.domain.model.order.OrderStatus
import com.example.academy_tbc.domain.usecase.order.GetOrdersByStatusUseCase
import com.example.academy_tbc.domain.usecase.order.UpdateOrderUseCase
import com.example.academy_tbc.presentation.screen.home.HomeEvent
import com.example.academy_tbc.presentation.screen.home.HomeViewModel
import com.example.academy_tbc.presentation.screen.home.orders.enums.OrdersTab
import com.example.academy_tbc.presentation.screen.home.orders.mapper.toPresentation
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertFalse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel

    private val getOrdersUseCase: GetOrdersByStatusUseCase = mockk()
    private val updateOrderUseCase: UpdateOrderUseCase = mockk()

    private val orders = listOf(
        Order(
            id = 1,
            status = OrderStatus.PENDING,
            orderName = OrderStatus.PENDING.name,
            trackingNumber = "TK131231384",
            quantity = "4",
            deliveryDate = "12/12/2026",
            subtotal = "$290",
            details = BorderDelayDetails(
                borderCountry = "Mexico",
                reason = "Illegal drugs",

                estimatedDelayDays = 29
            )
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        viewModel = HomeViewModel(
            getOrdersUseCase,
            updateOrderUseCase,
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getOrders success updates state`() = runTest {
        coEvery { getOrdersUseCase(OrdersTab.PENDING.status) } returns
                Resource.Success(orders)

        viewModel.onEvent(HomeEvent.GetOrders)

        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(orders.map { it.toPresentation() }, state.orders)
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `getOrders error clears orders and stops loading`() = runTest {
        coEvery { getOrdersUseCase(OrdersTab.PENDING.status) } returns
                Resource.Failure(DataError.Network.NO_CONNECTION)

        viewModel.onEvent(HomeEvent.GetOrders)
        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(emptyList(), state.orders)
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
    }

    @Test
    fun `changing tab fetches orders for new status`() = runTest {
        val shippedOrders = listOf(
            orders.first().copy(
                id = 2,
                status = OrderStatus.DELIVERED,
                orderName = OrderStatus.DELIVERED.name
            )
        )

        coEvery { getOrdersUseCase(OrdersTab.DELIVERED.status) } returns
                Resource.Success(shippedOrders)

        viewModel.onEvent(HomeEvent.TabSelected(OrdersTab.DELIVERED))
        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(
            shippedOrders.map { it.toPresentation() },
            state.orders
        )
    }

    @Test
    fun `updateOrder success refreshes orders`() = runTest {
        coEvery {
            updateOrderUseCase(1, OrderStatus.DELIVERED)
        } returns Resource.Success(Unit)

        coEvery {
            getOrdersUseCase(OrdersTab.PENDING.status)
        } returns Resource.Success(orders)

        viewModel.onEvent(
            HomeEvent.UpdateOrder(
                id = 1,
                status = OrderStatus.DELIVERED
            )
        )

        testScheduler.advanceUntilIdle()

        val state = viewModel.state.value

        assertEquals(
            orders.map { it.toPresentation() },
            state.orders
        )
        assertFalse(state.isLoading)
        assertFalse(state.isRefreshing)
    }


}