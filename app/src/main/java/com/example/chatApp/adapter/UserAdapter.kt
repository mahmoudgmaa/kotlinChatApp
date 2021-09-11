package com.example.chatApp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chatApp.R
import com.example.chatApp.model.User
import kotlinx.android.synthetic.main.user_item.view.*

class UserAdapter(private val context: Context, private var userList: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.viewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return viewHolder(view)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.itemView.apply {
            var user = userList[position]
            name_tv.text = user.username
            Glide.with(context).load(user.profileImg).placeholder(R.mipmap.profile_pic)
                .into(profile_img_circle)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    public fun addUser(user: User) {
        userList.add(user)
        notifyItemInserted(userList.size - 1)
    }

    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}