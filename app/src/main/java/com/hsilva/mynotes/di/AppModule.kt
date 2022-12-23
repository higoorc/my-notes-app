package com.hsilva.mynotes.di

import android.app.Application
import androidx.room.Room
import com.hsilva.mynotes.data.repository.NotesRepositoryImpl
import com.hsilva.mynotes.data.source.NotesDatabase
import com.hsilva.mynotes.domain.repository.NotesRepository
import com.hsilva.mynotes.domain.usecase.AddNote
import com.hsilva.mynotes.domain.usecase.DeleteNote
import com.hsilva.mynotes.domain.usecase.GetNotes
import com.hsilva.mynotes.domain.usecase.NotesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNotesDatabase(app: Application): NotesDatabase =
        Room.databaseBuilder(
            app,
            NotesDatabase::class.java,
            NotesDatabase.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun provideNotesRepository(db: NotesDatabase): NotesRepository =
        NotesRepositoryImpl(db.noteDao)

    @Provides
    @Singleton
    fun provideNotesManager(repository: NotesRepository): NotesManager =
        NotesManager(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository)
        )

}