package com.vroom_psshh.notes20.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vroom_psshh.notes20.repository.NotesRepository
import com.vroom_psshh.notes20.roomDB.UserInput

class MainViewModelFactory(private val repository: NotesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}