package com.example.uiexamples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bTExample = findViewById(R.id.bTExample) as Button
        var eTExample = findViewById(R.id.eTExample) as EditText
        var tVExample = findViewById(R.id.tVExample) as TextView
        bTExample.setOnClickListener {
            // make a toast on button click event
            Toast.makeText(this, "Hola Mundo", Toast.LENGTH_LONG).show()
            tVExample.setText(eTExample.text)

        }
    }
}