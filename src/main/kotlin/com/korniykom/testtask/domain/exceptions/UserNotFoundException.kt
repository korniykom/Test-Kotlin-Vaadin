package com.korniykom.testtask.domain.exceptions

class UserNotFoundException(id: String): RuntimeException("User with id $id not found") {
}