package com.x.roomwithview

import android.app.Application
import com.x.roomwithview.db.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

/**
 * Author : Ray
 * Time : 7/14/21 5:54 PM
 * Description :
 */
class WordApplication : Application() {

    private val applicationScope = CoroutineScope(SupervisorJob())

    private val database by lazy { WordRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { WordRepository(database.wordDao()) }
}