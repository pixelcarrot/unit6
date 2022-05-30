package com.example.unit6.complex.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.unit6.complex.model.Note
import com.example.unit6.complex.repository.NoteRepository
import com.example.unit6.complex.worker.NoteReminderWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

data class NoteState(
    val recents: List<Note>
)

sealed class NoteEvent {
    object ShowLoading : NoteEvent()
    object HideLoading : NoteEvent()
    data class ShowMessage(val message: String) : NoteEvent()
}

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private val _state = MutableLiveData<NoteState>()
    val state: LiveData<NoteState> = _state

    private val _event = MutableLiveData<NoteEvent>()
    val event: LiveData<NoteEvent> = _event

    init {
        _state.value = NoteState(listOf())
    }

    fun submitNote(message: String) {
        viewModelScope.launch {
            try {
                _event.value = NoteEvent.ShowLoading
                val result = repository.submitNote(message)
                _event.value = NoteEvent.ShowMessage(result)
                _event.value = NoteEvent.HideLoading
                getNotes()
            } catch (ex: Exception) {
                _event.value = NoteEvent.HideLoading
                _event.value = NoteEvent.ShowMessage(ex.toString())
            }
        }
    }

    fun getNotes() {
        viewModelScope.launch {
            try {
                _event.value = NoteEvent.ShowLoading
                _state.value = _state.value?.copy(recents = repository.getNotes())
                _event.value = NoteEvent.HideLoading
            } catch (ex: Exception) {
                _event.value = NoteEvent.HideLoading
                _event.value = NoteEvent.ShowMessage(ex.toString())
            }
        }
    }

    fun scheduleReminder(context: Context, duration: Long, unit: TimeUnit, note: Note) {
        val work = OneTimeWorkRequestBuilder<NoteReminderWorker>()
            .setInitialDelay(duration, unit)
            .setInputData(workDataOf(NoteReminderWorker.NOTE_MESSAGE to note.message))
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(note.id, ExistingWorkPolicy.KEEP, work)
    }

}
