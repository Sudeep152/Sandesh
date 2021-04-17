package com.shashank.sandesh.messageDao

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.firestore.FirebaseFirestore
import com.shashank.sandesh.model.messageModel
import java.time.LocalDateTime

class messageDao {
    val db = FirebaseFirestore.getInstance()
    val msgCollection = db.collection("message")

   @RequiresApi(Build.VERSION_CODES.O)
   val currentDateTime = LocalDateTime.now()
    @RequiresApi(Build.VERSION_CODES.O)
    fun chatStart(messageModel: messageModel){

      val data =  msgCollection.document(messageModel.recUid+messageModel.senderUid).collection(currentDateTime.toString())
        data.document().set(messageModel)

    }
}