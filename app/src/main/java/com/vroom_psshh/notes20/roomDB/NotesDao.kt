package com.vroom_psshh.notes20.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNotes(notes: List<UserInput>)

    @Query("SELECT * FROM notes")
    fun getNotes(): LiveData<List<UserInput>>

//    @Query("SELECT COUNT(*) FROM notes")
//    fun getEntryCount(): Int
}