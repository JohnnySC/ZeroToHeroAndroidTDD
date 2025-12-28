package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class Task044UiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun order_and_filter() {
        val productsPage = ProductsPage()
        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device A", price = "300$", os = "Android", ram = 6),
                ProductUi(name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductUi(name = "Device C", price = "200$", os = "Android", ram = 4),
                ProductUi(name = "Device D", price = "500$", os = "iOS", ram = 8),
            )
        )
        productsPage.openOrderSettings()
        val orderSettingsPage = OrderSettingsPage()
        orderSettingsPage.assertChosen("alphabet")

        orderSettingsPage.choose("price: low to high")

        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device C", price = "200$", os = "Android", ram = 4),
                ProductUi(name = "Device A", price = "300$", os = "Android", ram = 6),
                ProductUi(name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductUi(name = "Device D", price = "500$", os = "iOS", ram = 8),
            )
        )

        productsPage.openOrderSettings()
        orderSettingsPage.assertChosen("price: low to high")
        orderSettingsPage.choose("price: high to low")

        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device D", price = "500$", os = "iOS", ram = 8),
                ProductUi(name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductUi(name = "Device A", price = "300$", os = "Android", ram = 6),
                ProductUi(name = "Device C", price = "200$", os = "Android", ram = 4),
            )
        )

        productsPage.openFilterSettings()

        val filterSettingsPage = FilterSettingsPage()
        filterSettingsPage.assertNothingChosen()

        filterSettingsPage.choose("os" to "Android")

        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device A", price = "300$", os = "Android", ram = 6),
                ProductUi(name = "Device C", price = "200$", os = "Android", ram = 4),
            )
        )

        productsPage.openFilterSettings()
        filterSettingsPage.assertChosen("os" to "Android")
        filterSettingsPage.choose("RAM" to 4)

        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device C", price = "200$", os = "Android", ram = 4),
            )
        )

        productsPage.openFilterSettings()
        filterSettingsPage.assertChosen("os" to "Android")
        filterSettingsPage.assertChosen("RAM" to 4)

        filterSettingsPage.choose("RAM" to 8)

        productsPage.assertNothingFound()

        productsPage.openFilterSettings()
        filterSettingsPage.assertChosen("os" to "Android")
        filterSettingsPage.assertChosen("RAM" to 8)

        filterSettingsPage.choose("os" to "iOS")
        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device D", price = "500$", os = "iOS", ram = 8),
            )
        )

        productsPage.openFilterSettings()
        filterSettingsPage.assertChosen("os" to "iOS")
        filterSettingsPage.assertChosen("RAM" to 8)

        filterSettingsPage.unchoose("RAM")

        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device D", price = "500$", os = "iOS", ram = 8),
                ProductUi(name = "Device B", price = "400$", os = "iOS", ram = 6),
            )
        )

        productsPage.openOrderSettings()
        orderSettingsPage.assertChosen("price: high to low")

        orderSettingsPage.choose("alphabet")
        productsPage.assertProducts(
            listOf(
                ProductUi(name = "Device B", price = "400$", os = "iOS", ram = 6),
                ProductUi(name = "Device D", price = "500$", os = "iOS", ram = 8),
            )
        )
    }
}