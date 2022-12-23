package com.hsilva.mynotes.presentation

import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.util.NoteOrder

sealed class NoteEvent {
    data class Edit(val note: Note): NoteEvent()
    data class Order(val noteOrder: NoteOrder): NoteEvent()
    data class DeleteNote(val note: Note): NoteEvent()
    data class RestoreNote(val note: Note): NoteEvent()
}