package work.stamenic.userservice.security


import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import work.stamenic.userservice.model.User
import work.stamenic.userservice.repository.UserRepository
import java.lang.IllegalArgumentException


@Service
class CustomUserDetailService(
    val userRepository: UserRepository
) : UserDetailsService{
    override fun loadUserByUsername(username: String?): UserDetails {
        val user: User = username?.let { userRepository.findByUsernameAndActiveTrue(it) }
            ?: throw IllegalArgumentException("user not found")
        return org.springframework.security.core.userdetails.User(
            user.username,user.password,user.authorities
        )
    }
}