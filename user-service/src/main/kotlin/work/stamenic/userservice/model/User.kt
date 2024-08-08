package work.stamenic.userservice.model

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
open class User (
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long,
    @Column(unique = true)
    open var user: String,
    open var pass: String,
    @Column(unique = true)
    open var email: String,
    open var dateOfBirth: Long,
    open var firstName: String,
    open var lastName: String,
    open var status: Boolean,
    @Enumerated(EnumType.STRING)
    open var role: UserRole
): UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.name))
    }
    override fun getPassword(): String = pass
    override fun getUsername(): String = user
    override fun isAccountNonExpired(): Boolean = status
    override fun isAccountNonLocked(): Boolean = isAccountNonExpired
    override fun isCredentialsNonExpired(): Boolean = status
    override fun isEnabled(): Boolean = status
}