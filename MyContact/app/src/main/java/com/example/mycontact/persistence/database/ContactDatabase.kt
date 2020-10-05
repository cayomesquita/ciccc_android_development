package com.example.mycontact.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mycontact.models.Contact

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract val contactDao: ContactDao

    companion object {

        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            synchronized(this) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(context, ContactDatabase::class.java, "contact_table")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }

    }
}