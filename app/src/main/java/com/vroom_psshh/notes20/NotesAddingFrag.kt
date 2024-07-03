package com.vroom_psshh.notes20

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vroom_psshh.notes20.databinding.FragmentNotesAddingBinding
import com.vroom_psshh.notes20.databinding.NotesEntryListBinding
import com.vroom_psshh.notes20.roomDB.UserInput
import com.vroom_psshh.notes20.viewmodels.MainViewModel


class NotesAddingFrag : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentNotesAddingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notes_adding, container, false)

        //editText movement, scrollbar controller
        binding.notes.movementMethod = ScrollingMovementMethod.getInstance()

        binding.saveButton.setOnClickListener{
            val title = binding.title.text.toString().trim()
            val notes = binding.notes.text.toString().trim()
            val userInput = UserInput(0, title, notes)
            mainViewModel.addNotes(userInput)
            Toast.makeText(requireContext(), "Notes Saved!!", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}