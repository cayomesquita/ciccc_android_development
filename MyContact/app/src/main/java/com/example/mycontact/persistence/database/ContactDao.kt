package com.example.mycontact.persistence.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mycontact.models.Contact

@Dao
interface ContactDao {

    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Query("SELECT * FROM contact_table ORDER BY name ASC")
    fun fetch(): LiveData<List<Contact>>

    @Query("DELETE FROM contact_table")
    suspend fun clean()
}