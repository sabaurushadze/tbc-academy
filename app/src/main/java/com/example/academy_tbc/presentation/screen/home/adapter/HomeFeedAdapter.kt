package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemHomeFeedLocationsBinding
import com.example.academy_tbc.databinding.ItemPostBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.home.adapter.decoration.HorizontalMarginItemDecoration
import com.example.academy_tbc.presentation.screen.home.model.LocationUi
import com.example.academy_tbc.presentation.screen.home.model.PostUi

sealed class HomeFeedItem {
    data class Locations(val locations: List<LocationUi>) : HomeFeedItem()
    data class Post(val post: PostUi) : HomeFeedItem()
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
            is HomeFeedItem.Post -> TYPE_POST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_LOCATIONS -> LocationsViewHolder(
                ItemHomeFeedLocationsBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

            else -> PostViewHolder(
                ItemPostBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is HomeFeedItem.Locations -> (holder as LocationsViewHolder).bind(item.locations)
            is HomeFeedItem.Post -> (holder as PostViewHolder).bind(item.post)
        }
    }

    inner class LocationsViewHolder(binding: ItemHomeFeedLocationsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val adapter = LocationAdapter()

        init {
            binding.rvLocations.adapter = adapter
            binding.rvLocations.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            binding.rvLocations.addItemDecoration(HorizontalMarginItemDecoration(28, 28, 19))
        }

        fun bind(locations: List<LocationUi>) {
            adapter.submitList(locations)
        }
    }

    inner class PostViewHolder(private val binding: ItemPostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostUi) = with(binding) {
            ivPostAuthorPfp.loadImage(post.avatar)
            tvPostAuthor.text =
                root.context.getString(R.string.full_name, post.firstName, post.lastName)
            tvCreatedAt.text = post.postDate
            tvDescription.text = post.postDesc
            tvCommentAmount.text = root.context.getString(R.string.comments, post.commentsCount)
            tvLikeAmount.text = root.context.getString(R.string.likes, post.likesCount)

            when (post.images.size) {
                1 -> {
                    iv1.loadImage(post.images[0])
                    iv2.isVisible = false
                    iv3.isVisible = false
                }

                2 -> {
                    iv3.isVisible = false
                    iv2.isVisible = true
                    iv1.loadImage(post.images[0])
                    iv2.loadImage(post.images[1])
                }

                3 -> {
                    iv2.isVisible = true
                    iv3.isVisible = true

                    iv1.loadImage(post.images[0])
                    iv2.loadImage(post.images[1])
                    iv3.loadImage(post.images[2])

                }

                else -> {
                    iv1.loadImage("")
                    iv2.isVisible = false
                    iv3.isVisible = false
                }
            }

            icAttachment.isVisible = post.canPostPhoto
            etComment.isEnabled = post.canComment
        }
    }


}


class HomeFeedDiffUtil : DiffUtil.ItemCallback<HomeFeedItem>() {
    override fun areItemsTheSame(oldItem: HomeFeedItem, newItem: HomeFeedItem): Boolean {
        return when (oldItem) {
            is HomeFeedItem.Locations if newItem is HomeFeedItem.Locations ->
                oldItem.locations == newItem.locations

            is HomeFeedItem.Post if newItem is HomeFeedItem.Post ->
                oldItem.post.firstName == newItem.post.firstName &&
                oldItem.post.lastName == newItem.post.lastName &&
                oldItem.post.postDate == newItem.post.postDate

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: HomeFeedItem, newItem: HomeFeedItem): Boolean {
        return oldItem == newItem
    }
}