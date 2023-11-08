package com.example.a2023_android_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_android_project.MyApplication.Companion.email

import com.example.a2023_android_project.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //로그인 페이지
        val binding_login = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding_login.root)


        //로그인 상태 확인
        if(MyApplication.checkAuth()){
            //로그인 된 상태
            Log.d("login", "로그인 상태입니다.")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            //changeVisibility("login")
        }else {
            Log.d("login", "로그아웃 상태입니다.")
            //changeVisibility("logout")
        }

        //구글 로그인
        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //구글 로그인 결과 처리
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider
                    .getCredential(account.idToken, null)
                MyApplication.auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful){
                            Log.d("signup","구글 로그인 성공")
                            //구글 로그인 성공
                            email = account.email
                            Toast.makeText(this, "구글 로그인 성공!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            //changeVisibility("login")
                        } else{
                            Log.d("signup","구글 로그인 실패")
                            //구글 로그인 실패
                            //changeVisibility("logout")
                        }
                    }
            } catch (e: ApiException){
                Log.d("signup","구글 로그인 실패")
                //changeVisibility("logout")
            }
        }


        //아이디 및 비밀번호 값 잃어오기
//        binding_login.loginButton.setOnClickListener{
//            Log.d("login","login Button Event !")
//            var login_id : String = binding_login.loginId.text.toString()
//            var login_password : String = binding_login.loginPassword.text.toString()
//            Log.d("login","login id : ${login_id} login password : ${login_password}")
//
//            if(login_id == "" || login_password == ""){
//                Log.d("login", "아이디, 비밀번호 입력안됨")
//                Toast.makeText(this, "아이디, 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
//            }
//            else{
//                Log.d("login", "MainActivity로 이동합니다.")
//                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            }
//        }

        //사용자 개인 로그인 화면으로 이동.
        binding_login.loginButton.setOnClickListener{
            Log.d("login", "사용자 개인 로그인 화면으로 이동합니다.")
            startActivity(Intent(this, LoginActivity2::class.java))
        }

        binding_login.loginFindPassword.setOnClickListener{
            Log.d("login", "비밀번호 찾기 화면으로 이동합니다")
        }
        binding_login.loginSingUp.setOnClickListener{
            val intent = Intent(this, SingUpActivity::class.java)
            Log.d("login", "회원가입화면으로 이동합니다")
            startActivity(intent)
        }

        //구글 인증 로그인
        binding_login.loginGoogle.setOnClickListener{
            //구글 로그인....................
            val gso = GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            val signInIntent = GoogleSignIn.getClient(this, gso).signInIntent
            requestLauncher.launch(signInIntent)
        }

        //페이스북 인증 로그인
        binding_login.loginFacebook.setOnClickListener{

        }
    }
}