package work.stamenic.userservice.model

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
open class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(nullable = false, unique = true)
    val username: String,
    @Column(nullable = false)
    val password: String,
    @Column(nullable = false, unique = true)
    val email: String,
    @Column(nullable = false)
    val dateOfBirth: Long,
    @Column(nullable = false)
    val firstName: String,
    @Column(nullable = false)
    val lastName: String,
    @Column(nullable = false)
    val active: Boolean,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }
    override fun getPassword(): String = password
    override fun getUsername(): String = username
    override fun isAccountNonExpired(): Boolean = active
    override fun isAccountNonLocked(): Boolean = active
    override fun isCredentialsNonExpired(): Boolean = active
    override fun isEnabled(): Boolean = active
}