package com.practice.jsonplaceholderapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.jsonplaceholderapp.R
import com.practice.jsonplaceholderapp.model.PostReturn

class JSONAdapter(private val onItemClick: OnItemClick) : RecyclerView.Adapter<JSONAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_json, parent, false))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val json = differ.currentList[position]
        holder.id.text = json.id.toString()
        holder.userId.text = json.userId.toString()
        holder.title.text = json.title
        holder.body.text = json.body
        holder.itemView.setOnClickListener {
            onItemClick.onClick(json.id)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.itemJsonTitleTextView)
        var body: TextView = itemView.findViewById(R.id.itemJsonBodyTextView)
        var id: TextView = itemView.findViewById(R.id.itemJsonIdTextView)
        var userId: TextView = itemView.findViewById(R.id.itemJsonUserIdTextView)
    }

    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostReturn>() {
        override fun areItemsTheSame(oldItem: PostReturn, newItem: PostReturn): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PostReturn, newItem: PostReturn): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, DIFF_CALLBACK)

    fun submitList(list: List<PostReturn>) {
        differ.submitList(list)
    }

    interface OnItemClick {
        fun onClick(id: Int)
    }
}
