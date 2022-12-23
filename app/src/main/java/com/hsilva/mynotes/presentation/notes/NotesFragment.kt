package com.hsilva.mynotes.presentation.notes

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.hsilva.mynotes.databinding.FragmentNotesBinding
import com.hsilva.mynotes.presentation.NoteEvent
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModels()
    private val adapter = NotesAdapter(this::emitEvent)

    lateinit var notesRecycler: RecyclerView
    lateinit var filterVisibility: ImageView
    lateinit var filterButtons: RadioGroup
    lateinit var newNote: FloatingActionButton

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
        newNote.setOnClickListener {
            val direction = NotesFragmentDirections.notesToEditor()
            findNavController().navigate(direction)
        }

        notesRecycler.adapter = adapter
        notesRecycler.layoutManager = LinearLayoutManager(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.observeListState().observe(viewLifecycleOwner) {
            Log.d("NotesFragment", "EVENT - New list of notes received.")
            adapter.submitList(it)
        }

        viewModel.observeEditState().observe(viewLifecycleOwner) { note ->
            if (note != null) {
                Log.d("NotesFragment", "EVENT - New edit request received.")
                val direction = NotesFragmentDirections.notesToEditor().setNote(note)
                findNavController().navigate(direction)
            }
        }

        viewModel.observeDeletedNote().observe(viewLifecycleOwner) { note ->
            Log.d("NotesFragment", "EVENT - New deleted note received.")
            if (note != null) {
                Snackbar.make(requireView(), "Your note was deleted.", Snackbar.LENGTH_LONG)
                    .setAction("Undo") { emitEvent(NoteEvent.RestoreNote(note)) }
                    .setActionTextColor(requireContext().getColor(R.color.holo_red_dark))
                    .show()
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
/*        viewModel.observeEditState().removeObservers(viewLifecycleOwner)
        viewModel.observeListState().removeObservers(viewLifecycleOwner)
        viewModel.observeDeletedNote().removeObservers(viewLifecycleOwner)*/

        viewModel.clearData()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun toggleVisibility(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE)
            View.GONE else View.VISIBLE
    }

    private fun emitEvent(event: NoteEvent) =
        viewModel.onEvent(event)

}