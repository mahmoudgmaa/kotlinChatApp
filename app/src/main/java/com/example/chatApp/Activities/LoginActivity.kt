package com.example.chatApp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.chatApp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        login_btn.setOnClickListener {
            var email: String = et_email_login.text.toString()
            var password: String = et_password_login.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(
                    applicationContext,
                    "invalid credentials, can't left a empty field",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, UsersActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "invalid credentials",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
        go_signup_ac_btn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        
    }
}