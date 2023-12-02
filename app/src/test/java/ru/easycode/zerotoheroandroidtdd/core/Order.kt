package ru.easycode.zerotoheroandroidtdd.core

import org.junit.Assert

class Order {

    private val list = mutableListOf<String>()

    fun add(name: String) {
        list.add(name)
    }

    fun check(expected: List<String>) {
        Assert.assertEquals(expected, list)
    }
}