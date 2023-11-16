package com.example.a2023_android_project

import androidx.multidex.MultiDexApplication
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage


//앱 전역에서 인증 객체를 이용하고자 Application을 상속받은 클래스
//FirebaseAuth : 파이어베이스 인증 객체
//checkAuth() : 사용자의 이메일 정보 등 인증 상태를 파악하는 함수
//앱 전역에서 이용할 객체나 함수는 Application 객체에 등록하면 편리하다
//매니페스트에 등록해야 앱 전역에서 사용할 수 있다.


class MyApplication: MultiDexApplication() {
    companion object{
        lateinit var  auth: FirebaseAuth
        var email: String? = null
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage
        fun checkAuth(): Boolean{
            val currentUser = auth.currentUser
            return currentUser?.let{
                email = currentUser.email
                if(currentUser.isEmailVerified){
                    true
                } else{
                    false
                }
            } ?: let{
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = Firebase.auth
        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
    }
}