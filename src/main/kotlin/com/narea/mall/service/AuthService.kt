package com.narea.mall.service


import com.narea.mall.LoginRequest
import com.narea.mall.ReissueRequest
import com.narea.mall.TokenResponse
import com.narea.mall.auth.CustomUserDetails
import com.narea.mall.auth.JwtTokenProvider
import com.narea.mall.entity.RefreshToken
import com.narea.mall.exception.BadRequestException
import com.narea.mall.repository.RefreshTokenRepository
import com.sun.security.auth.UserPrincipal
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userService: UserService
) {
    fun login(loginRequest:LoginRequest):TokenResponse {
        val user = userService.getUser(loginRequest.email)
        val authenticationToken = UsernamePasswordAuthenticationToken(
            User(user.email, user.password, user.role.map { SimpleGrantedAuthority(it.name) }),
            loginRequest.password
        )
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val accessToken = jwtTokenProvider.generateAccessToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken(authentication)
        refreshTokenRepository.save(
            RefreshToken().apply {
                email = loginRequest.email
                token = refreshToken
            }
        )
        return TokenResponse(
            accessToken, refreshToken
        )
    }
    fun reissue(tokenRequest: ReissueRequest):TokenResponse {
        val authentication = jwtTokenProvider.getAuthenticationFromAccessToken(tokenRequest.accessToken)
        val prevRefreshToken = refreshTokenRepository.getRefreshTokenByEmail(authentication.name) ?: throw BadCredentialsException("존재하지 않거나 만료된 refreshToken 입니다.")
        if(tokenRequest.refreshToken != prevRefreshToken.token) throw BadCredentialsException("적합하지 않은 refreshToken입니다.")
        val newAccessToken = jwtTokenProvider.generateAccessToken(authentication)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken(authentication)
        refreshTokenRepository.save(RefreshToken().apply {
            email = authentication.name
            token = newRefreshToken
        })
        return TokenResponse(
            newAccessToken,
            newRefreshToken
        )
    }
}