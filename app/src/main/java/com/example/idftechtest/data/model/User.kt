package com.example.idftechtest.data.model

data class User(
    val id: Int = 0,
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val address: Address = Address("")
)

data class Address(
    val city: String = ""
)
