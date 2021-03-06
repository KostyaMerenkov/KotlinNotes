package com.edu.kotlinproject.ui.note

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.edu.kotlinproject.data.model.Note
import com.edu.kotlinproject.data.model.NoteResult
import com.edu.kotlinproject.data.model.Repository

import com.edu.kotlinproject.ui.BaseViewModel
import java.util.*


class NoteViewModel(private val repository: Repository = Repository) : BaseViewModel<Note?, NoteViewState>() {

    private var pendingNote: Note? = null

    fun saveChanges(note: Note) {
        pendingNote = note
    }

    override fun onCleared() {
        if (pendingNote != null) {
            repository.saveNote(pendingNote!!)
        }
    }

    fun loadNote(noteId: String) {
        repository.getNoteById(noteId).observeForever(object : Observer<NoteResult> {
            override fun onChanged(t: NoteResult?) {
                if (t == null) return

                when (t) {
                    is NoteResult.Success<*> ->
                        viewStateLiveData.value = NoteViewState(note = t.data as? Note)
                    is NoteResult.Error ->
                        viewStateLiveData.value = NoteViewState(error = t.error)
                }
            }
        })
    }

    fun getID(): String {
        return UUID.randomUUID().toString()
    }
}