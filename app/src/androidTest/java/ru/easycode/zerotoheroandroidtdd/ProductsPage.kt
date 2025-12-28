package ru.easycode.zerotoheroandroidtdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode

class ProductsPage(private val composeTestRule: ComposeTestRule) : AssertText {

    private val containerTag = "ProductsLazyColumn"

    private val orderButton =
        composeTestRule.onNode(
            hasTestTag("order button") and
                    hasText("order") and
                    hasClickAction()
        )

    private val filtersButton =
        composeTestRule.onNode(
            hasTestTag("filters button") and
                    hasText("filters") and
                    hasClickAction()
        )


    fun assertProducts(products: List<ProductUi>) {
        products.forEachIndexed { index, product ->
            product.assertUi(this, index)
        }
    }

    fun openOrderSettings() {
        orderButton.performClick()
    }

    fun openFilterSettings() {
        filtersButton.performClick()
    }

    fun assertNothingFound() {
        composeTestRule.onNodeWithTag(containerTag)
            .performScrollToNode(hasTestTag("nothing found"))
            .assertIsDisplayed()
    }

    override fun assertTextAtPosition(tag: String, position: Int, text: String): Unit =
        with(composeTestRule) {
            onNodeWithTag(containerTag)
                .performScrollToNode(hasTestTag("Product at $position"))
                .assertIsDisplayed()

            onNodeWithTag("Product $tag at $position", useUnmergedTree = true)
                .assertTextEquals(text)
        }
}

interface AssertText {
    fun assertTextAtPosition(tag: String, position: Int, text: String)
}