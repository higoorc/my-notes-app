package com.hsilva.mynotes.presentation.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.usecase.NotesManager
import com.hsilva.mynotes.domain.util.NoteOrder
import com.hsilva.mynotes.presentation.NoteEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val notesManager: NotesManager
) : ViewModel() {

    private val listState = MutableLiveData<List<Note>>()
    fun observeListState(): LiveData<List<Note>> =
        listState

    private val editNote = MutableLiveData<Note?>()
    fun observeEditState(): LiveData<Note?> =
        editNote

    private val deletedNote = MutableLiveData<Note?>()
    fun observeDeletedNote(): LiveData<Note?> =
        deletedNote

    private var currentFilter: NoteOrder =
        NoteOrder.Date

    init {
        getNotes()
    }

    fun onEvent(event: NoteEvent) {
        Log.d("NotesViewModel", "EVENT - New event received ${event.javaClass}")

        when (event) {
            is NoteEvent.Edit -> {
                editNote.value = event.note
            }
            is NoteEvent.Order -> {
                if (isOrderAlreadyDefined(event)) {
                    return
                }

                currentFilter = event.noteOrder
                getNotes()
            }
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    notesManager.deleteNote(event.note)
                    deletedNote.value = event.note
                    getNotes()
                }
            }
            is NoteEvent.RestoreNote -> {
                viewModelScope.launch {
                    notesManager.addNote(event.note)
                    getNotes()
                }
            }
        }
    }

    fun clearData() {
        deletedNote.value = null
        editNote.value = null
    }

    private fun getNotes() =
        notesManager.getNotes(currentFilter)
            .onEach { notes -> listState.value = notes }
            .launchIn(viewModelScope)

    private fun isOrderAlreadyDefined(event: NoteEvent.Order) =
        currentFilter == event.noteOrder
}