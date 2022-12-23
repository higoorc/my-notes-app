package com.hsilva.mynotes.data.repository

import com.hsilva.mynotes.data.source.NotesDao
import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.repository.NotesRepository
import kotlinx.coroutines.flow.Flow

// Simple use case like this don't requires much data transformation,
// but this repository is the layer for this.
class NotesRepositoryImpl(
    private val dao: NotesDao
) : NotesRepository {

    override fun getAll(): Flow<List<Note>> =
        dao.getAll()

    override suspend fun getById(id: Int): Note? =
        dao.getById(id)

    override suspend fun insert(note: Note) =
        dao.insert(note)

    override suspend fun delete(note: Note) =
        dao.delete(note)

}