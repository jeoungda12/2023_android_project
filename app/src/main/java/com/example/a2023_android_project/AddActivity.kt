package com.example.a2023_android_project

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.storage.StorageReference
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

class AddActivity : AppCompatActivity() {

    lateinit var filePath: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
    }


    fun dateToString(date: Date): String {
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(date)
    }

    //db email, content, date data 저장 / image 저장
    fun saveStore() {
        val data = mapOf(
            "email" to MyApplication.email,
            // "content" to 바인딩객체
            "date" to dateToString(Date())
        )
        MyApplication.db.collection("news")
            .add(data)
            .addOnSuccessListener {
                //upload
            }
            .addOnFailureListener{
                //Data 저장 실패
                Log.d("db", "data save error!")
            }
    }
    //이미지 파일 db 업로드
    fun uploadImage(docId: String){
        val storage = MyApplication.storage
        val storageRef: StorageReference = storage.reference
        val imgRef: StorageReference = storageRef.child("images/${docId}.jpg")

        //파일 업로드
        var file = Uri.fromFile(File(filePath))
        imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this,"사진 저장 성공", Toast.LENGTH_SHORT).show()
                finish()
            }

    }
}