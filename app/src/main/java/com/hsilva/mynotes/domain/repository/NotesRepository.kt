package com.hsilva.mynotes.domain.repository

import com.hsilva.mynotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

// Interface for easy fake repository creation during tests.
interface NotesRepository {

    fun getAll(): Flow<List<Note>>

    suspend fun getById(id: Int): Note?

    suspend fun insert(note: Note)

    suspend fun delete(note: Note)

}