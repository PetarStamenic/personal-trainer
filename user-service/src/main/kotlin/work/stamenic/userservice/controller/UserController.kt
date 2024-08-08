package work.stamenic.userservice.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import work.stamenic.userservice.model.dto.LoginRequestDto
import work.stamenic.userservice.security.JwtUtil
import work.stamenic.userservice.service.UserService
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.web.bind.annotation.*
@RestController
@RequestMapping("/users")
@CrossOrigin(origins = ["*"])
class UserController (
    var userService: UserService,
    var jwtUtil: JwtUtil,
    var authenticationManager: AuthenticationManager
){
    @Operation(summary = "User login", description = "Authenticate user and return JWT token")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Login successful",
            content = [Content(mediaType = "application/json", schema = Schema(implementation = String::class))]),
        ApiResponse(responseCode = "401", description = "Invalid credentials")
    ])
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequestDto): ResponseEntity<String> {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginRequest.username,
                    loginRequest.password
                )
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseEntity.status(401).build()
        }
        return ResponseEntity.ok(jwtUtil.generateToken(loginRequest.username))
    }
}