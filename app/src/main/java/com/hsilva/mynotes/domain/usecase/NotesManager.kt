package com.hsilva.mynotes.domain.usecase

data class NotesManager(
    val getNotes: GetNotes,
    val deleteNote: DeleteNote,
    val addNote: AddNote
)