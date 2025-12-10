package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemHomeLocationsBinding
import com.example.academy_tbc.databinding.ItemHomePostsBinding
import com.example.academy_tbc.presentation.screen.home.model.LocationUi
import com.example.academy_tbc.presentation.screen.home.model.PostUi

sealed class HomeFeedItem {
    data class Locations(val list: List<LocationUi>) : HomeFeedItem()
    data class Posts(val list: List<PostUi>) : HomeFeedItem()
}

class HomeFeedAdapter :
    ListAdapter<HomeFeedItem, RecyclerView.ViewHolder>(HomeFeedDiffUtil()) {

    companion object {
        private const val TYPE_LOCATIONS = 0
        private const val TYPE_POST = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is HomeFeedItem.Locations -> TYPE_LOCATIONS
            is HomeFeedItem.Posts -> TYPE_POST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOCATIONS -> LocationsViewHolder(
                ItemHomeLocationsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> PostsViewHolder(
                ItemHomePostsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LocationsViewHolder ->
                holder.bind((getItem(position) as HomeFeedItem.Locations).list)

            is PostsViewHolder ->
                holder.bind((getItem(position) as HomeFeedItem.Posts).list)
        }
    }

    inner class LocationsViewHolder(private val binding: ItemHomeLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val locationAdapter by lazy { LocationAdapter() }

        init {
            binding.rvLocations.apply {
                layoutManager = LinearLayoutManager(
                    binding.root.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = locationAdapter
            }
        }

        fun bind(locations: List<LocationUi>) {
            locationAdapter.submitList(locations)
        }
    }

    inner class PostsViewHolder(private val binding: ItemHomePostsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val postAdapter by lazy { PostAdapter() }

        init {
            binding.rvPosts.apply {
                layoutManager = LinearLayoutManager(binding.root.context)
                adapter = postAdapter
            }
        }

        fun bind(posts: List<PostUi>) {
            postAdapter.submitList(posts)
        }
    }
}

class HomeFeedDiffUtil : DiffUtil.ItemCallback<HomeFeedItem>() {
    override fun areItemsTheSame(oldItem: HomeFeedItem, newItem: HomeFeedItem): Boolean {
        return when (oldItem) {
            is HomeFeedItem.Locations if newItem is HomeFeedItem.Locations ->
                oldItem.list == newItem.list

            is HomeFeedItem.Posts if newItem is HomeFeedItem.Posts ->
                oldItem.list == newItem.list

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: HomeFeedItem, newItem: HomeFeedItem): Boolean {
        return oldItem == newItem
    }
}