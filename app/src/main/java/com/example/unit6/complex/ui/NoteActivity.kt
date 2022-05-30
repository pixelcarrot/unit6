package com.example.unit6.complex.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.unit6.complex.api.NoteApi
import com.example.unit6.complex.repository.NoteRepositoryImpl
import com.example.unit6.complex.ui.adapter.NoteAdapter
import com.example.unit6.complex.ui.adapter.NoteListener
import com.example.unit6.complex.viewmodel.NoteEvent
import com.example.unit6.complex.viewmodel.NoteViewModel
import com.example.unit6.complex.viewmodel.NoteViewModelFactory
import com.example.unit6.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteBinding

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(NoteRepositoryImpl(NoteApi.retrofitService))
    }

    private val noteAdapter = NoteAdapter(NoteListener {
        val dialog = ReminderDialogFragment(it)
        dialog.show(supportFragmentManager, "NoteReminderDialogFragment")
        true
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.adapter = noteAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.state.observe(this, Observer { state ->
            noteAdapter.submitList(state.recents)
        })

        viewModel.event.observe(this, Observer { event ->
            when (event) {
                NoteEvent.HideLoading -> binding.progressBar.visibility = View.GONE
                NoteEvent.ShowLoading -> binding.progressBar.visibility = View.VISIBLE
                is NoteEvent.ShowMessage -> Toast.makeText(
                    this,
                    event.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })

        binding.btnSubmit.setOnClickListener {
            viewModel.submitNote(binding.etMessage.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }

}
