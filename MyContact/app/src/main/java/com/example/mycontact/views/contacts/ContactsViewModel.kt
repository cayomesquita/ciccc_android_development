package com.example.mycontact.views.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycontact.models.Contact
import com.example.mycontact.persistence.repository.ContactRepository
import kotlinx.coroutines.launch

class ContactsViewModel(val contactRepository: ContactRepository) : ViewModel() {

    val contacts = contactRepository.contacts

    fun refreshContacts() {
        viewModelScope.launch {
            contactRepository.refreshContacts()
        }
    }

    fun clearContacts() {
        viewModelScope.launch {
            contactRepository.cleanContacts()
        }
    }

    fun addContact(newContact: Contact): Unit {
        viewModelScope.launch {
            contactRepository.addContact(newContact)
        }
    }

}