package com.hsilva.mynotes.domain.util

sealed class NoteOrder {
    object Title : NoteOrder()
    object Date : NoteOrder()
    object Color : NoteOrder()
}