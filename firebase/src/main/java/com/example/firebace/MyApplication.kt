package com.example.firebace

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


//앱 전역에서 이용할 객체나 함수를 객체에 등록.
//이렇게 만든 클래스는 매니페스트에 등록해야 앱 전역에서 사용가능하다.
class MyApplication: MultiDexApplication() {
    companion object {
        lateinit var auth: FirebaseAuth
        var email: String? = null
        lateinit var db: FirebaseFirestore
        lateinit var storage: FirebaseStorage

        //인증된 사용자의 정보를 파악하는 함수
        fun checkAuth(): Boolean {
            var currentUser = auth.currentUser
            return currentUser?.let {
                email = currentUser.email
                currentUser.isEmailVerified
            } ?: let {
                false
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        auth = com.google.firebase.ktx.Firebase.auth
        db = FirebaseFirestore.getInstance()
        storage = com.google.firebase.ktx.Firebase.storage
    }
}