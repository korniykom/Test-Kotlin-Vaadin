package com.korniykom.testtask.domain.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

enum class Role {
    USER, ADMIN
}

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(GenerationType.UUID)
    val id: String = "",

    @Column
    var name: String,

    @Column
    var email: String,

    @Column
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column
    var role: Role,

    @Column
    var createdAt: LocalDateTime = LocalDateTime.now(),

    @Column
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)