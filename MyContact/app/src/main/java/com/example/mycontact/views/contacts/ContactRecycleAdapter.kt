package com.example.mycontact.views.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mycontact.databinding.ListHeaderBinding
import com.example.mycontact.databinding.ListItemContactBinding
import com.example.mycontact.models.Contact

class ContactRecycleAdapter: ListAdapter<DataItem,ViewHolder>(ContactDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        return when (viewType) {
            CONTACT_TYPE -> ViewHolder.itemFrom(parent)
            HEADER_TYPE -> ViewHolder.headerFrom(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            is DataItem.ContactItem -> (holder as ViewHolder.ItemViewHolder)?.let { it.bind(item.contact) }
            is DataItem.Header -> (holder as ViewHolder.HeaderViewHolder)?.let { it.bind(item.title) }
        }
    }

    private val CONTACT_TYPE = 1
    private val HEADER_TYPE = 0

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)){
            is DataItem.ContactItem -> CONTACT_TYPE
            is DataItem.Header -> HEADER_TYPE
        }
    }

    fun addAndSubmitList(contactList: List<Contact>) {
        if (contactList.isEmpty()){
            submitList(emptyList())
        }
        var contactListSorted = contactList.sortedBy { it.name }
        contactListSorted.firstOrNull()?.firstLetter()?.let {
            var firstLetter = it
            val list = mutableListOf<DataItem>(DataItem.Header(firstLetter.toString()))
            for (contact in contactListSorted) {
                contact.firstLetter()?.let { letter ->
                    if (firstLetter != letter) {
                        firstLetter = letter
                        list += DataItem.Header(firstLetter.toString())
                    }
                }
                list += DataItem.ContactItem(contact)
            }
            submitList(list)
        }
    }
}

class ContactDiffCallBack: DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    class ItemViewHolder(val binding: ListItemContactBinding) : ViewHolder(binding.root) {
        fun bind(contact: Contact): Unit {
            binding.contact = contact
            binding.executePendingBindings()
        }
    }

    class HeaderViewHolder(val binding: ListHeaderBinding) : ViewHolder(binding.root) {
        fun bind(title: String): Unit {
            binding.title = title
            binding.executePendingBindings()
        }
    }

    companion object {
        fun itemFrom(parent: ViewGroup): ItemViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemContactBinding.inflate(layoutInflater, parent, false)
            return ItemViewHolder(binding)
        }

        fun headerFrom(parent: ViewGroup): HeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListHeaderBinding.inflate(layoutInflater, parent, false)
            return HeaderViewHolder(binding)
        }
    }

}
//class ViewHolder : RecyclerView.ViewHolder {
//
//    companion object {
//        fun itemFrom(parent: ViewGroup): ViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ListItemContactBinding.inflate(layoutInflater, parent, false)
//            return ViewHolder(binding)
//        }
//
//        fun headerFrom(parent: ViewGroup): ViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ListHeaderBinding.inflate(layoutInflater, parent, false)
//            return ViewHolder(binding)
//        }
//    }
//
//    constructor(binding: ListHeaderBinding) : super(binding.root)
//    constructor(binding: ListItemContactBinding) : super(binding.root)
//
//    fun bind(contact: Contact): Unit {
//        binding.contact = contact
//        binding.executePendingBindings()
//    }
//}

sealed class DataItem {

    abstract val id: String

    data class ContactItem(val contact: Contact) : DataItem() {
        override val id = contact.id
    }

    data class Header(val title: String) : DataItem() {
        override val id = title
    }
}

fun Contact.firstLetter() : Char? {
    return this.name.firstOrNull()
}