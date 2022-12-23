package com.hsilva.mynotes.domain.usecase

import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.repository.NotesRepository
import com.hsilva.mynotes.domain.util.NoteOrder
import com.hsilva.mynotes.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NotesRepository
) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> =
        repository.getAll()
            .map { notes ->
                when (noteOrder.orderType) {
                    is OrderType.Ascending -> {
                        when (noteOrder) {
                            is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                            is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                            is NoteOrder.Color -> notes.sortedBy { it.color }
                        }
                    }
                    is OrderType.Descending -> {
                        when (noteOrder) {
                            is NoteOrder.Title -> notes.sortedByDescending { it.title.lowercase() }
                            is NoteOrder.Date -> notes.sortedByDescending { it.timestamp }
                            is NoteOrder.Color -> notes.sortedByDescending { it.color }
                        }
                    }
                }
            }
}