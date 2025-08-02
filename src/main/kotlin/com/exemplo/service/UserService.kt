package com.exemplo.service

import com.exemplo.model.User
import java.util.concurrent.atomic.AtomicInteger

object UserService {
    private val users = mutableListOf<User>()
    private val idCounter = AtomicInteger(1)

    fun getAll(): List<User> = users

    fun getById(id: Int): User? = users.find { it.id == id }

    fun create(name: String, email: String): User {
        val user = User(idCounter.getAndIncrement(), name, email)
        users.add(user)
        return user
    }

    fun delete(id: Int): Boolean = users.removeIf { it.id == id }
}