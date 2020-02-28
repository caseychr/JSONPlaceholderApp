package com.practice.jsonplaceholderapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.practice.jsonplaceholderapp.R
import com.practice.jsonplaceholderapp.model.*
import com.practice.jsonplaceholderapp.viewmodel.JSONViewModel
import kotlinx.android.synthetic.main.fragment_detail_json.*

class JSONDetailFragment : Fragment() {

    private lateinit var viewModel: JSONViewModel
    private var postId: Int? = null

    override fun onResume() {
        super.onResume()
        subscribeObservers()
        if(arguments != null) {
            if(arguments!!.containsKey(POST_ID)) {
                postId = arguments!!.getInt(POST_ID)
                viewModel.getPost(postId!!)
            }
        }
        buttonClick(postId == null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail_json, container, false)
    }

    private fun subscribeObservers() {
        viewModel = ViewModelProvider(this)[JSONViewModel::class.java]
        viewModel.getJsonLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getJsonResourceView))
        viewModel.jsonLiveData.observe(viewLifecycleOwner, ResourceViewObserver(jsonResourceView))
    }

    private val getJsonResourceView = object : ResourceView<PostReturn> {
        override fun showData(data: PostReturn) { setJSONPlaceholder(data) }
        override fun showLoading(isLoading: Boolean) { Log.i("JSONDetailFragment", "loading: $isLoading") }
        override fun showError(error: Throwable) { Log.i("JSONDetailFragment", error.message) }
    }

    private val jsonResourceView = object : ResourceView<PostResponse> {
        override fun showData(data: PostResponse) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, JSONListFragment())?.commit()
        }
        override fun showLoading(isLoading: Boolean) { Log.i("JSONDetailFragment", "loading: $isLoading") }
        override fun showError(error: Throwable) { Log.i("JSONDetailFragment", error.message) }

    }

    private fun setJSONPlaceholder(postReturn: PostReturn) {
        jsonTitleEditText.setText(postReturn.title)
        jsonBodyEditText.setText(postReturn.body)
        jsonUserIdEditText.setText(postReturn.userId.toString())
        jsonIdTextView.text = postReturn.id.toString()
        addJsonButton.text = UPDATE
    }

    private fun buttonClick(createNew: Boolean) {
        addJsonButton.setOnClickListener {
            if (createNew) {
                createPost(PostRequest(jsonTitleEditText.text.toString(), jsonBodyEditText.text.toString(),
                    jsonUserIdEditText.text.toString()))
            } else {
                updatePost(PostReturn(jsonTitleEditText.text.toString(), jsonBodyEditText.text.toString(),
                    jsonUserIdEditText.text.toString().toInt(), jsonIdTextView.text.toString().toInt()))
            }
        }
    }

    private fun createPost(postRequest: PostRequest) {
        viewModel.createPost(postRequest)
    }

    private fun updatePost(postReturn: PostReturn) {
        viewModel.updatePost(postReturn)
    }

    companion object {
        fun newInstance(id: Int? = null): JSONDetailFragment {
            val fragment = JSONDetailFragment()
            if(id != null) {
                val bundle = Bundle()
                bundle.putInt(POST_ID, id)
                fragment.arguments = bundle
            }
            return fragment
        }
    }
}

private const val POST_ID = "POST_ID"
private const val UPDATE = "Update"