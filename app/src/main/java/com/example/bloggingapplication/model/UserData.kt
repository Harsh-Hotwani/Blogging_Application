package com.example.bloggingapplication.model

data class UserData(
    val name:String="",
    val email:String="",
    val password:String="",
    val profileImage:String=""
){
    constructor(): this (" ", "","","")
}
