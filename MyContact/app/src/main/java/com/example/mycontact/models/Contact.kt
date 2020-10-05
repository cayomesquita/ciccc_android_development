package com.example.mycontact.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact_table")
data class Contact(
    @PrimaryKey
    var id: String,
    @ColumnInfo
    var name: String,
    @ColumnInfo
    var phone: String
)

//data class ContactJson(var id: String, val name: String, val phone: String)


