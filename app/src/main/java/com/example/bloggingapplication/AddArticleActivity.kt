package com.example.bloggingapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.bloggingapplication.databinding.ActivityAddArticleBinding
import com.example.bloggingapplication.model.BlogItemModel
import com.example.bloggingapplication.model.UserData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.Date

class AddArticleActivity : AppCompatActivity() {
    private val binding:ActivityAddArticleBinding by lazy {
        ActivityAddArticleBinding.inflate(layoutInflater)
    }

    private val databaseReference:DatabaseReference = FirebaseDatabase.getInstance("https://blogging-application-b9676-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("blogs")
    private val userReference:DatabaseReference = FirebaseDatabase.getInstance("https://blogging-application-b9676-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference("users")
    private val auth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.addBlog.setOnClickListener{
            val title = binding.textInputLayout.editText?.text.toString().trim()
            val description = binding.textInputLayout2.editText?.text.toString().trim()

            if(title.isEmpty() && description.isEmpty()){
                Toast.makeText(this, "enter sufficient details", Toast.LENGTH_SHORT).show()
            }
            val user:FirebaseUser? = auth.currentUser

            if(user!=null){
                val userId = user.uid
                val userName = user.displayName?:"anoun"
                val userImageUrl = user.photoUrl?:""

                userReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val userData = snapshot.getValue(UserData::class.java)
                        if (userData!=null){
                            val userNameFromDB = userData.name
                            val userImageUrlFromDB = userData.profileImage
                            val currentDate = SimpleDateFormat("yyyy-mm-dd").format(Date())

                            val blogItem = BlogItemModel(
                                title,
                                userNameFromDB,
                                currentDate,
                                description,
                                0,
                                userImageUrlFromDB
                            )
                            val key = databaseReference.push().key
                            if(key!=null){
                                val blogReference = databaseReference.child(key)
                                blogReference.setValue(blogItem).addOnCompleteListener {
                                    if (it.isSuccessful){
                                        finish()
                                    }
                                    else{
                                        Toast.makeText(this@AddArticleActivity, "failed to add blog", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }
        }
    }
}