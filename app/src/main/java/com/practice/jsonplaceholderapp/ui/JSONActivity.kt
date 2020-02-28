package com.practice.jsonplaceholderapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.practice.jsonplaceholderapp.R

class JSONActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_json)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, JSONListFragment()).commit()
    }
}
