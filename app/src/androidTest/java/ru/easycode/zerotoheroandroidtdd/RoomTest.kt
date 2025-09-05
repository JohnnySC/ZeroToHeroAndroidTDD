package ru.easycode.zerotoheroandroidtdd

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Also check Task043Test and ListViewModelTest
 */
@RunWith(AndroidJUnit4::class)
class RoomTest {

    private lateinit var dao: RecordsDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.dao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun test() = runBlocking {
        val flow: Flow<List<RecordEntity>> = dao.list()
        assertEquals(emptyList<RecordEntity>(), flow.first())

        dao.insert(RecordEntity(id = 1L, text = "one"))
        assertEquals(
            listOf(
                RecordEntity(id = 1L, text = "one")
            ), flow.first()
        )

        dao.insert(RecordEntity(id = 2L, text = "one"))
        assertEquals(
            listOf(
                RecordEntity(id = 2L, text = "one"),
                RecordEntity(id = 1L, text = "one"),
            ), flow.first()
        )

        dao.insert(RecordEntity(id = 2L, text = "two"))
        assertEquals(
            listOf(
                RecordEntity(id = 2L, text = "two"),
                RecordEntity(id = 1L, text = "one"),
            ), flow.first()
        )
    }
}