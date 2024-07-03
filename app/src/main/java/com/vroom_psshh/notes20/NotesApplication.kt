package com.vroom_psshh.notes20

import android.app.Application
import com.vroom_psshh.notes20.repository.NotesRepository
import com.vroom_psshh.notes20.roomDB.NotesDatabase

class NotesApplication: Application() {

    lateinit var notesRepository: NotesRepository

    override fun onCreate() {
        super.onCreate()
     initialize()
    }

    private fun initialize() {
        val database = NotesDatabase.getDatabase(applicationContext)
        notesRepository = NotesRepository(database)

    }
}