package com.ahyadroid.whatsappclone.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ahyadroid.whatsappclone.MainActivity
import com.ahyadroid.whatsappclone.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            onLogin()
        }
        txt_signup.setOnClickListener {
            onSignup()
        }

    }


    private fun onLogin() {
        startActivity(Intent(this, MainActivity::class.java))
    }
    private fun onSignup() {
        Toast.makeText(this@LoginActivity, "Tunggu sampai bikin Sign up Activity", Toast.LENGTH_SHORT).show()
    }

}