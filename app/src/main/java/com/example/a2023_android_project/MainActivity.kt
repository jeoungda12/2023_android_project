package com.example.a2023_android_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.a2023_android_project.databinding.ActivityLoginBinding
import com.example.a2023_android_project.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btCommunity.setOnClickListener{
            Log.d("button","btcommunity button success!")
        }
        binding.btAiCamera.setOnClickListener{
            Log.d("button","btAiCamera button success!")
        }
        binding.btInsta.setOnClickListener{
            Log.d("button","btcommunity btInsta success!")
        }
        binding.btNaver.setOnClickListener{
            Log.d("button","btcommunity btNaver success!")
        }
        binding.btReservation.setOnClickListener{
            Log.d("button","btcommunity btReservation success!")
        }
        binding.btYoutube.setOnClickListener{
            Log.d("button","btcommunity btYoutube success!")
        }


    }

    /* */
    public override fun onStart() {
        super.onStart()

        firebaseSingup()
    }


    fun firebaseSingup() {
        //사용자가 로그인 되어있는지 확인한다.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("Singup","로그인 중입니다.")
        }else{
            Log.d("Singup","로그인중이 아닙니다.")
        }

        var email: String = "jeoungda12@naver.com"
        var password: String = "12345678"
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    Log.d("Singup","회원가입 성정")
                } else{
                    Log.w("singup", "createUserWithEmail:fail", task.exception)
                }
            }
    }
    override fun onMapReady(p0: NaverMap) {
        TODO("Not yet implemented")
    }
}