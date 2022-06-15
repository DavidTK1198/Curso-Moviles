package com.matricula.mobile.app

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.matricula.mobile.*
import com.matricula.mobile.apiService.LoginService
import com.matricula.mobile.models.Usuario
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class Login : AppCompatActivity() {

    lateinit var et_user_name: EditText
    lateinit var et_password: EditText
    lateinit var btn_sum:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (this.supportActionBar != null)
            this.supportActionBar!!.hide()

        et_user_name = findViewById(R.id.et_user_name)
         et_password = findViewById(R.id.et_password)
        btn_sum=findViewById(R.id.btn_submit)
    }

   private fun toMainActivity(usuario: Usuario){
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("user",usuario)
        startActivity(i)
        finish()
    }

    private fun login ():Usuario{
        var username= et_user_name.text.toString()
        var password =et_password.text.toString()
        return Usuario(username,password,"","")
    }

    private fun initLoading(){
        val loader=findViewById<ProgressBar>(R.id.loading)
        loader.visibility=View.VISIBLE
        btn_sum.isEnabled = false
        btn_sum.isClickable = false
    }

    private fun stopLoading(){
        val loader=findViewById<ProgressBar>(R.id.loading)
        loader.visibility=View.GONE
        btn_sum.isEnabled = true
        btn_sum.isClickable = true
    }
    fun startService(view: View) {
//        initLoading()
//        CoroutineScope(Dispatchers.IO).launch {
//            val login=login()
//            val call = LoginService.Companion.getInstance().login(login)
//            val logged = call.body()
//            if(call.isSuccessful){
//                toMainActivity(logged!!)
//            }else{
//                stopLoading()
//            }
//        }
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
        }
    }

