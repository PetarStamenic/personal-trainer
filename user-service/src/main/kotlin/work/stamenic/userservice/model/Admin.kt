package work.stamenic.userservice.model

import jakarta.persistence.Entity

@Entity
class Admin (
    username: String,
    password: String,
    email: String,
    dateOfBirth: Long,
    firstName: String,
    lastName: String
) : User(
    id = 0,
    username = username,
    password = password,
    email = email,
    dateOfBirth = dateOfBirth,
    firstName = firstName,
    lastName = lastName,
    active = true,
    role = UserRole.ADMIN
)