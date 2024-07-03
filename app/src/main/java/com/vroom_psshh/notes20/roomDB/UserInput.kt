package com.vroom_psshh.notes20.roomDB


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class UserInput(
    @PrimaryKey(autoGenerate = true)
    val notesId:Int,
    val title: String,
    val userInput: String
)
