package com.example.uiexamples

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class AddPersona : AppCompatActivity() {
    var users: Users = Users.instance
    val hashMap= HashMap<Int,Int>()
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_persona)
        var users: Users = Users.instance
        user = intent.extras?.get("user") as User
        this.initHashmap()
        val username = findViewById<EditText>(R.id.name4)
        val fullname = findViewById<EditText>(R.id.nameInput3)
        val email = findViewById<EditText>(R.id.emailid3)
        val password = findViewById<EditText>(R.id.passcreate)
        val country = findViewById<EditText>(R.id.country3)
        val city = findViewById<EditText>(R.id.city3)
        val address = findViewById<EditText>(R.id.address3)
        val code = findViewById<EditText>(R.id.zipCodeInput3)
        val phone = findViewById<EditText>(R.id.phoneNumber3)
        val apply = findViewById<EditText>(R.id.positionInput3)
        val add = findViewById<ImageButton>(R.id.createUser)
        val back = findViewById<ImageButton>(R.id.backcrud)
        val radioGroup= findViewById<RadioGroup>(R.id.radioGroup)

        add.setOnClickListener{
            var name = fullname.text.toString()
            var usern=username.text.toString()
            var pass=password.text.toString()
            var emailu=email.text.toString()
            var countryu=country.text.toString()
            var cityu=  city.text.toString()
            var addressu=address.text.toString()
            var cod=code.text.toString()
            var cel=phone.text.toString()
            var applied=apply.text.toString()

            if (radioGroup.getCheckedRadioButtonId() == -1)
            {
                Toast.makeText(this,"Please select a user type", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                if(name=="" || usern=="" || pass==""||emailu==""||countryu==""||cityu==""||addressu==""||cod==""
                    ||cel==""||applied==""){
                    Toast.makeText(this,"Please fill all the fields", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
               var idr= radioGroup.checkedRadioButtonId
                var selected= findViewById<RadioButton>(idr)
               var role =selected.text.toString()
                var key=(1..7).random()
                var photo=hashMap.get(key)
                users.addPersona(User(usern,pass,name, photo!!,role,cityu,
                    addressu,cod,countryu,emailu,cel,applied))
                Toast.makeText(this,"added log", Toast.LENGTH_SHORT).show()
            }


        }

        back.setOnClickListener{
            val crud = Intent(this, CrudPersonas::class.java)
            crud.putExtra("msg", "MENSAJE DE JobApp al Menú")
            crud.putExtra("user", user)
            startActivity(crud)

        }

    }

    private fun initHashmap()
    {
        hashMap.put(1,R.drawable.foto01)
        hashMap.put(2,R.drawable.foto02)
        hashMap.put(3,R.drawable.foto03)
        hashMap.put(4,R.drawable.foto04)
        hashMap.put(5,R.drawable.foto05)
        hashMap.put(6,R.drawable.foto06)
        hashMap.put(7,R.drawable.foto07)
    }
}
