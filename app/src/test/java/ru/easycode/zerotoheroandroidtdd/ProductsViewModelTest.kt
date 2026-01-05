package ru.easycode.zerotoheroandroidtdd

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductsViewModelTest {

    private lateinit var viewModel: ProductsViewModel
    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var repository: FakeProductsRepository
    private lateinit var runAsync: FakeRunAsync

    @Before
    fun setup() {
        savedStateHandle = SavedStateHandle()
        repository = FakeProductsRepository()
        runAsync = FakeRunAsync()
        viewModel = ProductsViewModel(
            savedStateHandle = savedStateHandle,
            repository = repository,
            runAsync = runAsync,
        )
    }

    @Test
    fun scenario() {
        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(
                    id = 1,
                    name = "Device A",
                    price = "300$",
                    os = "Android",
                    ram = 6
                ),
                ProductListUi.Base(id = 3, name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductListUi.Base(
                    id = 2,
                    name = "Device C",
                    price = "200$",
                    os = "Android",
                    ram = 4
                ),
                ProductListUi.Base(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8)
            ),
            viewModel.productsUiListStateFlow.value
        )
        assertEquals(
            listOf<OrderUi>(
                OrderUi(name = "alphabet", chosen = true),
                OrderUi(name = "price: low to high", chosen = false),
                OrderUi(name = "price: high to low", chosen = false)
            ),
            viewModel.ordersUiListStateFlow.value
        )

        viewModel.chooseOrder(name = "price: low to high")

        assertEquals(
            listOf<OrderUi>(
                OrderUi(name = "alphabet", chosen = false),
                OrderUi(name = "price: low to high", chosen = true),
                OrderUi(name = "price: high to low", chosen = false)
            ),
            viewModel.ordersUiListStateFlow.value
        )

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(
                    id = 2,
                    name = "Device C",
                    price = "200$",
                    os = "Android",
                    ram = 4
                ),
                ProductListUi.Base(
                    id = 1,
                    name = "Device A",
                    price = "300$",
                    os = "Android",
                    ram = 6
                ),
                ProductListUi.Base(id = 3, name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductListUi.Base(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8)
            ),
            viewModel.productsUiListStateFlow.value
        )

        viewModel.chooseOrder(name = "price: high to low")

        assertEquals(
            listOf<OrderUi>(
                OrderUi(name = "alphabet", chosen = false),
                OrderUi(name = "price: low to high", chosen = false),
                OrderUi(name = "price: high to low", chosen = true)
            ),
            viewModel.ordersUiListStateFlow.value
        )

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8),
                ProductListUi.Base(id = 3, name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductListUi.Base(
                    id = 1,
                    name = "Device A",
                    price = "300$",
                    os = "Android",
                    ram = 6
                ),
                ProductListUi.Base(
                    id = 2,
                    name = "Device C",
                    price = "200$",
                    os = "Android",
                    ram = 4
                ),
            ),
            viewModel.productsUiListStateFlow.value
        )

        assertEquals(
            listOf<FilterUi>(
                FilterUi(id = 1, category = "os", value = "Android", chosen = false),
                FilterUi(id = 2, category = "os", value = "iOS", chosen = false),
                FilterUi(id = 3, category = "RAM", value = "4", chosen = false),
                FilterUi(id = 4, category = "RAM", value = "6", chosen = false),
                FilterUi(id = 5, category = "RAM", value = "8", chosen = false),
            ),
            viewModel.filtersUiListStateFlow.value
        )

        viewModel.chooseFilter(id = 1)

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(
                    id = 1,
                    name = "Device A",
                    price = "300$",
                    os = "Android",
                    ram = 6
                ),
                ProductListUi.Base(
                    id = 2,
                    name = "Device C",
                    price = "200$",
                    os = "Android",
                    ram = 4
                ),
            ),
            viewModel.productsUiListStateFlow.value
        )

        assertEquals(
            listOf<FilterUi>(
                FilterUi(id = 1, category = "os", value = "Android", chosen = true),
                FilterUi(id = 2, category = "os", value = "iOS", chosen = false),
                FilterUi(id = 3, category = "RAM", value = "4", chosen = false),
                FilterUi(id = 4, category = "RAM", value = "6", chosen = false),
                FilterUi(id = 5, category = "RAM", value = "8", chosen = false),
            ),
            viewModel.filtersUiListStateFlow.value
        )

        viewModel.chooseFilter(id = 3)

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(
                    id = 2,
                    name = "Device C",
                    price = "200$",
                    os = "Android",
                    ram = 4
                ),
            ),
            viewModel.productsUiListStateFlow.value
        )

        assertEquals(
            listOf<FilterUi>(
                FilterUi(id = 1, category = "os", value = "Android", chosen = true),
                FilterUi(id = 2, category = "os", value = "iOS", chosen = false),
                FilterUi(id = 3, category = "RAM", value = "4", chosen = true),
                FilterUi(id = 4, category = "RAM", value = "6", chosen = false),
                FilterUi(id = 5, category = "RAM", value = "8", chosen = false),
            ),
            viewModel.filtersUiListStateFlow.value
        )

        viewModel.chooseFilter(id = 5)

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Empty
            ),
            viewModel.productsUiListStateFlow.value
        )

        assertEquals(
            listOf<FilterUi>(
                FilterUi(id = 1, category = "os", value = "Android", chosen = true),
                FilterUi(id = 2, category = "os", value = "iOS", chosen = false),
                FilterUi(id = 3, category = "RAM", value = "4", chosen = false),
                FilterUi(id = 4, category = "RAM", value = "6", chosen = false),
                FilterUi(id = 5, category = "RAM", value = "8", chosen = true),
            ),
            viewModel.filtersUiListStateFlow.value
        )

        viewModel.chooseFilter(id = 2)

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8)
            ),
            viewModel.productsUiListStateFlow.value
        )

        assertEquals(
            listOf<FilterUi>(
                FilterUi(id = 1, category = "os", value = "Android", chosen = false),
                FilterUi(id = 2, category = "os", value = "iOS", chosen = true),
                FilterUi(id = 3, category = "RAM", value = "4", chosen = false),
                FilterUi(id = 4, category = "RAM", value = "6", chosen = false),
                FilterUi(id = 5, category = "RAM", value = "8", chosen = true),
            ),
            viewModel.filtersUiListStateFlow.value
        )

        viewModel.unchooseFilter(id = 5)

        assertEquals(
            listOf<FilterUi>(
                FilterUi(id = 1, category = "os", value = "Android", chosen = false),
                FilterUi(id = 2, category = "os", value = "iOS", chosen = true),
                FilterUi(id = 3, category = "RAM", value = "4", chosen = false),
                FilterUi(id = 4, category = "RAM", value = "6", chosen = false),
                FilterUi(id = 5, category = "RAM", value = "8", chosen = false),
            ),
            viewModel.filtersUiListStateFlow.value
        )

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8),
                ProductListUi.Base(id = 3, name = "Device B", price = "400$", os = "iOS", ram = 6)
            ),
            viewModel.productsUiListStateFlow.value
        )

        assertEquals(
            listOf<OrderUi>(
                OrderUi(name = "alphabet", chosen = false),
                OrderUi(name = "price: low to high", chosen = false),
                OrderUi(name = "price: high to low", chosen = true)
            ),
            viewModel.ordersUiListStateFlow.value
        )

        viewModel.chooseOrder(name = "alphabet")

        assertEquals(
            listOf<ProductListUi>(
                ProductListUi.Base(id = 3, name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductListUi.Base(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8),
            ),
            viewModel.productsUiListStateFlow.value
        )

        assertEquals(
            listOf<OrderUi>(
                OrderUi(name = "alphabet", chosen = true),
                OrderUi(name = "price: low to high", chosen = false),
                OrderUi(name = "price: high to low", chosen = false)
            ),
            viewModel.ordersUiListStateFlow.value
        )
    }
}

private class FakeRunAsync : RunAsync {

    override fun <T : Any> runFlowCollect(
        scope: CoroutineScope,
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    ) {
        scope.launch(Dispatchers.Unconfined) {
            flow.collect(collect)
        }
    }
}

private class FakeProductsRepository : ProductsRepository {

    override suspend fun products(): List<Product> {
        return listOf(
            Product(id = 1, name = "Device A", price = "300$", os = "Android", ram = 6),
            Product(id = 2, name = "Device C", price = "200$", os = "Android", ram = 4),
            Product(id = 3, name = "Device B", price = "400$", os = "iOS", ram = 6),
            Product(id = 4, name = "Device D", price = "500$", os = "iOS", ram = 8)
        )
    }

    override suspend fun orderList(): List<String> {
        return listOf(
            "alphabet",
            "price: low to high",
            "price: high to low",
        )
    }

    override suspend fun filters(): List<ProductFilter> {
        return listOf(
            ProductFilter(id = 1, category = "os", name = "Android"),
            ProductFilter(id = 2, category = "os", name = "iOS"),
            ProductFilter(id = 3, category = "RAM", name = "4"),
            ProductFilter(id = 4, category = "RAM", name = "6"),
            ProductFilter(id = 5, category = "RAM", name = "8"),
        )
    }
}