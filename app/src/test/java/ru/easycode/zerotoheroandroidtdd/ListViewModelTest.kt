package ru.easycode.zerotoheroandroidtdd

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Also check Task043UiTest and RoomTest
 */
class ListViewModelTest {

    @Test
    fun test() {
        val dao = FakeDao()
        val time = FakeTime()
        val runAsync = FakeRunAsync()
        val viewModel = ListViewModel(
            dao = dao,
            provideTime = time,
            runAsync = runAsync
        )

        val state: StateFlow<List<RecordEntity>> = viewModel.state
        assertEquals(emptyList<RecordEntity>(), state.value)

        viewModel.add(text = "text number one")
        assertEquals(
            listOf(
                RecordEntity(id = 1L, text = "text number one")
            ), dao.insertedList
        )

        viewModel.add(text = "text number two")
        assertEquals(
            listOf(
                RecordEntity(id = 1L, text = "text number one"),
                RecordEntity(id = 2L, text = "text number two"),
            ), dao.insertedList
        )
    }
}

private class FakeDao : RecordsDao {

    private val flow = MutableStateFlow<List<RecordEntity>>(listOf())

    override fun list(): Flow<List<RecordEntity>> {
        return flow
    }

    val insertedList = mutableListOf<RecordEntity>()

    override suspend fun insert(record: RecordEntity) {
        insertedList.add(record)
    }
}

private class FakeTime : ProvideTime {

    private var time = 0L

    override fun now(): Long {
        return ++time
    }
}

private class FakeRunAsync : RunAsync {

    override fun run(
        scope: CoroutineScope,
        background: suspend () -> Unit
    ) {
        runBlocking {
            background.invoke()
        }
    }
}