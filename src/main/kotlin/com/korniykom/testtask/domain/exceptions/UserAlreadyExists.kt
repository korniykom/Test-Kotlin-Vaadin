package com.korniykom.testtask.domain.exceptions

class UserAlreadyExists(email: String): RuntimeException("User with email $email already exists") {
}