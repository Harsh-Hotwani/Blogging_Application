package com.example.bloggingapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.bloggingapplication.Adapter.BlogAdapter
import com.example.bloggingapplication.databinding.ActivityMainBinding
import com.example.bloggingapplication.model.BlogItemModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var databaseReference:DatabaseReference
    private val blogItems = mutableListOf<BlogItemModel>()
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        databaseReference=FirebaseDatabase.getInstance("https://blogging-application-b9676-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child("blogs")

        val userId = auth.currentUser?.uid

        if(userId!=null){
            loadUserProfileImage(userId)
        }

        val recyclerView = binding.recyclerView
        val blogAdapter = BlogAdapter(blogItems)
        recyclerView.adapter=blogAdapter
        recyclerView.layoutManager=LinearLayoutManager(this)

        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot in snapshot.children)
                {
                    val blogitem = snapshot.getValue(BlogItemModel::class.java)
                    if (blogitem!=null){
                        blogItems.add(blogitem)
                    }
                }
                blogAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "something went wrong to fetch the blogs", Toast.LENGTH_SHORT).show()
            }

        })

        binding.floatingAddArticleButton.setOnClickListener{
            startActivity(Intent(this,AddArticleActivity::class.java))
        }
    }

    private fun loadUserProfileImage(userId: String) {
        val userReference = FirebaseDatabase.getInstance("https://blogging-application-b9676-default-rtdb.asia-southeast1.firebasedatabase.app/").reference.child("users").child(userId)
        userReference.child("profileImage").addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val profileImageUrl = snapshot.getValue(String::class.java)

                if (profileImageUrl!=null){
                    Glide.with(this@MainActivity)
                        .load(profileImageUrl)
                        .into(binding.imageView6)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "error to load a profile image", Toast.LENGTH_SHORT).show()
            }
        })
    }
}