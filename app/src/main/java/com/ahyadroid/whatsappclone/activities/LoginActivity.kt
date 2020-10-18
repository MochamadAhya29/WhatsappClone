package com.ahyadroid.whatsappclone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.Window
import android.widget.EditText
import android.widget.Toast
import com.ahyadroid.whatsappclone.MainActivity
import com.ahyadroid.whatsappclone.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        // mengecheck userId yang sedang aktif, jika ada, proses akan langsung intent ke hal.utama
        val user = firebaseAuth.currentUser?.uid
        if (user != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        setTextChangedListener(edt_email, til_email)
        setTextChangedListener(edt_password, til_password)
        progress_layout.setOnTouchListener { v , event -> true }

        btn_login.setOnClickListener {
            onLogin()
        }
        txt_signup.setOnClickListener {
            onSignup()
        }

    }
    private fun setTextChangedListener(edt: EditText, til: TextInputLayout) {
        edt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?
                                           , start: Int, count: Int, after: Int) {
            }
            // ketika editText diubah memastikan TextInputLayout tidak menunjukkan pesan error
            override fun onTextChanged(s: CharSequence?
                                       , start: Int, before: Int, count: Int) {
                til.isErrorEnabled = false
            }
        })
    }

    private fun onLogin() {
        var proceed = true
        if (edt_email.text.isNullOrEmpty()) { // check jika EditText kosong
            til_email.error = "Required Password" // TextInputLayout(til) menampilkan pesan
            til_email.isErrorEnabled = true // mengubah state til yang sebelumnya tidak
            proceed = false // menampilkan error sekarang menampilkan
        }
        if (edt_password.text.isNullOrEmpty()) {
            til_password.error = "Required Password"
            til_password.isErrorEnabled = true
            proceed = false
        }
        if (proceed) {
            progress_layout.visibility = View.VISIBLE // menampilkan ProgressBar
            firebaseAuth.signInWithEmailAndPassword( // untuk menunjukkan bahwa ada
                edt_email.text.toString(), // proses yang sedang dilakukan
                edt_password.text.toString() // mengubah data dalam editText jadi string
            )
                .addOnCompleteListener { task -> // jika proses sebelumnya selesai dilaksanakan
                    if (!task.isSuccessful) { // jika proses yang selesai dilaksanakan hasilnya kegagalan
                        progress_layout.visibility = View.GONE // ProgressBar dihilangkan
                        Toast.makeText( // ditampilkan pesan error
                            this@LoginActivity, // melalui Toast
                            "Login error: ${task.exception?.localizedMessage}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                .addOnFailureListener { e -> // jika proses sebelumnya tidak dilaksanakan
                    progress_layout.visibility = View.GONE // ProgressBar dihilangkan
                    e.printStackTrace() // ditampilkan log errornya
                }
        }
    }
    override fun onStart() {
        super.onStart() // method yang pertama kali dijalankan sebelum method lainnya
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }
    override fun onStop() {
        super.onStop() // dijalankan jika proses dalam activity selesai atau dihentikan system
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }
    private fun onSignup() {
        startActivity(Intent(this, SignUpActivity::class.java))
        finish()
    }

}