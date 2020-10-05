package com.example.mycontact

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    fun onClickAddBtnss(view: View) {
//        view.findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment)
        Toast.makeText(this,"Add", Toast.LENGTH_SHORT).show()
    }


}