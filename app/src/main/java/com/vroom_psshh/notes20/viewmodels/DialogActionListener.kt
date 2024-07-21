package com.vroom_psshh.notes20.viewmodels

interface DialogActionListener {
    fun onEdit(position: Int)
    fun onDelete(position: Int)
    fun onShare(position: Int)
}