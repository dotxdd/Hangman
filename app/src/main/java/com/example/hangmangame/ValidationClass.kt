package com.example.hangmangame

class ValidationClass {
    companion object {
        fun isEmailValid(email: String): Boolean {
            val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")
            return email.matches(emailPattern)
        }
    }
}