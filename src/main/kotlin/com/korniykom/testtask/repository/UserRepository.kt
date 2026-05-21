package com.korniykom.testtask.repository

import com.korniykom.testtask.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface UserRepository: JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    fun findByEmail(email: String): User?
}