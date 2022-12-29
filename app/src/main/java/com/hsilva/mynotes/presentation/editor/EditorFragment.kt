package com.hsilva.mynotes.presentation.editor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hsilva.mynotes.R
import com.hsilva.mynotes.databinding.FragmentEditorBinding
import com.hsilva.mynotes.domain.model.Note
import dagger.hilt.android.AndroidEntryPoint
import java.time.Instant
import java.util.*

@AndroidEntryPoint
class EditorFragment : Fragment() {

    private val viewModel: EditorViewModel by viewModels()

    private val args: EditorFragmentArgs by navArgs()

    private lateinit var noteCard: CardView
    private lateinit var noteTitle: EditText
    private lateinit var noteContent: EditText
    private lateinit var purpleButton: ImageView
    private lateinit var orangeButton: ImageView
    private lateinit var blueButton: ImageView
    private lateinit var tealButton: ImageView
    private lateinit var yellowButton: ImageView
    private lateinit var saveButton: FloatingActionButton

    private var selectedColor: Int = R.color.teal
    private lateinit var currentNote: Note

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentEditorBinding
            .inflate(inflater, container, false)

        noteCard = binding.noteCard
        noteTitle = binding.noteTitle
        noteContent = binding.noteContent
        purpleButton = binding.colorPurple
        orangeButton = binding.colorOrange
        blueButton = binding.colorBlue
        tealButton = binding.colorTeal
        yellowButton = binding.colorYellow
        saveButton = binding.addFab

        purpleButton.setOnClickListener { setColorListener(R.color.purple, it) }
        orangeButton.setOnClickListener { setColorListener(R.color.orange, it) }
        blueButton.setOnClickListener { setColorListener(R.color.blue, it) }
        tealButton.setOnClickListener { setColorListener(R.color.teal, it) }
        yellowButton.setOnClickListener { setColorListener(R.color.yellow, it) }
        saveButton.setOnClickListener { saveNote() }

        args.note.let {
            if (it != null) {
                currentNote = it
                noteTitle.setText(currentNote.title)
                noteContent.setText(currentNote.content)
                selectedColor = currentNote.color
            }
        }

        noteCard.setCardBackgroundColor(resources.getColor(selectedColor))
        return binding.root
    }

    private fun setColorListener(color: Int, view: View) {
/*        purpleButton.visibility = View.VISIBLE
        orangeButton.visibility = View.VISIBLE
        blueButton.visibility = View.VISIBLE
        tealButton.visibility = View.VISIBLE
        yellowButton.visibility = View.VISIBLE
        view.visibility = View.GONE*/

        // TODO: Color not worked as expected, wrong colors and buttons visibility.
        selectedColor = color
        noteCard.setCardBackgroundColor(resources.getColor(selectedColor))
    }

    private fun saveNote() {
        val title = noteTitle.text.toString()
        val content = noteContent.text.toString()

        // TODO: Improve error handling with UI messages about note not created.
        if (title.isNotEmpty() && content.isNotEmpty()) {
            val timestamp = Date.from(Instant.now()).time
            var newNote = Note(
                title = title,
                content = content,
                timestamp = timestamp,
                color = selectedColor
            )

            if (this::currentNote.isInitialized) {
                newNote = newNote.copy(id = currentNote.id)
            }

            viewModel.saveNote(newNote)
        }

        findNavController().navigateUp()
    }
}