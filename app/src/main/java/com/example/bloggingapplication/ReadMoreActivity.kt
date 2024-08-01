package com.example.bloggingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bloggingapplication.databinding.ActivityReadMoreBinding
import com.example.bloggingapplication.model.BlogItemModel

class ReadMoreActivity : AppCompatActivity() {

    private val binding: ActivityReadMoreBinding by lazy {
        ActivityReadMoreBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val blogs = intent.getParcelableExtra<BlogItemModel>("blogItem")
        if (blogs!=null)
        {
            binding.titleReadMore.text = blogs.heading
            binding.name.text = blogs.userName
            binding.dateOfReadMore.text = blogs.date
            binding.detailedDes.text = blogs.post
        }
        else{
            Toast.makeText(this, "failed to load post", Toast.LENGTH_SHORT).show()
        }


    }
}