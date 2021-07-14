package com.x.roomwithview

import androidx.annotation.WorkerThread
import com.x.roomwithview.bean.Word
import com.x.roomwithview.dao.WordDao
import kotlinx.coroutines.flow.Flow

/**
 * Author : Ray
 * Time : 7/14/21 3:28 PM
 * Description :
 */
class WordRepository(private val wordDao: WordDao) {

    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }

}