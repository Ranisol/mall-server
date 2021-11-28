package com.narea.mall.backend.api.controller


import com.narea.mall.LoginRequest
import com.narea.mall.UserCreateRequest
import com.narea.mall.service.AuthService
import com.narea.mall.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.Email

@Tag(name = "auth", description = "인증 api")
@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val authService: AuthService,
    private val userService: UserService
) {
    @PostMapping("/login")
    fun login(
        @RequestBody @Valid
        loginRequest: LoginRequest
    ) = authService.login(loginRequest)

    @PostMapping("/signup")
    fun signup(
        @RequestBody @Valid
        userCreateRequest: UserCreateRequest
    ) = userService.createUser(userCreateRequest)

    @PostMapping("/reissue")
    fun reissue() = {}

    @PostMapping("/logout")
    fun logout() {}
}