package com.hsilva.mynotes.presentation.editor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.usecase.NotesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val notesManager: NotesManager
) : ViewModel() {

    fun saveNote(note: Note) {
        viewModelScope.launch {
            notesManager.addNote(note)
        }
    }
}