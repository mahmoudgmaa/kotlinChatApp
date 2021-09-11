package com.example.chatApp.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.chatApp.R
import com.example.chatApp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        imgBack2.setOnClickListener {
            onBackPressed()
        }

        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        databaseReference =
            FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.uid)
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                userName.text = user!!.username

                if (user.profileImg == "") {
                    profile_img_pfac.setImageResource(R.mipmap.profile_pic)
                } else {
                    Glide.with(this@ProfileActivity).load(user.profileImg).into(profile_img_pfac)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}