package com.example.chatApp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.chatApp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlin.collections.HashMap

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRefrence: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        go_login_ac_btn.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        sign_up_btn.setOnClickListener {
            val username = et_name.text.toString()
            val email = et_email.text.toString()
            val password = et_password.text.toString()

            if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) || TextUtils.isEmpty(
                    password
                )
            ) {
                Toast.makeText(
                    applicationContext,
                    "invalid credentials, can't left a empty field",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                registerUser(username, email, password)
            }

        }
    }

    private fun registerUser(username: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
            if (it.isSuccessful) {
                print("reached")
                var user: FirebaseUser? = auth.currentUser
                var userId: String = user!!.uid

                databaseRefrence =
                    FirebaseDatabase.getInstance().getReference("users").child(userId)

                var hashMap: HashMap<String, String> = HashMap()
                hashMap.put("userId", userId)
                hashMap.put("username", username)
                hashMap.put("profileImg", "")

                databaseRefrence.setValue(hashMap).addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        startActivity(Intent(this, UsersActivity::class.java))
                        finish()
                    }
                }
            }
        }.addOnFailureListener(this) {
            println(it.message)
        }
    }
}