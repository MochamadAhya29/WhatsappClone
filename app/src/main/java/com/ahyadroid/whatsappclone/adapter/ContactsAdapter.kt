package com.ahyadroid.whatsappclone.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ahyadroid.whatsappclone.R
import com.ahyadroid.whatsappclone.listener.ContactsClickListener
import com.ahyadroid.whatsappclone.model.Contact
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_contact.*

class ContactsAdapter(val contacts: ArrayList<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {


    private var clickListener: ContactsClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ContactsViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        )


    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactsAdapter.ContactsViewHolder, position: Int) {
        holder.bindItem(contacts[position], clickListener)
    }

    class ContactsViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindItem(contact: Contact, listener: ContactsClickListener?){
            txt_contact_name.text = contact.name
            txt_contact_number.text = contact.phone
            itemView.setOnClickListener {
                listener?.onContactClicked(contact.name, contact.phone)
            }
        }
    }

    fun setOnItemClickListener(listener:ContactsClickListener){
        clickListener = listener
        notifyDataSetChanged()
    }


}