package com.example.mycontact.views.contacts

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.mycontact.R
import com.example.mycontact.databinding.FragmentContactsBinding
import com.example.mycontact.persistence.database.ContactDatabase
import com.example.mycontact.persistence.repository.ContactRepository

/**
 * A simple [Fragment] subclass.
 * Use the [ContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactsFragment : Fragment() {

    private lateinit var contactViewModel: ContactsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentContactsBinding>(
            inflater,
            R.layout.fragment_contacts, container, false
        )

        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = ContactsViewModelFactory(
            ContactRepository(
                ContactDatabase.getInstance(application),
                application
            )
        )
        contactViewModel =
            ViewModelProvider(this, viewModelFactory).get(ContactsViewModel::class.java)

        val contactAdapter = ContactRecycleAdapter()
        contactViewModel.contacts.observe(viewLifecycleOwner, Observer {
            it?.let { contactAdapter.addAndSubmitList(it) }
        })

        binding.contactViewModel = contactViewModel
        binding.contactList.adapter = contactAdapter

        binding.addBtn.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment)
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refreshBtn -> {
                contactViewModel.refreshContacts()
                Toast.makeText(activity, "Refresh", Toast.LENGTH_LONG).show()
            }
            R.id.cleanBtn -> {
                contactViewModel.clearContacts()
                Toast.makeText(activity, "Clean", Toast.LENGTH_LONG).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

}