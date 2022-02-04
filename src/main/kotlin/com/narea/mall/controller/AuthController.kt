package com.narea.mall.controller
import com.narea.mall.dto.LoginRequest
import com.narea.mall.dto.ReissueRequest
import com.narea.mall.dto.UserCreateRequest
import com.narea.mall.service.AuthService
import com.narea.mall.service.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

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
    fun reissue(
        @RequestBody @Valid
        reissueRequest: ReissueRequest
    ) = authService.reissue(reissueRequest)

    @PostMapping("/logout")
    fun logout() {}
}

