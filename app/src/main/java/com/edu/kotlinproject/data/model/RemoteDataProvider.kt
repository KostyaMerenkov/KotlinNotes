package com.edu.kotlinproject.data.model

import androidx.lifecycle.LiveData

interface RemoteDataProvider {

    fun subscribeToAllNotes(): LiveData<NoteResult>

    fun getNoteById(id: String): LiveData<NoteResult>

    fun saveNote(note: Note) : LiveData<NoteResult>

}