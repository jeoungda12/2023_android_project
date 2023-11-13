package com.example.a2023_android_project

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.video.Recorder
import androidx.camera.video.Recording
import androidx.camera.video.VideoCapture
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.widget.Toast
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.core.Preview
import androidx.camera.core.CameraSelector
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.video.FallbackStrategy
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.video.Quality
import androidx.camera.video.QualitySelector
import androidx.camera.video.VideoRecordEvent
import androidx.core.content.PermissionChecker
import com.example.a2023_android_project.databinding.ActivityCameraBinding
import com.example.a2023_android_project.databinding.ActivityMainBinding
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Locale

/*
    CemraX 개발 참고
    https://developer.android.com/codelabs/camerax-getting-started?hl=ko#4
 */

typealias LumaListener = (luma: Double) -> Unit

class CameraActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityCameraBinding

    private var imageCapture: ImageCapture? = null

    private var videoCapture: VideoCapture<Recorder>? = null
    private var recording: Recording? = null

    private lateinit var cameraExecutor: ExecutorService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        //사진 촬영 버튼 이벤트
        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }

        //갤러리 연동 기능 (PickMedia)
//        val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {}
//        // Set up the listeners for take photo and video capture buttons
//        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }
//        viewBinding.videoCaptureButton.setOnClickListener {
//            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
//            Log.d("PhotoPicker","pickMedia : $pickMedia")
//        }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    //Take Photo 버튼을 호출되는 메서드
    private fun takePhoto() {
        //ImageCapture 사용 사례에 대한 참조를 가져온다.
        //사용 사례가 null이면 함수를 종료한다.
        //이미지 캡처가 설정되기 전에 사진 버튼을 탭하면 null이 된다.
        val imageCapture = imageCapture ?: return

        //이미지를 보관할 MediaStore 콘텐츠 값을 만든다.
        //MediaStore의 표시 이름이 고유하도록 파임스탬프를 사용한다.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        //outputOption 객체를 만든다. 이 객체에서 원하는 출력 방법에 관한 사항을 지정할 수 있다.
        //출력을 MediaStore에 저장하여 다른 앱에서 표시할 수 있또록 MediaStore 항목을 추가한다.
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues)
            .build()

        //ImageCapture 객체에서 takePicture()를 호출합니다. outputOption, 실행자, 이미지가
        //저장될 떄의 콜백을 전달 합니다.
        val intent = Intent(this, GalleryActivity::class.java)
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                //이미지 캡처에 실패하거나 이미지 캡처 저장에 실패하는 경우 오류 사례를 추가합니다.
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                }
                //사진을 성공적으로 촬영하고, 만든 파일에 사진을 저장하고, 사용자에게 사진 촬영이 완료되었음을
                //알리는 토스트 메시지를 표시하고, 로그 구문을 출력한다.
                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults){
                    val msg = "Photo capture succeeded: ${output.savedUri}"
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    //촬영한 사진을 GalleryActivity에 출력시켜야된다.
                    Log.d("jgh", "사진 촬영성공 갤러리액티비티로 이동")
                    intent.putExtra("camera_img_uri", output.savedUri)
                    startActivity(intent)
                }
            }
        )
    }

    //PhotoPicker / Android13(SDK33)이상 버전만 가능하다
    private fun captureVideo() {

    }

    private fun startCamera() {
        //ProcessCameraProvider 인스턴스를 만든다.
        //이는 카메라의 수명 주기를 수명 주기 소유자와 바인딩하는 데 사용한다.
        //Camerax가 수명 주기를 인식하므로 카메라를 열고 닫는 작업이 필요하지 않게된다.
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        //cameraProviderFutre에 리스너를 추가한다. Runnable을 하나의 인수로 추가한다. 나중에 채워넣는다.
        //ContextCompat.getMainExcutor()를 두 번째 인수로 추가한다. 그러면 기본 스레드에서 실행되는
        //Executor가 반환된다.
        cameraProviderFuture.addListener({
            // Used to bind the lifecycle of cameras to the lifecycle owner
            //Runnable에서 ProcessCameraProvider를 추가한다. 
            //이는 카메라의 수명 주기를 애플리케이션 프로세스 내의 Lifecycleowner에 바인딩하는데 사용된다.
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Preview
            //Preview 객체를 초기화하고 이 객체에서 build를 호출하고 뷰파인더에서 노출 영역 제공자를
            //가지고 온 다음 미리보기에서 설정한다.
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            // Select back camera as a default
            //CameraSelector 객체를 만들고 Deafult_back_camera를 선택한다.
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
            
            
            //try 블록 내에서 cmeraProvider에 바인딩된 항목이 없도록 한 다음
            //cameraSelector 및 비리 보기 객체를 cameraProvider에 바인딩한다.
            try {
                // Unbind use cases before rebinding
                cameraProvider.unbindAll()

                // Bind use cases to camera
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture)

            //앱의 더 이상 포커스가 없는 경우와 같이 이 코드는 몇 가지의 방법으로 실패 할 수 있다.
            } catch(exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(this))


    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        //요청 코드가 올바른지 확인한다
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            //권한이 부여되면 startCamera()를 호출한다.
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                //권한이 부여되지 않으면 토스트 메시지를 표시한다
                Toast.makeText(this,
                    "카메라 권한을 설정해주세요.",
                    Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf (
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}