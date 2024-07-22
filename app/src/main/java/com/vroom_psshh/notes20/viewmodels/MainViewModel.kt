package com.vroom_psshh.notes20.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vroom_psshh.notes20.repository.NotesRepository
import com.vroom_psshh.notes20.roomDB.UserInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (private val repository: NotesRepository): ViewModel() {

    fun addNotes(userInput: UserInput){
        viewModelScope.launch(Dispatchers.IO){
            repository.addNotes(listOf(userInput))
        }
    }
    fun updateNotes(userInput: UserInput){
        viewModelScope.launch(Dispatchers.IO){
            repository.updateNotes(userInput)
        }
    }
    val notesLiveData: LiveData<List<UserInput>> = repository.getNotes()
}