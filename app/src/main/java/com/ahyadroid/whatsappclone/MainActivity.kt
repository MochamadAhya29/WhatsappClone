package com.ahyadroid.whatsappclone

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.ahyadroid.whatsappclone.activities.ContactsActivity
import com.ahyadroid.whatsappclone.activities.LoginActivity
import com.ahyadroid.whatsappclone.activities.ProfileActivity
import com.ahyadroid.whatsappclone.adapter.SectionPagerAdapter
import com.ahyadroid.whatsappclone.util.PERMISSION_REQUEST_READ_CONTACT
import com.ahyadroid.whatsappclone.util.REQUEST_NEW_CHATS
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import java.util.jar.Pack200


class MainActivity : AppCompatActivity() {

    companion object{
        const val PARAM_NAME = "name"
        const val PARAM_PHONE = "phone"
    }


    private val firebaseAuth = FirebaseAuth.getInstance() // connect ke Firebase Authentication
    private var mSectionPagerAdapter: SectionPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setSupportActionBar(toolbar) // menambahkan toolbar dari layout
        mSectionPagerAdapter = SectionPagerAdapter(supportFragmentManager)
        container.adapter = mSectionPagerAdapter

        fab.setOnClickListener {
          onChatNew()
        }

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> fab.hide()
                    1 -> fab.show()
                    2 -> fab.hide()
                    3 -> fab.hide()
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

        })
        resizeTabs()
        tabs.getTabAt(1)?.select()

    }

    private fun onChatNew() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_CONTACTS)){
                AlertDialog.Builder(this)
                    .setTitle("Contacts Permission")
                    .setMessage("This App Requires Access to your contacts to invitation A conversation")
                    .setPositiveButton("Yes"){
                        dialog, which -> requestContactPermission()
                    }
                    .setNegativeButton("No"){
                        dialog, which ->
                    }
                    .show()
            }
            else {
                requestContactPermission()
            }
        } else {
            startNewActivity()
        }
    }

    private fun requestContactPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_CONTACTS),
            PERMISSION_REQUEST_READ_CONTACT
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            PERMISSION_REQUEST_READ_CONTACT -> {
                if (grantResults.isNotEmpty() && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    startNewActivity()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(firebaseAuth.currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun resizeTabs() {
        val layout = (tabs.getChildAt(0) as LinearLayout).getChildAt(0) as LinearLayout
        val layoutParams = layout.layoutParams as LinearLayout.LayoutParams
        layoutParams.weight = 0.4f
        layout.layoutParams = layoutParams
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_setelan -> onProfil()
            R.id.action_logout -> onLogout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun onProfil() {
        startActivity(Intent(this, ProfileActivity::class.java))
    }

    private fun onLogout(){
        firebaseAuth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    class PlaceHolderFragment : Fragment() {
        companion object {
            private val ARG_SECTION_NUMBER = "section_number"

            fun newIntent(sectionNumber: Int): PlaceHolderFragment {
                val fragment = PlaceHolderFragment()
                val args = Bundle() // mengikat data untuk dikirimkan bersamaan
                args.putInt(ARG_SECTION_NUMBER, sectionNumber) // mengirimkan data
                fragment.arguments = args
                return fragment
            }
        }
        // memasangkan tampilan dari layout dengan menggunakan LayoutInflater
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_main, container, false)
            rootView.section_label.text =
                "Hello world, from section ${arguments?.getInt(ARG_SECTION_NUMBER)}"
            return rootView
        }
    }

    fun startNewActivity(){
        startActivityForResult(Intent(this, ContactsActivity::class.java), REQUEST_NEW_CHATS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK){
            when(requestCode){
                REQUEST_NEW_CHATS -> {}
            }
        }
    }
}