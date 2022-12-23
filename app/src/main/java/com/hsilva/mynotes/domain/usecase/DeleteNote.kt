package com.hsilva.mynotes.domain.usecase

import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.repository.NotesRepository

class DeleteNote(
    private val repository: NotesRepository
) {

    suspend operator fun invoke(note: Note) =
        repository.delete(note)
}