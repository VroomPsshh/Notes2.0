package com.vroom_psshh.notes20.viewmodels

import com.vroom_psshh.notes20.roomDB.UserInput

interface DialogActionListener {
    fun onEdit(position: Int)
    fun onDelete(userInput: UserInput)
    fun onShare(position: Int)
}