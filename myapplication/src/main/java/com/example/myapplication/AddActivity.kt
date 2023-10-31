package com.example.myapplication

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.ch20_firebase.databinding.ActivityAddBinding
import com.example.ch20_firebase.util.dateToString
import java.io.File
import java.util.*

class AddActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddBinding
    lateinit var filePath: String
    val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ){
        if(it.all { permission -> permission.value }){
            saveStore()
        }else {
            Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    val requestLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult())
    {
        if(it.resultCode === android.app.Activity.RESULT_OK){
            Glide
                .with(getApplicationContext())
                .load(it.data?.data)
                .apply(RequestOptions().override(250, 200))
                .centerCrop()
                .into(binding.addImageView)


            val cursor = contentResolver.query(it.data?.data as Uri,
                arrayOf<String>(MediaStore.Images.Media.DATA), null, null, null);
            cursor?.moveToFirst().let {
                filePath=cursor?.getString(0) as String
            }
            Log.d("kkang", "filePath : $filePath")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_add_gallery){
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*"
            )
            requestLauncher.launch(intent)
        }else if(item.itemId === R.id.menu_add_save){
            if(binding.addImageView.drawable !== null && binding.addEditView.text.isNotEmpty()){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            "android.permission.READ_MEDIA_IMAGES"
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        saveStore()
                    } else {
                        permissionLauncher.launch(
                            arrayOf<String>(
                                "android.permission.READ_MEDIA_IMAGES"
                            )
                        )
                    }
                }else {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            "android.permission.READ_EXTERNAL_STORAGE"
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        saveStore()
                    } else {
                        permissionLauncher.launch(
                            arrayOf(
                                "android.permission.READ_EXTERNAL_STORAGE"
                            )
                        )
                    }
                }

            }else {
                Toast.makeText(this, "데이터가 모두 입력되지 않았습니다.", Toast.LENGTH_SHORT).show()
            }

        }
        return super.onOptionsItemSelected(item)
    }
    //....................
    private fun saveStore(){
        //add............................
        val data = mapOf(
            "email" to MyApplication.email,
            "content" to binding.addEditView.text.toString(),
            "date" to dateToString(Date())
        )

        MyApplication.db.collection("news")
            .add(data)
            .addOnSuccessListener {
                uploadImage(it.id)
            }
            .addOnFailureListener{
                Log.d("kkang", "data save error", it)
            }
        
    }
    private fun uploadImage(docId: String){
        //add............................
        val storage = MyApplication.storage
        val storageRef = storage.reference
        val imgRef = storageRef.child("images/${docId}.jpg")

        val file = Uri.fromFile(File(filePath))
        imgRef.putFile(file)
            .addOnSuccessListener {
                Toast.makeText(this, "save ok..", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener{
                Log.d("kkang", "file save error", it)
            }

    }
}