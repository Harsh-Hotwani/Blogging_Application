package com.example.bloggingapplication.model

data class BlogItemModel(
    val heading: String,
    val userName: String,
    val date: String,
    val post: String,
    val likeCount: Int,
    val imageUri: String
)
