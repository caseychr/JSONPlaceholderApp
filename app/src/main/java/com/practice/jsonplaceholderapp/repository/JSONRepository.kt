package com.practice.jsonplaceholderapp.repository

import android.content.Context
import com.practice.jsonplaceholderapp.api.JSONService
import com.practice.jsonplaceholderapp.api.PlaceholderApi
import com.practice.jsonplaceholderapp.model.PostRequest
import com.practice.jsonplaceholderapp.model.PostResponse
import com.practice.jsonplaceholderapp.model.PostReturn
import com.practice.jsonplaceholderapp.model.Resource
import com.practice.jsonplaceholderapp.room.PlaceholderDB
import com.practice.jsonplaceholderapp.room.PlaceholderDao
import kotlinx.coroutines.*

class JSONRepository(private val jsonService: JSONService,
                     private val context: Context) {
//                     private val jsonDao: PlaceholderDao = PlaceholderDB.getInstance(context).placeHolderDao) {

    ///////// Api Call /////////
    suspend fun getPosts(): Resource<List<PostReturn>> {
        return loadApiResource { jsonService.getPosts() }
    }

    suspend fun getPost(id: Int): Resource<PostReturn> {
        return loadApiResource { jsonService.getPost(id) }
    }

    suspend fun createPost(postRequest: List<PostRequest>): Resource<PostResponse> {
        return loadApiResource { jsonService.createPost(postRequest) }
    }

    suspend fun updatePost(postReturn: PostReturn): Resource<PostResponse> {
        return loadApiResource { jsonService.updatePost(postReturn.id, postReturn) }
    }

    ///////// Room /////////
    /*suspend fun insertPostDb(postReturn: PostReturn) {
        jsonDao.insertJson(postReturn)
    }

    suspend fun updatePostDb(postReturn: PostReturn) {
        jsonDao.updateJson(postReturn)
    }

    suspend fun getPostDb(id: Int): Resource<PostReturn> {
        return loadApiResource { jsonDao.getPost(id) }
    }

    suspend fun getPostsDb(): Resource<List<PostReturn>> {
        return loadApiResource { jsonDao.getAllPosts() }
    }

    suspend fun deletePostDb(postReturn: PostReturn) {
        jsonDao.deletePost(postReturn)
    }

    suspend fun deleteAllPostsDb(postReturn: PostReturn) {
        jsonDao.deleteAllPosts()
    }*/



    suspend fun <T, R> loadApiResource(loader: () -> Deferred<T>, mapper: (T) -> R): Resource<R> {
        return try {
            Resource.Success(mapper(loader().await()))
        } catch (error: Exception) {
            error.printStackTrace()
            return Resource.Error(error)
        }
    }

    suspend fun <T> loadApiResource(loader: () -> Deferred<T>): Resource<T> {
        return this.loadApiResource(loader, {it})
    }
}