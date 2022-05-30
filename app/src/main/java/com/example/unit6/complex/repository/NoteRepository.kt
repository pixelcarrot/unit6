package com.example.unit6.complex.repository

import com.example.unit6.complex.api.NoteService
import com.example.unit6.complex.api.model.NoteApiModel
import com.example.unit6.complex.model.Note

interface NoteRepository {
    suspend fun getNotes(): List<Note>

    suspend fun submitNote(message: String): String
}

class NoteRepositoryImpl(private val api: NoteService) : NoteRepository {

    override suspend fun getNotes(): List<Note> {
        return api.getNotes()
            .map { (id, apiModel) ->
                Note(
                    id = id,
                    message = apiModel.message,
                    timestamp = apiModel.timestamp
                )
            }
            .sortedByDescending {
                it.timestamp
            }
    }

    override suspend fun submitNote(message: String): String {
        val data = NoteApiModel().also {
            it.message = message
        }
        return api.submitNote(data).name
    }

}
