package com.vroom_psshh.notes20.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vroom_psshh.notes20.roomDB.NotesDatabase
import com.vroom_psshh.notes20.roomDB.UserInput

class NotesRepository(private val notesDatabase: NotesDatabase) {

    //here we have used Encapsulation ln 11,12,13
    //this live data is to update the database
    /*private val notesLiveData = MutableLiveData<UserInput>()
    fun addNotesLiveData(userInput: UserInput){
        notesLiveData.value = userInput
    }
    val notes: LiveData<UserInput>
        get() = notesLiveData*/
    //

    fun getNotes():LiveData<List<UserInput>> = notesDatabase.notesDao().getNotes()

    fun addNotes(userInput: List<UserInput>){
        notesDatabase.notesDao().addNotes(userInput)
    }
}