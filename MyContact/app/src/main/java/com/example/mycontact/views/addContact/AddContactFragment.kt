package com.example.mycontact.views.addContact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.mycontact.R
import com.example.mycontact.databinding.FragmentAddContactBinding
import com.example.mycontact.models.Contact
import com.example.mycontact.persistence.database.ContactDatabase
import com.example.mycontact.persistence.repository.ContactRepository
import com.example.mycontact.views.contacts.ContactsViewModel
import com.example.mycontact.views.contacts.ContactsViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [AddContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddContactFragment : Fragment() {

    private lateinit var contactViewModel: ContactsViewModel
    private lateinit var binding: FragmentAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<FragmentAddContactBinding>(
            inflater,
            R.layout.fragment_add_contact, container, false
        )

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ContactsViewModelFactory(
            ContactRepository(
                ContactDatabase.getInstance(application),
                application
            )
        )
        contactViewModel =
            ViewModelProvider(this, viewModelFactory).get(ContactsViewModel::class.java)

        binding.cancelBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
        }

        binding.saveBtn.setOnClickListener { view ->
            if (validate()){
                val name = binding.nameTxtInput.text.toString()
                val phone = binding.phoneTxtInput.text.toString()
                val id = name.replace(" ","_")
                contactViewModel.addContact(Contact(id = id, name = name, phone = phone))
                view.findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
            }
        }



        return binding.root
    }

    private fun validate(): Boolean {
        var flag = true
        binding.nameTxtInput.text?.let {
            if (it.isNullOrBlank() || !it.toString().matches("""^\w+\s+\w+$""".toRegex())) {
                binding.nameTxtInput.error = "Name must be fist name and last name"
                flag = false
            } else {
                binding.nameTxtInput.error = null
            }
        }
        binding.phoneTxtInput.text?.let {
            if (!it.isDigitsOnly() || it.length != 10){
                flag = false
                binding.phoneTxtInput.error = "Phone must be 10 digits"
            } else {
                binding.phoneTxtInput.error = null
            }
        }
        return flag
    }

}