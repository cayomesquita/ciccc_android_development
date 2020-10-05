package com.example.mycontact.utils

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mycontact.models.Contact

@BindingAdapter("contactNameFormatted")
fun TextView.setContactName(contact: Contact): Unit {
    this.text = contact.name
}

@BindingAdapter("contactPhoneFormatted")
fun TextView.setContactPhone(contact: Contact): Unit {
    this.text = contact.phone
}