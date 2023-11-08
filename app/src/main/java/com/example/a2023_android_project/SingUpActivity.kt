package com.example.a2023_android_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SingUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)
        Log.d("signup", "Activity_sing_up")
        auth = Firebase.auth
    }

    public override fun onStart() {
        super.onStart()

        firebaseSingup()
    }


    fun firebaseSingup() {
        //사용자가 로그인 되어있는지 확인한다.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("signup","로그인 중입니다.")
        }else{
            Log.d("signup","로그인중이 아닙니다.")
        }


        //binding으로 사용자 정보 가지고 오기
        var email: String = "jeoungda12@naver.com"
        var password: String = "12345678"



        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    Log.d("signup","회원가입 성공 메일 정송하기")
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener{ sendTask ->
                            if(sendTask.isSuccessful){
                                Log.d("singup","회원가입 성공")
                            }else{
                                Log.d("signup","회원가입 실패")
                            }
                        }
                } else{
                    Log.w("signup", "createUserWithEmail:fail", task.exception)
                }
            }
    }

}