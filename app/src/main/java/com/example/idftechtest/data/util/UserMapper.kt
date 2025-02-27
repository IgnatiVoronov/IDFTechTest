package com.example.idftechtest.data.util

import com.example.idftechtest.data.database.UserEntity
import com.example.idftechtest.data.model.Address
import com.example.idftechtest.data.model.User

fun User.toEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone,
        city = this.address.city
    )
}

fun UserEntity.toUser (): User {
    return User(
        id = this.id,
        name = this.name,
        email = this.email,
        phone = this.phone,
        address = Address(city = this.city)
    )
}