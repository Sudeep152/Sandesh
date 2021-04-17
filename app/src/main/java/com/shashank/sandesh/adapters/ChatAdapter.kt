package com.shashank.sandesh.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.shashank.sandesh.R
import com.shashank.sandesh.model.messageModel

class ChatAdapter(options: FirestoreRecyclerOptions<messageModel>) : FirestoreRecyclerAdapter<messageModel, ChatAdapter.tochatviewHolder>(options) {



    inner class tochatviewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val toMsg =itemView.findViewById<TextView>(R.id.messageTo)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): tochatviewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_to_chat,parent,false)


        return tochatviewHolder(view)


    }

    override fun onBindViewHolder(holder: tochatviewHolder, position: Int, model: messageModel) {

        holder.toMsg.text = model.message


    }
}