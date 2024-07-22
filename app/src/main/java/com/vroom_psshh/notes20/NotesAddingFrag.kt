package com.vroom_psshh.notes20

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.vroom_psshh.notes20.databinding.FragmentNotesAddingBinding
import com.vroom_psshh.notes20.roomDB.UserInput
import com.vroom_psshh.notes20.viewmodels.MainViewModel


class NotesAddingFrag : Fragment() {

    private val mainViewModel: MainViewModel by activityViewModels()
    private var notesId:Int = 0
    private lateinit var title: String
    private lateinit var userInput: String
    private lateinit var newTitle: String
    private lateinit var newUserInput: String
    private lateinit var notesClickedForUpdate: UserInput

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding: FragmentNotesAddingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notes_adding, container, false)

        //fetching data from bundle
        val receivedData = arguments
        if (receivedData != null) {
            notesId = receivedData.getInt("notesId")
            title = receivedData.getString("title").toString()
            userInput = receivedData.getString("userInput").toString()
            val editableTitle = title.toEditable()
            val editableUserInput = userInput.toEditable()
            binding.title.text = editableTitle
            binding.notes.text = editableUserInput
            //previous data that will be updated
            notesClickedForUpdate = UserInput(notesId, title, userInput)
        }
        //editText movement, scrollbar controller
        binding.notes.movementMethod = ScrollingMovementMethod.getInstance()

        //save new notes using this logic
        if (TextUtils.isEmpty(binding.title.text.toString()) && TextUtils.isEmpty(binding.notes.text.toString())) {
            binding.saveButton.setOnClickListener {
                if (TextUtils.isEmpty(binding.title.text.toString()) && TextUtils.isEmpty(binding.notes.text.toString())) {
                    Toast.makeText(requireContext(), "Add some data first", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    newTitle = binding.title.text.toString().trim()
                    newUserInput = binding.notes.text.toString().trim()
                    val userInput = UserInput(0, newTitle, newUserInput)
                    mainViewModel.addNotes(userInput)
                    Toast.makeText(requireContext(), "Notes Saved!!", Toast.LENGTH_SHORT).show()
                }
            }
            //Update new notes using this logic
        } else {
            binding.saveButton.text = "Update"
            binding.saveButton.setOnClickListener {
                newTitle = binding.title.text.toString().trim()
                newUserInput = binding.notes.text.toString().trim()
                if (title != newTitle || userInput != newUserInput) {
                    val userInput = notesClickedForUpdate.copy(
                        notesId = notesId,
                        title = newTitle,
                        userInput = newUserInput
                    )
                    mainViewModel.updateNotes(userInput)
                    Toast.makeText(requireContext(), "Notes Updated!!", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(
                    requireContext(),
                    "Make some changes first",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        return binding.root
    }

    private fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)
}