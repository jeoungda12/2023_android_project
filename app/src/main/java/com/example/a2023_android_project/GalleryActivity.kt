package com.example.a2023_android_project


import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
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

        //CameraActivity로부터 imageUri 값을 받아옴
        var imageUri: Uri? = intent.getParcelableExtra<Uri>("camera_img_uri")
        //Log.d("jgh","카메라 uri : $imageUri")

        //imageUir을 전달받으면
        if(imageUri != null){
            //사진 찍은 이미지를 띄워줌
            binding.imgLoad.setImageURI(imageUri)
            imageUri = null
        }

        //갤러리와 연동해서 사진 출력하기
        val readImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri ->
            if(imageUri != null){
                binding.imgLoad.load(imageUri)
            } else{
                //이미지 수신 실패
                binding.imgLoad.load(uri)
            }
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