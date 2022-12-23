package com.hsilva.mynotes.data.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hsilva.mynotes.domain.model.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    // if the id already exists, just will update.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)

    // returns a flow, so don't need to be suspend.
    @Query("SELECT * FROM note")
    fun getAll(): Flow<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    suspend fun getById(id: Int): Note?

    @Delete
    suspend fun delete(note: Note)
}