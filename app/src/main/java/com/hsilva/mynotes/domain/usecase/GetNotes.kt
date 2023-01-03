package com.hsilva.mynotes.domain.usecase

import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.repository.NotesRepository
import com.hsilva.mynotes.domain.util.NoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NotesRepository
) {

    operator fun invoke(
        order: NoteOrder = NoteOrder.Date
    ): Flow<List<Note>> =
        repository.getAll()
            .map { notes ->
                when (order) {
                    is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                    is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                    is NoteOrder.Color -> notes.sortedBy { it.color }
                }
            }
}