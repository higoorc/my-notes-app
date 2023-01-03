package com.hsilva.mynotes.presentation.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.hsilva.mynotes.R
import com.hsilva.mynotes.databinding.FragmentNotesBinding
import com.hsilva.mynotes.domain.model.Note
import com.hsilva.mynotes.domain.util.NoteOrder
import com.hsilva.mynotes.domain.util.OrderType
import com.hsilva.mynotes.presentation.NoteEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModels()

    lateinit var adapter: NotesAdapter
    lateinit var notesRecycler: RecyclerView
    lateinit var filterVisibility: ImageView
    lateinit var filterButtons: RadioGroup
    lateinit var newNote: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNotesBinding
            .inflate(inflater, container, false)

        filterVisibility = binding.filter
        filterButtons = binding.radioButtonsRg
        notesRecycler = binding.notesList
        newNote = binding.addFab

        filterVisibility.setOnClickListener { toggleVisibility(filterButtons) }
        newNote.setOnClickListener { navigateToEditor() }
        setFilterButtons()

        adapter = NotesAdapter(requireContext(), this::emitEvent)
        notesRecycler.adapter = adapter
        notesRecycler.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.observeListState().observe(viewLifecycleOwner) { adapter.submitList(it) }

        viewModel.observeEditState().observe(viewLifecycleOwner) { note ->
            if (note != null) {
                navigateToEditor(note)
            }
        }

        viewModel.observeDeletedNote().observe(viewLifecycleOwner) { note ->
            if (note != null) {
                Snackbar.make(requireView(), "Your note was deleted.", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { emitEvent(NoteEvent.RestoreNote(note)) }
                    .setActionTextColor(requireContext().getColor(R.color.orange))
                    .show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        viewModel.clearData()
        super.onDestroyView()
    }

    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE)
            View.GONE else View.VISIBLE
    }

    private fun emitEvent(event: NoteEvent) =
        viewModel.onEvent(event)

    private fun navigateToEditor(note: Note? = null) {
        val direction = NotesFragmentDirections.notesToEditor().setNote(note)
        findNavController().navigate(direction)
    }

    private fun setFilterButtons() {
        filterButtons.setOnCheckedChangeListener { _, id ->
            val event = when (id) {
                R.id.radio_button1 -> NoteEvent.Order(NoteOrder.Title(OrderType.Ascending))
                R.id.radio_button2 -> NoteEvent.Order(NoteOrder.Date(OrderType.Ascending))
                R.id.radio_button3 -> NoteEvent.Order(NoteOrder.Color(OrderType.Ascending))
                else -> throw Exception()
            }

            emitEvent(event)
        }
    }

}