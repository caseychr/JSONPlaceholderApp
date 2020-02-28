package com.practice.jsonplaceholderapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practice.jsonplaceholderapp.api.PlaceholderApi
import com.practice.jsonplaceholderapp.model.PostRequest
import com.practice.jsonplaceholderapp.model.PostResponse
import com.practice.jsonplaceholderapp.model.PostReturn
import com.practice.jsonplaceholderapp.model.Resource
import com.practice.jsonplaceholderapp.repository.JSONRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class JSONViewModel(application: Application) : AndroidViewModel(application) {

    private var repo: JSONRepository = JSONRepository(PlaceholderApi.service(), application.applicationContext)

    val getAllJsonLiveData = MutableLiveData<Resource<List<PostReturn>>>()
    val getJsonLiveData = MutableLiveData<Resource<PostReturn>>()
    val jsonLiveData = MutableLiveData<Resource<PostResponse>>()

    fun getPosts() {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            getAllJsonLiveData.value = repo.getPosts()
        }
    }

    fun getPost(id: Int) {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            getJsonLiveData.value = repo.getPost(id)
        }
    }

    fun createPost(postRequest: PostRequest) {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            val list = mutableListOf<PostRequest>()
            list.add(postRequest)
            jsonLiveData.value = repo.createPost(list)
        }
    }

    fun updatePost(postReturn: PostReturn) {
        CoroutineScope(Job() + Dispatchers.Main).launch {
            jsonLiveData.value = repo.updatePost(postReturn)
        }
    }


}