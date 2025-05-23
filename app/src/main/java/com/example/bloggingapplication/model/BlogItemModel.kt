package com.example.bloggingapplication.model

import android.os.Parcel
import android.os.Parcelable

data class BlogItemModel(
    val heading: String? ="null",
    val userName: String? ="null",
    val date: String? ="null",
    val post: String? ="null",
    val likeCount: Int=0,
    val profileImage: String? ="null"
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"null",
        parcel.readString()?:"null",
        parcel.readString()?:"null",
        parcel.readString()?:"null",
        parcel.readInt(),
        parcel.readString()?:"null"
    ) {

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(heading)
        parcel.writeString(userName)
        parcel.writeString(date)
        parcel.writeString(post)
        parcel.writeInt(likeCount)
        parcel.writeString(profileImage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BlogItemModel> {
        override fun createFromParcel(parcel: Parcel): BlogItemModel {
            return BlogItemModel(parcel)
        }

        override fun newArray(size: Int): Array<BlogItemModel?> {
            return arrayOfNulls(size)
        }
    }

}
