package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.databinding.ItemPostBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.home.model.PostUi

class PostAdapter() :
    ListAdapter<PostUi, PostAdapter.PostViewHolder>(PostDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
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

class PostDiffUtil : DiffUtil.ItemCallback<PostUi>() {
    override fun areItemsTheSame(
        oldItem: PostUi, newItem: PostUi,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: PostUi, newItem: PostUi,
    ): Boolean {
        return oldItem == newItem
    }

}