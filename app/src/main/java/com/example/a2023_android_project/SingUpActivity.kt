package com.example.a2023_android_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.a2023_android_project.databinding.ActivitySingUpBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.play.core.integrity.p
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.actionCodeSettings
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SingUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    var email: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_sing_up)

        auth = Firebase.auth

        var binding = ActivitySingUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener{
            Log.d("signup", "회원가입 loginButton Event!")
            val email: String = binding.loginId.text.toString()
            var password: String = binding.loginPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Toast.makeText(baseContext, "Email과 Password를 입력해주세요", Toast.LENGTH_SHORT).show()
            }else {
                firebaseSingup(password, email)
            }
        }
    }

    public override fun onStart() {
        super.onStart()
    }

    /*이메일, 비밀번호를 통한 회원가입 함수*/
    fun firebaseSingup(p: String, e: String) {
        var password = p
        var email = e

        //사용자가 로그인 되어있는지 확인한다.
        val currentUser = auth.currentUser
        if(currentUser != null){
            Log.d("signup","로그인 중입니다.")
        }else{
            Log.d("signup","로그인중이 아닙니다.")
        }

        //binding으로 사용자 정보 가지고 오기

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    Log.d("signup","회원가입 성공 메일 전송하기")
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnCompleteListener{ sendTask ->
                            if(sendTask.isSuccessful){
                                Log.d("singup","회원가입 성공")
                                Toast.makeText(this, "회원가입성공!", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            }else{
                                Log.d("signup","회원가입 실패")
                            }
                        }
                } else{
                    Log.w("signup", "createUserWithEmail:fail", task.exception)
                }
            }
    }

    /*구글 로그인 Firebase 연동 함수*/
    fun firebaseGoogleLogin(){
        val requestLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //구글 로그인 결과 처리
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            try{
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider
                    .getCredential(account.idToken, null)
                auth.signInWithCredential(credential)
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful){
                            Log.d("signup","구글 로그인 성공")
                            //구글 로그인 성공
                            email = account.email
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
    }

}