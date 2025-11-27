package com.example.academy_tbc.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.R
import com.example.academy_tbc.data.remote.home.model.UsersResponseDto
import com.example.academy_tbc.databinding.ItemUserBinding
import com.example.academy_tbc.presentation.extension.view.loadImage

class UsersAdapter() :
    PagingDataAdapter<UsersResponseDto.User, UsersAdapter.UserViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let {
            holder.bind(user)
        }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UsersResponseDto.User) = with(binding) {
            ivUser.loadImage(user.avatar)
            tvEmail.text = user.email
            tvFullName.text =
                root.context.getString(R.string.full_name, user.firstName, user.lastName)
        }
    }
}

class UserDiffUtil : DiffUtil.ItemCallback<UsersResponseDto.User>() {
    override fun areItemsTheSame(
        oldItem: UsersResponseDto.User, newItem: UsersResponseDto.User,
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UsersResponseDto.User, newItem: UsersResponseDto.User,
    ): Boolean {
        return oldItem == newItem
    }

}