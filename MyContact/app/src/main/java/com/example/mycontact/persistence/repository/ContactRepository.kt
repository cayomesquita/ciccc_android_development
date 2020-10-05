package com.example.mycontact.persistence.repository

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.example.mycontact.models.Contact
import com.example.mycontact.network.ContactApi
import com.example.mycontact.persistence.database.ContactDatabase
import java.lang.Exception

class ContactRepository(private val contactDatabase: ContactDatabase, private val application: Application) {

    val contacts: LiveData<List<Contact>> = contactDatabase.contactDao.fetch()
//    val contacts = MutableLiveData<List<Contact>>()

    private val jsonFileName = "contacts.json"

    suspend fun refreshContacts() {
//        fetchFromJsonFile()
        try {
            val result = ContactApi.retrofitService.getContacts().results.map { it.toEntity() }
            addContactList(result)
        } catch (e: Exception) {
            Toast.makeText(application, e.message, Toast.LENGTH_LONG).show()
        }

    }

    private suspend fun addContactList(list: List<Contact>) {
        contacts.value?.let {
            val newContacts = list subtract it
            newContacts.forEach { contactDatabase.contactDao.insert(it) }
        }
    }

    suspend fun cleanContacts(){
        contactDatabase.contactDao.clean()
    }

    suspend fun addContact(newContact: Contact) {
        contactDatabase.contactDao.insert(newContact)
    }

//    private fun fetchFromJsonFile() {
//        val jsonStr = getJsonDataFromAsset(application, jsonFileName)
//        val gson = Gson()
//        val listContactType = object : TypeToken<List<ContactJson>>() {}.type
//        val aux: List<ContactJson> = gson.fromJson(jsonStr, listContactType)
//        contacts.value = aux.map { Contact(it.id, it.name, it.phone) }
//    }

}