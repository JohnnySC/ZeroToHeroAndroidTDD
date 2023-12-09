package ru.easycode.zerotoheroandroidtdd.folder.core

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.easycode.zerotoheroandroidtdd.core.FolderCache
import ru.easycode.zerotoheroandroidtdd.core.FoldersDao
import ru.easycode.zerotoheroandroidtdd.note.core.FakeNotesDao
import ru.easycode.zerotoheroandroidtdd.note.core.Now

class FoldersRepositoryTest {

    @Test
    fun test() = runBlocking {
        val now = FakeNow.Base(7L)
        val foldersDao = FakeFoldersDao.Base()
        val notesDao = FakeNotesDao.Base()
        val repository = FoldersRepository.Base(
            now = now,
            foldersDao = foldersDao,
            notesDao = notesDao
        )

        val firstFolderId = repository.createFolder(name = "first")
        assertEquals(7L, firstFolderId)
        val secondFolderId = repository.createFolder(name = "second")
        assertEquals(8L, secondFolderId)

        val foldersInitialActual: List<Folder> = repository.folders()
        val foldersInitialExpected = listOf(
            Folder(id = 7L, title = "first", notesCount = 0),
            Folder(id = 8L, title = "second", notesCount = 0)
        )
        assertEquals(foldersInitialExpected, foldersInitialActual)

        repository.delete(folderId = 7L)
        notesDao.checkDeleteCalledWith(folderId = 7L)

        repository.rename(folderId = 8L, newName = "new name for second")

        val foldersFinalListActual: List<Folder> = repository.folders()
        val foldersFinalListExpected = listOf(
            Folder(id = 8L, title = "new name for second", notesCount = 0)
        )
        assertEquals(foldersFinalListExpected, foldersFinalListActual)
    }
}

interface FakeNow : Now {

    class Base(private var time: Long) : FakeNow {

        override fun timeInMillis(): Long {
            return time++
        }
    }
}

private interface FakeFoldersDao : FoldersDao {

    class Base : FakeFoldersDao {

        private val map = mutableMapOf<Long, FolderCache>()

        override suspend fun insert(folder: FolderCache) {
            map[folder.id] = folder
        }

        override suspend fun delete(folderId: Long) {
            map.remove(folderId)
        }

        override suspend fun folders(): List<FolderCache> {
            return map.map { it.value }.toList()
        }
    }
}
