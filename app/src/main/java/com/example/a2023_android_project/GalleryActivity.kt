package com.example.a2023_android_project


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import coil.load
import com.example.a2023_android_project.databinding.ActivityGalleryBinding


class GalleryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //갤러리와 연동해서 사진 출력하기
        val readImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
            binding.imgLoad.load(uri)
        }

        //갤러리 버튼 이벤트
        binding.btnFileImg.setOnClickListener{
            readImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        //카메라 버튼 이벤트
        binding.btnCamera.setOnClickListener{
            startActivity(Intent(this, CameraActivity::class.java))
        }
    }
}