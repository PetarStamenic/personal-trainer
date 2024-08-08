package work.stamenic.userservice.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import lombok.Data
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
class GymManager(
    var gymName: String,
    var employmentDate: Long,
    user: String,
    pass: String,
    email: String,
    dateOfBirth: Long,
    firstName: String,
    lastName: String
) : User(
    id = 0,
    user = user,
    pass = pass,
    email = email,
    dateOfBirth = dateOfBirth,
    firstName = firstName,
    lastName = lastName,
    status = true,
    role = UserRole.GYM_MANAGER
)