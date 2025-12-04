package com.example.academy_tbc.presentation.screen.users.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.ItemUserBinding
import com.example.academy_tbc.presentation.extension.loadImage
import com.example.academy_tbc.presentation.screen.users.model.UserUi

class UsersAdapter(
    private val onUserClick: (UserUi) -> Unit,
    private val onUserLongClick: (UserUi) -> Unit
) :
    ListAdapter<UserUi, UsersAdapter.UserViewHolder>(UserDiffUtil()) {
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserUi) = with(binding) {
            tvFullName.text = user.fullName
            tvEmail.text = user.email
            tvActiveStatus.setText(user.activationStatus)
            ivUser.loadImage(user.profileImageUrl)

            root.setOnClickListener {
                onUserClick(user)
            }

            root.setOnLongClickListener {
                onUserLongClick(user)
                true
            }
        }
    }
}

class UserDiffUtil : DiffUtil.ItemCallback<UserUi>() {
    override fun areItemsTheSame(
        oldItem: UserUi, newItem: UserUi
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserUi, newItem: UserUi
    ): Boolean {
        return oldItem == newItem
    }

}