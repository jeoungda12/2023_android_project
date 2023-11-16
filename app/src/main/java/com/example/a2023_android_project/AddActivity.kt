package com.example.a2023_android_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.text.SimpleDateFormat
import java.util.Date

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        fun dateToString(date: Date): String {
            val format = SimpleDateFormat("yyyy-MM-dd")
            return format.format(date)
        }

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

                }
        }
    }
}