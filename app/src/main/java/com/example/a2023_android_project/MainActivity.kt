package com.example.a2023_android_project

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_android_project.MyApplication.Companion.auth
import com.example.a2023_android_project.databinding.ActivityMainBinding
import com.facebook.FacebookSdk
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64
import com.google.firebase.ktx.Firebase
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //미인증 상태이면 로그인 페이지로 이동
        if(MyApplication.checkAuth()){
            if(!MyApplication.checkAuth()){
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }

        //val CAMERA = arrayListOf(Manifest.permission.)

        binding.btAiCamera.setOnClickListener{
            Log.d("button","btAiCamera button success!")
            startActivity(Intent(this, GalleryActivity::class.java))
        }

        //예약 버튼
        binding.btReservation.setOnClickListener{
            Log.d("button","btReservation btReservation success!")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://pf.kakao.com/_axetVs/87283380"))
            startActivity(intent)
        }

        //커뮤니티 버튼
        binding.btCommunity.setOnClickListener{
            startActivity(Intent(this, CommuActivity::class.java))
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
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@muddyday"))
            startActivity(intent)
        }
        binding.btNaver.setOnClickListener{
            Log.d("button","btcommunity btNaver success!")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://blog.naver.com/muddyday96"))
            startActivity(intent)
        }
        binding.btInsta.setOnClickListener{
            Log.d("button","btcommunity btInsta success!")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/muddyday_00/"))
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
    }

    override fun onMapReady(p0: NaverMap) {
        TODO("Not yet implemented")
    }
}