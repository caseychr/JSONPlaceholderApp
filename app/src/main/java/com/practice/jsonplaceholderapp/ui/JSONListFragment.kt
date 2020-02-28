package com.practice.jsonplaceholderapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.practice.jsonplaceholderapp.R
import com.practice.jsonplaceholderapp.model.PostReturn
import com.practice.jsonplaceholderapp.model.ResourceView
import com.practice.jsonplaceholderapp.model.ResourceViewObserver
import com.practice.jsonplaceholderapp.viewmodel.JSONViewModel
import kotlinx.android.synthetic.main.fragment_list_json.*

class JSONListFragment : Fragment(), JSONAdapter.OnItemClick {

    private lateinit var viewModel: JSONViewModel
    private lateinit var jsonAdapter: JSONAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list_json, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
        initRecyclerView()
        addNewJsonButton.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragmentContainer, JSONDetailFragment.newInstance())?.commit()
        }
        viewModel.getPosts()
    }

    private fun subscribeObservers() {
        viewModel = ViewModelProvider(this)[JSONViewModel::class.java]
        viewModel.getAllJsonLiveData.observe(viewLifecycleOwner, ResourceViewObserver(getAllJsonResourceView))
    }

    private fun initRecyclerView() {
        jsonRecyclerView.apply {
            jsonAdapter = JSONAdapter(this@JSONListFragment)
            layoutManager = LinearLayoutManager(this@JSONListFragment.context)
            adapter = jsonAdapter
        }
    }

    override fun onClick(id: Int) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragmentContainer, JSONDetailFragment.newInstance(id))?.commit()
    }

    private val getAllJsonResourceView = object : ResourceView<List<PostReturn>> {
        override fun showData(data: List<PostReturn>) { jsonAdapter.submitList(data) }
        override fun showLoading(isLoading: Boolean) { Log.i("JSONListFragment", "loading: $isLoading") }
        override fun showError(error: Throwable) { Log.i("JSONListFragment", error.message) }
    }
}