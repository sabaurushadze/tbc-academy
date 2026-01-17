package com.example.academy_tbc.presentation


import app.cash.turbine.test
import com.example.academy_tbc.domain.common.DataError
import com.example.academy_tbc.domain.common.Resource
import com.example.academy_tbc.domain.model.category.Category
import com.example.academy_tbc.domain.model.outfit.Currency
import com.example.academy_tbc.domain.model.outfit.Outfit
import com.example.academy_tbc.domain.model.outfit.OutfitCategory
import com.example.academy_tbc.domain.usecase.category.GetCategoriesUseCase
import com.example.academy_tbc.domain.usecase.outfit.GetOutfitsByCategoryIdUseCase
import com.example.academy_tbc.domain.usecase.outfit.GetOutfitsUseCase
import com.example.academy_tbc.presentation.screen.home.HomeEvent
import com.example.academy_tbc.presentation.screen.home.HomeSideEffect
import com.example.academy_tbc.presentation.screen.home.HomeViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    private lateinit var viewModel: HomeViewModel

    private val getCategoriesUseCase: GetCategoriesUseCase = mockk()
    private val getOutfitsUseCase: GetOutfitsUseCase = mockk()
    private val getOutfitsByCategoryIdUseCase: GetOutfitsByCategoryIdUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    private val dummyCategories = listOf(
        Category(1, "Party"),
        Category(2, "Sports")
    )

    val dummyOutfits = listOf(
        Outfit(
            id = 1,
            category = OutfitCategory.PARTY,
            name = "Outfit1",
            image = "https://fastly.picsum.photos/id/830/536/354.jpg?hmac=M5EsVFyBxZR708JhsNqDjIbvm0CMgKZ_rOjrXCI5KYw",
            price = Outfit.Price(amount = 10.0, currency = Currency.GEL)
        ),
        Outfit(
            id = 2,
            category = OutfitCategory.CAMPING,
            name = "Outfit2",
            image = "https://fastly.picsum.photos/id/237/536/354.jpg?hmac=i0yVXW1ORpyCZpQ-CknuyV-jbtU7_x9EBQVhvT5aRr0",
            price = Outfit.Price(amount = 20.0, currency = Currency.USD)
        )
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        coEvery { getCategoriesUseCase() } returns Resource.Success(dummyCategories)
        coEvery { getOutfitsUseCase() } returns Resource.Success(dummyOutfits)
        coEvery { getOutfitsByCategoryIdUseCase(any()) } returns Resource.Success(dummyOutfits)

        viewModel = HomeViewModel(
            getCategoriesUseCase,
            getOutfitsUseCase,
            getOutfitsByCategoryIdUseCase
        )

        testDispatcher.scheduler.advanceUntilIdle()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads categories and outfits successfully`() = runTest {
        val state = viewModel.state.value

        assertEquals(2, state.categories.size)
        assertEquals(2, state.outfits.size)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `clicking a category updates outfits and selectedCategoryId`() = runTest {
        val categoryId = 2
        val updatedOutfits = listOf(
            Outfit(
                3, OutfitCategory.CAMPING, "Outfit3", "https://fastly.picsum.photos/id/237/536/354.jpg?hmac=i0yVXW1ORpyCZpQ-CknuyV-jbtU7_x9EBQVhvT5aRr0", Outfit.Price(
                    2.2,
                    Currency.GEL
                )
            )
        )
        coEvery { getOutfitsByCategoryIdUseCase(categoryId) } returns Resource.Success(
            updatedOutfits
        )

        viewModel.onEvent(HomeEvent.CategoryClicked(categoryId))
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(categoryId, state.selectedCategoryId)
        assertEquals(updatedOutfits.size, state.outfits.size)
    }

    @Test
    fun `clicking favorite toggles favorite list`() = runTest {
        val outfitId = 1

        assertEquals(0, viewModel.state.value.favoriteOutfits.size)

        viewModel.onEvent(HomeEvent.FavoriteClicked(outfitId))
        assertEquals(listOf(outfitId), viewModel.state.value.favoriteOutfits)

        viewModel.onEvent(HomeEvent.FavoriteClicked(outfitId))
        assertEquals(emptyList<Int>(), viewModel.state.value.favoriteOutfits)
    }

    @Test
    fun `getCategoriesUseCase failure emits side effect`() = runTest {
        coEvery { getCategoriesUseCase() } returns Resource.Failure(DataError.Network.INTERNAL_SERVER_ERROR)

        viewModel = HomeViewModel(
            getCategoriesUseCase,
            getOutfitsUseCase,
            getOutfitsByCategoryIdUseCase
        )

        viewModel.sideEffect.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val effect = awaitItem()
            assert(effect is HomeSideEffect.ShowSnackBar)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getOutfitsUseCase failure emits side effect`() = runTest {
        coEvery { getOutfitsUseCase() } returns Resource.Failure(DataError.Network.TIMEOUT)

        viewModel = HomeViewModel(
            getCategoriesUseCase,
            getOutfitsUseCase,
            getOutfitsByCategoryIdUseCase
        )

        viewModel.sideEffect.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val effect = awaitItem()
            assert(effect is HomeSideEffect.ShowSnackBar)
            cancelAndIgnoreRemainingEvents()
        }
    }
}