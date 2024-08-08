package work.stamenic.userservice.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import work.stamenic.userservice.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.Claims
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import work.stamenic.userservice.model.User
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.HashMap

@Component
class JwtUtil(
    var userRepository: UserRepository
) {
    @Value("\${jwt.secret}")
    private lateinit var secretKey: String

    fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body
    }

    fun extractUsername(token: String?): String {
        return extractAllClaims(token).subject
    }

    fun isTokenExpired(token: String?): Boolean {
        return extractAllClaims(token).expiration.before(Date())
    }

    fun generateToken(username: String?): String? {
        if(username.isNullOrEmpty())
            throw IllegalArgumentException("username cannot be null")
        var user: User? = userRepository.findByUserAndStatusTrue(username)
        var claims: MutableMap<String, Any> = HashMap()
        if(user == null)
            throw IllegalArgumentException("user not found")
        claims["type"] = user.role.name
        claims["id"] = user.id
        return Jwts.builder()
            .setClaims(claims)
            .setSubject(username)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60))
            .signWith(SignatureAlgorithm.HS512, secretKey).compact()
    }

    fun validateToken(token: String?, user: UserDetails): Boolean {
        return user.username == extractUsername(token) && !isTokenExpired(token)
    }
}