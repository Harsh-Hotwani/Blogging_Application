package com.example.bloggingapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.bloggingapplication.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ObjectOutputStream.PutField

class Registration : AppCompatActivity() {
    private val binding:ActivityRegistrationBinding by lazy{
        ActivityRegistrationBinding.inflate(layoutInflater)
    }
    private lateinit var auth :FirebaseAuth
    private lateinit var database : FirebaseDatabase
    private lateinit var storage : FirebaseStorage
    private var PICK_IMAGE_REQUEST=1
    private var imageuri:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar!!.hide()

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()


        binding.registerButton.setOnClickListener{
            val registerName = binding.editTextTextPersonName3.text.toString()
            val registerEmail = binding.editTextTextEmailAddress.text.toString()
            val registerPassword = binding.editTextTextPassword2.text.toString()
            
            if (registerName.isEmpty() || registerEmail.isEmpty() || registerPassword.isEmpty()){
                Toast.makeText(this, "please enter a sufficient data", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(registerEmail,registerPassword)
                    .addOnCompleteListener{ task ->
                    Toast.makeText(this, "creating....", Toast.LENGTH_SHORT).show()
                    if (task.isSuccessful){
                        val user = auth.currentUser
                        user?. let{
                            val userReference = database.getReference("users")
                            val userId = user.uid
                            val userData = com.example.bloggingapplication.model.UserData(
                                registerName,
                                registerEmail,
                                registerPassword,
                            )
                            userReference.child(userId).setValue(userData)

                            val storageReference = storage.reference.child("profile_image/$userId.jpg")
                            storageReference.putFile(imageuri!!)
                        }
                    }
                    else{
                        Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        binding.loginButton.setOnClickListener{
            startActivity(Intent(this,Login::class.java))
            finish()
        }

        binding.cardView.setOnClickListener{
            val intent = Intent()
            intent.type="image/*"
            intent.action=Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent , "select a image"),
                PICK_IMAGE_REQUEST
            )
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.data!=null)
            imageuri==data.data
            Glide.with(this)
                .load(imageuri)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.registerImage)
    }

}