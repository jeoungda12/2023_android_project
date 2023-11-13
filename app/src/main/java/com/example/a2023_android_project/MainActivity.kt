package com.example.a2023_android_project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_android_project.MyApplication.Companion.auth
import com.example.a2023_android_project.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(Intent.ACTION_VIEW)

        //val CAMERA = arrayListOf(Manifest.permission.)
        binding.btCommunity.setOnClickListener{
            Log.d("button","btcommunity button success!")
        }
        binding.btAiCamera.setOnClickListener{
            Log.d("button","btAiCamera button success!")
            startActivity(Intent(this, GalleryActivity::class.java))
        }

        //예약 버튼
        binding.btReservation.setOnClickListener{
            Log.d("button","btReservation btReservation success!")
            intent.setData(Uri.parse("https://pf.kakao.com/_axetVs/87283380"))
            startActivity(intent)
        }

        //로그아웃
        binding.btLogout.setOnClickListener{
            Log.d("button","Logout!")
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }

        //네이버 인스타 유투브 외부 하이퍼링크 연결
        binding.btYoutube.setOnClickListener{
            Log.d("button","btcommunity btYoutube success!")
            intent.setData(Uri.parse("https://www.youtube.com/@muddyday"))
            startActivity(intent)
        }
        binding.btNaver.setOnClickListener{
            Log.d("button","btcommunity btNaver success!")
            intent.setData(Uri.parse("https://blog.naver.com/muddyday96"))
            startActivity(intent)
        }
        binding.btInsta.setOnClickListener{
            Log.d("button","btcommunity btInsta success!")
            intent.setData(Uri.parse("https://www.instagram.com/muddyday_00/"))
            startActivity(intent)
        }
    }

    /* */
    public override fun onStart() {
        super.onStart()


    }



    override fun onMapReady(p0: NaverMap) {
        TODO("Not yet implemented")
    }
}