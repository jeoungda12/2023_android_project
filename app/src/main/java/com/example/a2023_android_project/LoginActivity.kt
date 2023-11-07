package com.example.a2023_android_project

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a2023_android_project.databinding.ActivityLoginBinding
import com.example.a2023_android_project.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //로그인 페이지
        val binding_login = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding_login.root)

        //아이디 및 비밀번호 값 잃어오기
        binding_login.loginButton.setOnClickListener{
            Log.d("login","login Button Event !")
            var login_id : String = binding_login.loginId.text.toString()
            var login_password : String = binding_login.loginPassword.text.toString()
            Log.d("login","login id : ${login_id} login password : ${login_password}")

            if(login_id == "" || login_password == ""){
                Log.d("login", "아이디, 비밀번호 입력안됨")
                Toast.makeText(this, "아이디, 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
            else{
                Log.d("login", "MainActivity로 이동합니다.")
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        }

        binding_login.loginFindPassword.setOnClickListener{
            Log.d("login", "비밀번호 찾기 화면으로 이동합니다")
        }
        binding_login.loginSingUp.setOnClickListener{
            Log.d("login", "회원가입화면으로 이동합니다")
        }
    }
}