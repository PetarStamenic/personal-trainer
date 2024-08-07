package work.stamenic.userservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Client(
    @Column(nullable = false, unique = true)
    val membershipCardNumber: String,
    @Column(nullable = false)
    val bookedTrainings: Int,
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
    role = UserRole.CLIENT
)
