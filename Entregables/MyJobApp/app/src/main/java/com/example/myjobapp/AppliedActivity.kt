package com.example.myjobapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myjobapp.model.Company
import com.example.myjobapp.model.JobApplication
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_applied.*
import model.User

class AppliedActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_applied)
        val company = Company("Microsoft", "PART-TIME", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque nec luctus arcu. Quisque id facilisis elit, non gravida arcu. Maecenas at tempus eros, at dignissim sem. In molestie nunc vel nisi feugiat, nec varius nisl ullamcorper. Nam sollicitudin tortor in libero scelerisque malesuada. Duis ipsum elit, tristique ac rhoncus et, sollicitudin quis velit. Nam aliquam urna eu blandit aliquet. Aenean id enim lectus. Donec blandit id libero vitae pellentesque. Pellentesque viverra enim nec porta cursus. Maecenas viverra ultricies accumsan. Vestibulum vulputate quam iaculis rhoncus rhoncus."
            , R.drawable.icons8_microsoft_144)
        var user= User()
        val application1= JobApplication(user,company)
        val applications= listOf(application1)

        val applicationAdapter=ApplicationAdapter(this,applications)
        listaAp.adapter=applicationAdapter

    }
}