package com.example.chatApp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import com.example.chatApp.R
import com.google.firebase.auth.FirebaseAuth

@Suppress("DEPRECATION")
class splash_screen : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        auth = FirebaseAuth.getInstance()

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        Handler().postDelayed({
            if (auth.currentUser == null) {
                startActivity(Intent(this, SignUpActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, UsersActivity::class.java))
                finish()
            }

        }, 2000)
    }
}