package com.example.unit6.complex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.unit6.complex.repository.NoteRepository

class NoteViewModelFactory(private val repository: NoteRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            NoteViewModel(repository) as T
        } else {
            throw java.lang.IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
