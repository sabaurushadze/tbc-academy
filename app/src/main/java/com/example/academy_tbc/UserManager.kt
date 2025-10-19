package com.example.academy_tbc

object UserManager {
    private val users = mutableMapOf<String, User>()
    private var deletedUsers = 0
    private var operation: Operation = Operation.PENDING

    val activeUsers: Int get() = users.size
    val deletedUserCount: Int get() = deletedUsers
    val currentOperation: Operation get() = operation

    fun addUser(user: User, email: String) {
        users[email] = user
    }

    fun updateUser(user: User, email: String) {
        users[email] = user
    }

    fun removeUser(email: String) {
        users.remove(email)
        deletedUsers++
    }

    fun setOperation(newOperation: Operation) {
        operation = newOperation
    }

    fun getRandomUser(): String {
        return users.keys.random()
    }

    fun containsEmail(email: String): Boolean {
        return users.contains(email)
    }

    fun getUserByEmail(email: String): User? {
        return users[email]
    }

}

enum class Operation {
    SUCCESS, FAILURE, PENDING
}