package com.example.academy_tbc.domain.model.users

data class GetUsers(
    val page: Int,
    val perPage: Int,
    val total: Int,
    val totalPages: Int,
    val data: List<GetUser>,
) {
    data class GetUser(
        val id: Int,
        val email: String,
        val firstName: String,
        val lastName: String,
        val avatar: String,
    )
}
