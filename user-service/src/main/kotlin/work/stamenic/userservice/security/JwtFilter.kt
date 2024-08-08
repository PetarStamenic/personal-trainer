package work.stamenic.userservice.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtFilter(
    var userService: CustomUserDetailService,
    var jwtUtil: JwtUtil
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")

        var jwt: String? = null
        var username: String? = null

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwt = authHeader.substring(7)
            username = jwtUtil.extractUsername(jwt)
        }

        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userService.loadUserByUsername(username)

            if (jwtUtil.validateToken(jwt, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details =
                    WebAuthenticationDetailsSource().buildDetails(request)

                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }

        filterChain.doFilter(request, response)
    }
}