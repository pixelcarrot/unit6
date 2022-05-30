package com.example.unit6.complex.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.unit6.R
import com.example.unit6.complex.api.NoteApi
import com.example.unit6.complex.model.Note
import com.example.unit6.complex.repository.NoteRepositoryImpl
import com.example.unit6.complex.viewmodel.NoteViewModel
import com.example.unit6.complex.viewmodel.NoteViewModelFactory
import java.util.concurrent.TimeUnit

class ReminderDialogFragment(private val note: Note) : DialogFragment() {

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(NoteRepositoryImpl(NoteApi.retrofitService))
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
                .setTitle("Note Reminder")
                .setItems(R.array.note_schedule_array) { _, position ->
                    when (position) {
                        0 -> viewModel.scheduleReminder(requireContext(), 5, TimeUnit.SECONDS, note)
                        1 -> viewModel.scheduleReminder(
                            requireContext(),
                            15,
                            TimeUnit.SECONDS,
                            note
                        )
                        2 -> viewModel.scheduleReminder(
                            requireContext(),
                            30,
                            TimeUnit.SECONDS,
                            note
                        )
                        3 -> viewModel.scheduleReminder(requireContext(), 1, TimeUnit.MINUTES, note)
                    }
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}
