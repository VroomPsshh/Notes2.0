package com.vroom_psshh.notes20.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNotes(notes: List<UserInput>)

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<UserInput>>

    @Update
    fun updateNotes(notes: List<UserInput>)

    @Delete
    fun deleteNotes(notes: List<UserInput>)

//    @Query("SELECT COUNT(*) FROM notes")
//    fun getEntryCount(): Int
}