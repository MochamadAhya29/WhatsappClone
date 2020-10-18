package com.ahyadroid.whatsappclone.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahyadroid.whatsappclone.MainActivity
import com.ahyadroid.whatsappclone.R
import com.ahyadroid.whatsappclone.adapter.ContactsAdapter
import com.ahyadroid.whatsappclone.listener.ContactsClickListener
import com.ahyadroid.whatsappclone.model.Contact
import kotlinx.android.synthetic.main.activity_contacts.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_profile.progress_layout

class ContactsActivity : AppCompatActivity(), ContactsClickListener {

    private val contactList = ArrayList<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)

        getContacts()
    }

    private fun getContacts() {
        progress_layout.visibility = View.VISIBLE
        contactList.clear()
        val newList = ArrayList<Contact>()
        val phone = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (phone!!.moveToNext()){
            val name = phone.getString(phone
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phone.getString(phone
                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            newList.add(Contact(name, phoneNumber))
        }

        contactList.addAll(newList)
        phone.close()
    }
    private fun setupList() {
        progress_layout.visibility = View.GONE
        val contactsAdapter = ContactsAdapter(contactList) // memasukkan data ke dalam adapter
        contactsAdapter.setOnItemClickListener(this) // memberikan aksi ketika item kontak diklik
        rv_contacts.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onContactClicked(name: String?, phone: String?) {
        val intent = Intent()
        intent.putExtra(MainActivity.PARAM_NAME, name)
        intent.putExtra(MainActivity.PARAM_PHONE, phone)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}