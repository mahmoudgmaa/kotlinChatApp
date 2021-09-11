package com.example.chatApp.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.chatApp.R
import com.example.chatApp.adapter.UserAdapter
import com.example.chatApp.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_users.*

class UsersActivity : AppCompatActivity() {
    var userList = ArrayList<User>()
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        recView.layoutManager = LinearLayoutManager(this)
        //getuser
        userAdapter = UserAdapter(this, userList)
        recView.adapter = userAdapter

        imgBack.setOnClickListener {
            onBackPressed()
        }
        profile_img_usac.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        getUser()
    }

    fun getUser() {
        var firebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        var refrence: DatabaseReference = FirebaseDatabase.getInstance().getReference("users")
        userList.clear()
        refrence.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snap: DataSnapshot in snapshot.children) {
                    val user: User? = snap.getValue(User::class.java)
                    if (firebaseUser.uid == user?.userId) {
                        if (user.profileImg == "") {
                            profile_img_usac.setImageResource(R.mipmap.profile_pic)
                        } else {
                            Glide.with(this@UsersActivity).load(user.profileImg)
                                .into(profile_img_usac)
                        }
                    }
                    if (!user!!.userId.equals(firebaseUser.uid)) {
                        Toast.makeText(this@UsersActivity, user.username, Toast.LENGTH_LONG).show()
                        userAdapter.addUser(user)
                        userAdapter.notifyDataSetChanged()
                    }
                    if (userList.size == 0) {
                        noUsersTv.visibility = VISIBLE
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}