package com.vroom_psshh.notes20

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.vroom_psshh.notes20.databinding.NotesEntryListBinding
import com.vroom_psshh.notes20.roomDB.UserInput
import com.vroom_psshh.notes20.viewmodels.DialogActionListener

class UserInputAdapter(private val context: Context, private var entryList: List<UserInput>, private val dialogActionListener:DialogActionListener) :
    RecyclerView.Adapter<UserInputAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NotesEntryListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userPosition = entryList[position]
        holder.bind(userPosition, position)
    }

    override fun getItemCount(): Int {
        return entryList.size
    }

    fun updateNotes(notes: List<UserInput>) {
        this.entryList = notes
        notifyDataSetChanged()
    }

    inner class ViewHolder(private var binding: NotesEntryListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userInput: UserInput, position: Int) {
            val context = binding.root.context
            binding.userInput = userInput
            binding.executePendingBindings()
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.custom_dialog_layout)
            val optionEdit = dialog.findViewById<TextView>(R.id.optionEdit)
            val optionDelete = dialog.findViewById<TextView>(R.id.optionDelete)
            val optionShare = dialog.findViewById<TextView>(R.id.optionShare)
            binding.notesCard.setOnLongClickListener {
                optionEdit.setOnClickListener{
                    dialogActionListener.onEdit(position)
                    dialog.dismiss()
                }
                optionDelete.setOnClickListener{
                    dialogActionListener.onDelete(position)
                    dialog.dismiss()
                }
                optionShare.setOnClickListener{
                    dialogActionListener.onShare(position)
                    dialog.dismiss()
                }
                dialog.show()
                true
                // Return 'true' to indicate that the event is consumed
            }
        }
    }
}