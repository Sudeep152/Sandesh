package com.shashank.sandesh.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.shashank.sandesh.R
import com.shashank.sandesh.chatscreen.ChatRoomctivity
import com.shashank.sandesh.model.User
import de.hdodenhof.circleimageview.CircleImageView


class firebaseAdapter(
    options: FirestoreRecyclerOptions<User>

):
    FirestoreRecyclerAdapter<User, firebaseAdapter.viewHolder>(options) {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_user_row,
            parent,
            false
        )

        return viewHolder(view)

    }

    override fun onBindViewHolder(holder: viewHolder, position: Int, model: User) {

        holder.nameEdt.text=model.name
        holder.bioEdt.text =model.bio
        Glide.with(holder.profileImg.context)
                .load(model.profile)
                .circleCrop()
                .into(holder.profileImg)


        holder.itemView.setOnClickListener {

            val intent = Intent(it.context, ChatRoomctivity::class.java)

            intent.putExtra("name",holder.nameEdt.text)
            intent.putExtra("profileImg",model.profile.toString())
            intent.putExtra("recUid",model.uid)
            it.context.startActivity(intent)



        }
    }


  class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val nameEdt =itemView.findViewById<TextView>(R.id.nameEdt)
        val bioEdt = itemView.findViewById<TextView>(R.id.bioEdt)
        val profileImg =itemView.findViewById<CircleImageView>(R.id.pfImagelist)

    }
}
