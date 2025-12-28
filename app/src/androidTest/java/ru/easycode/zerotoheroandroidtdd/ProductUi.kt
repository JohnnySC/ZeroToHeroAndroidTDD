package ru.easycode.zerotoheroandroidtdd

data class ProductUi(
    private val name: String,
    private val price: String,
    private val os: String,
    private val ram: Int
) {

    fun assertUi(assertText: AssertText, index: Int) = with(assertText) {
        assertTextAtPosition("name", index, name)
        assertTextAtPosition("os", index, os)
        assertTextAtPosition("ram", index, ram.toString())
        assertTextAtPosition("price", index, price)
    }
}