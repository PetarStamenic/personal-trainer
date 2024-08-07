package work.stamenic.userservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class GymManager(
    @Column(nullable = false)
    val gymName: String,

    @Column(nullable = false)
    val employmentDate: Long,

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
    role = UserRole.GYM_MANAGER
)