package com.practice.jsonplaceholderapp.api

import com.practice.jsonplaceholderapp.model.PostRequest
import com.practice.jsonplaceholderapp.model.PostResponse
import com.practice.jsonplaceholderapp.model.PostReturn
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface JSONService {

    @GET("/posts")
    fun getPosts(): Deferred<List<PostReturn>>

    @GET("/posts/{id}")
    fun getPost(@Path("id") id: Int): Deferred<PostReturn>

    @POST("/posts")
    fun createPost(@Body post: List<PostRequest>): Deferred<PostResponse>

    @PUT("/posts/{id}")
    fun updatePost(@Path("id") id: Int, @Body post: PostReturn): Deferred<PostResponse>
}