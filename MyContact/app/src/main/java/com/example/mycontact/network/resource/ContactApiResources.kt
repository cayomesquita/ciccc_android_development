package com.example.mycontact.network.resource

import com.example.mycontact.models.Contact

data class ContactApiResponse(val results: List<ContactResource>)

data class ContactResource(val name: NameResource, val phone: String, val login: LoginResource) {
    fun toEntity(): Contact {
        val id = login.uuid
        val name = name.first + " " + name.last
        return Contact(id = id, name = name, phone = phone)
    }
}

data class NameResource(val first: String, val last:String)

data class LoginResource(val uuid:String)