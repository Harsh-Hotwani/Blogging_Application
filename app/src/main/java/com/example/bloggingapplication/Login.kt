package com.example.bloggingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button
import android.widget.Toast
import com.example.bloggingapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Login : AppCompatActivity() {
    private val binding:ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private lateinit var auth:FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance("https://blogging-application-b9676-default-rtdb.asia-southeast1.firebasedatabase.app/")
        storage = FirebaseStorage.getInstance()

        binding.button2.setOnClickListener {
            startActivity(Intent(this,Registration::class.java))
            finish()
        }
        binding.loginbutton.setOnClickListener{
            val loginemail = binding.editTextTextPersonName.text.toString()
            val loginpassword = binding.editTextTextPassword.text.toString()

            if(loginemail.isEmpty() || loginpassword.isEmpty())
            {
                Toast.makeText(this, "please enter sufficient details", Toast.LENGTH_SHORT).show()
            }
            auth.signInWithEmailAndPassword(loginemail,loginpassword)
                .addOnCompleteListener{task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(this, "login sucessfull", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                    finish()
                }
                else {
                    Toast.makeText(
                        this,
                        "login failed,please enter correct details",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        }


    }
}