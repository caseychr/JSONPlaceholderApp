package com.practice.jsonplaceholderapp.room

import androidx.room.*
import com.practice.jsonplaceholderapp.model.PostReturn
import kotlinx.coroutines.Deferred

@Dao
interface PlaceholderDao {

    @Insert
    fun insertJson(postReturn: PostReturn)

    @Update
    fun updateJson(postReturn: PostReturn)

    @Query("SELECT * FROM postreturn ORDER BY id ASC")
    fun getAllPosts(): Deferred<List<PostReturn>>

    @Query("SELECT * FROM postreturn WHERE id = :id")
    fun getPost(id: Int): Deferred<PostReturn>

    @Delete
    fun deletePost(postReturn: PostReturn)

    @Query("DELETE FROM postreturn")
    fun deleteAllPosts()
}