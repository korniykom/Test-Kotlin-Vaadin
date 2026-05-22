package com.korniykom.testtask.service

import com.korniykom.testtask.domain.models.Role
import com.korniykom.testtask.domain.models.User
import com.korniykom.testtask.repository.UserRepository
import jakarta.persistence.criteria.Predicate
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) : UserDetailsService {
    fun getUsers(name: String?, email: String?, pageable: Pageable): Page<User> {
        val spec = Specification<User> { userTable, _, criteriaBuilder ->

            val predicates = mutableListOf<Predicate>()
            if (!name.isNullOrBlank()) {
                predicates.add(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(userTable.get("name")),
                        "%${name.lowercase()}%"
                    )
                )
            }
            if (!email.isNullOrBlank()) {
                predicates.add(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(userTable.get("email")),
                        "%${email.lowercase()}%"
                    )
                )
            }
            criteriaBuilder.and(*predicates.toTypedArray())
        }
        return userRepository.findAll(spec, pageable)
    }

    fun createUser(name: String, email: String, password: String, role: Role = Role.USER): User {
        val user = User(
            name = name,
            email = email,
            password = passwordEncoder.encode(password)!!,
            role = role,
        )
        return userRepository.save(user)
    }

    fun updateUser(id: String, name: String, email: String): User {
        val user = userRepository.findById(id).orElseThrow { IllegalArgumentException("User $id not found") }
        user.name = name
        user.email = email
        user.updatedAt = LocalDateTime.now()
        return userRepository.save(user)
    }

    fun deleteUser(id: String) {
        userRepository.deleteById(id)
    }

    fun findByName(name: String): User? {
        return userRepository.findByName(name)
    }

    fun countUsers(name: String?, email: String?): Long {
        val spec = Specification<User> { userTable, _, cb ->
            val predicates = mutableListOf<Predicate>()

            if (!name.isNullOrBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(userTable.get("name")),
                        "%${name.lowercase()}%"
                    )
                )
            }

            if (!email.isNullOrBlank()) {
                predicates.add(
                    cb.like(
                        cb.lower(userTable.get("email")),
                        "%${email.lowercase()}%"
                    )
                )
            }

            cb.and(*predicates.toTypedArray())
        }

        return userRepository.count(spec)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username)
            ?: throw UsernameNotFoundException("User $username not found")
        println("Loading user: ${user.name}, role: ${user.role.name}")
        return org.springframework.security.core.userdetails.User(
            user.name,
            user.password,
            listOf(SimpleGrantedAuthority("ROLE_${user.role.name}"))
        )
    }
}