package com.shashank.sandesh.messageDao

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.shashank.sandesh.model.messageModel
import java.time.LocalDateTime

class messageDao {
    val db = FirebaseFirestore.getInstance()
   // val msgCollection = db.collection("Chatroom")

    var  messageModel :messageModel ?=null
   @RequiresApi(Build.VERSION_CODES.O)
   val currentDateTime = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)


//         fun onetoone():String{
//        if(messageModel?.senderUid.toString() < messageModel?.recUid.toString()){
//            return messageModel?.senderUid+messageModel?.recUid;
//        }
//        else{
//            return messageModel?.recUid + messageModel?.senderUid;
//        }
//         }


    fun chatStart(messageModel: messageModel){

        val firestore = FirebaseFirestore.getInstance()

        val data =  firestore.collection("Chatroom").document(messageModel.senderUid+messageModel.recUid).collection("messages")
        data.document().set(messageModel)

    }




    }
