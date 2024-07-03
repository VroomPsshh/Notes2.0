package com.vroom_psshh.notes20

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vroom_psshh.notes20.databinding.NotesEntryListBinding
import com.vroom_psshh.notes20.roomDB.UserInput

class UserInputAdapter(private val context: Context, private var entryList :List<UserInput>): RecyclerView.Adapter<UserInputAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotesEntryListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userPosition = entryList[position]
        holder.bind(userPosition)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    fun updateNotes(notes: List<UserInput>){
        this.entryList = notes
        notifyDataSetChanged()
    }

    class ViewHolder(private var binding: NotesEntryListBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(userInput: UserInput){
            val context = binding.root.context
            binding.userInput = userInput
            binding.executePendingBindings()
            binding.notesCard.setOnClickListener{
                Toast.makeText(context, "hey this is your current position $adapterPosition", Toast.LENGTH_SHORT).show()
            }
        }
    }
}