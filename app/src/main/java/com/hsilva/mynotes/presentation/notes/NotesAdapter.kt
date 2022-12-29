package com.hsilva.mynotes.presentation.notes

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hsilva.mynotes.R
import com.hsilva.mynotes.databinding.ItemNoteBinding
import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.presentation.NoteEvent
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter(
    private val context: Context,
    private val onClick: (NoteEvent) -> Unit
) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val notesList = mutableListOf<Note>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemNoteBinding.bind(itemView)

        lateinit var editButton: ImageView
        lateinit var deleteButton: ImageView

        fun bind(currentNote: Note) {
            binding.apply {
                noteTitle.text = currentNote.title
                noteContent.text = currentNote.content
                noteTimestamp.text = convertLongToTime(currentNote.timestamp)
                noteCard.setCardBackgroundColor(context.resources.getColor(currentNote.color))

                editButton = noteEdit
                deleteButton = noteDelete
            }
        }

        @SuppressLint("SimpleDateFormat")
        fun convertLongToTime(time: Long): String {
            val date = Date(time)
            val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
            return format.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            val note = notesList[position]
            bind(note)

            editButton.setOnClickListener { onClick(NoteEvent.Edit(note)) }
            deleteButton.setOnClickListener { onClick(NoteEvent.DeleteNote(note)) }
        }
    }

    override fun getItemCount(): Int =
        notesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newNotes: List<Note>) {
        notesList.clear()
        notesList.addAll(newNotes)
        notifyDataSetChanged()
    }

}