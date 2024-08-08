package work.stamenic.userservice.model

import jakarta.persistence.Entity
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
class Admin (
    user: String,
    pass: String,
    email: String,
    dateOfBirth: Long,
    firstName: String,
    lastName: String
) : User(
    0,
    user = user,
    pass = pass,
    email = email,
    dateOfBirth = dateOfBirth,
    firstName = firstName,
    lastName = lastName,
    status = true,
    role = UserRole.ADMIN
)