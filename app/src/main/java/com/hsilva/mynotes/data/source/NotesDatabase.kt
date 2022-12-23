package com.hsilva.mynotes.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hsilva.mynotes.domain.model.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NotesDatabase : RoomDatabase() {

    abstract val noteDao: NotesDao

    companion object {
        const val DB_NAME = "notes_db"
    }
}