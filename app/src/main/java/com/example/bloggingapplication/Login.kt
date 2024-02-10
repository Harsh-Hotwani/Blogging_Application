package com.example.bloggingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Button
import com.example.bloggingapplication.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private val binding:ActivityLoginBinding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()

        binding.button2.setOnClickListener {
            startActivity(Intent(this,Registration::class.java))
            finish()
        }


    }
}