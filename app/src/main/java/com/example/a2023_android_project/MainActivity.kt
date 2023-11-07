package com.example.a2023_android_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.NonNull
import com.example.a2023_android_project.databinding.ActivityLoginBinding
import com.example.a2023_android_project.databinding.ActivityMainBinding
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

    override fun onMapReady(p0: NaverMap) {
        TODO("Not yet implemented")
    }

}