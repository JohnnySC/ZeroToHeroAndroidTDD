package ru.easycode.zerotoheroandroidtdd.folder.details

import ru.easycode.zerotoheroandroidtdd.core.Order

interface FakeFolderLiveDataWrapper : FolderLiveDataWrapper.Mutable {

    fun check(expected: FolderUi)

    class Base(private val order: Order) : FakeFolderLiveDataWrapper {

        private lateinit var actual: FolderUi

        override fun check(expected: FolderUi) {
            assertEquals(expected, actual)
        }

        override fun update(folder: FolderUi) {
            order.add(UPDATE_FOLDER_LIVEDATA)
            actual = folder
        }
    }
}

const val UPDATE_FOLDER_LIVEDATA = "FolderLiveDataWrapper.Mutable#update"