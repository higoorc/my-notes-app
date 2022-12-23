package com.hsilva.mynotes.domain.usecase

import com.hsilva.mynotes.domain.exceptions.InvalidNoteException
import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.repository.NotesRepository

class AddNote(
    private val repository: NotesRepository
) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("Title can't be empty.")
        }

        if (note.content.isBlank()) {
            throw InvalidNoteException("Content can't be empty")
        }

        repository.insert(note)
    }

}