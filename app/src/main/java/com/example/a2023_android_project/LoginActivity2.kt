package com.example.a2023_android_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.a2023_android_project.databinding.ActivityLogin2Binding

class LoginActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityLogin2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //Login Button Event
        binding.loginButton.setOnClickListener{
            var email = binding.loginId.text.toString()
            var password = binding.loginPassword.text.toString()
            //Email, password 공백 금지
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(baseContext, "Email과 Password를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else {
                Log.d("Login2", "email:$email, password:$password")
                MyApplication.auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        binding.loginId.text.clear()
                        binding.loginPassword.text.clear()
                        if (task.isSuccessful) {
                            if (MyApplication.checkAuth()) {
                                MyApplication.email = email
                                startActivity(Intent(this, MainActivity::class.java))
                                Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()
                                //changeVisibility("login")
                            } else {
                                Toast.makeText(
                                    baseContext,
                                    "전송된 메일로 이메일 인증이 되지 않았습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        } else {
                            Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
        binding.loginFindPassword.setOnClickListener{
            Log.d("login", "비밀번호 찾기")
            val emailAddress = binding.loginId.text.toString()

            if (emailAddress.isEmpty()){
                Toast.makeText(baseContext,"가입하신 Email을 입력해주세요!",Toast.LENGTH_LONG).show()
            }else {
                MyApplication.auth.sendPasswordResetEmail(emailAddress)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("login", "비밀번호 변경 Email이 전송되었습니다")
                            Toast.makeText(
                                baseContext,
                                "비밀번호 변경 Email이 전송되었습니다!",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Log.d("login", "가입된 Email이 아닙니다!")
                            Toast.makeText(baseContext, "가입된 Email이 아닙니다!", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        }
        binding.loginSingUp.setOnClickListener{
            val intent = Intent(this, SingUpActivity::class.java)
            Log.d("login", "회원가입화면으로 이동합니다")
            startActivity(intent)
        }
    }
}