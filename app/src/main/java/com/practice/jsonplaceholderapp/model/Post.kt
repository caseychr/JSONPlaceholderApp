package com.practice.jsonplaceholderapp.model

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

//@Entity
data class PostReturn(@SerializedName("title") val title: String,
                @SerializedName("body") val body: String,
                @SerializedName("userId") val userId: Int,
                @SerializedName("id") val id: Int)

data class PostRequest(@SerializedName("title") val title: String,
                       @SerializedName("body") val body: String,
                       @SerializedName("userId") val userId: String)

data class PostResponse(@SerializedName("0") val post: PostRequest,
                        @SerializedName("id") val id: Int)