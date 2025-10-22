package com.example.academy_tbc.screen.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.academy_tbc.databinding.UserItemLayoutBinding

class UsersAdapter(
    private val users: MutableList<UserItem>,
    private val onItemClick: (UserItem) -> Unit
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {

    inner class UserViewHolder(private val binding: UserItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(userItem: UserItem) = with(binding) {
            tvFirstName.text = userItem.firstName
            tvLastName.text = userItem.lastName
            tvAge.text = userItem.age.toString()
            tvEmail.text = userItem.email

            root.setOnLongClickListener {
                onItemClick(userItem)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            binding = UserItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(
        holder: UserViewHolder, position: Int
    ) {
        holder.bind(users[position])
    }

    override fun getItemCount() = users.size
}