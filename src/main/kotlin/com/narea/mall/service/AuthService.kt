package com.narea.mall.service
import com.narea.mall.auth.JwtTokenProvider
import com.narea.mall.dto.LoginRequest
import com.narea.mall.dto.ReissueRequest
import com.narea.mall.dto.TokenResponse
import com.narea.mall.entity.RefreshToken
import com.narea.mall.entity.Role
import com.narea.mall.exception.ForbiddenException
import com.narea.mall.repository.RefreshTokenRepository
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional(readOnly = true)
class AuthService (
    private val jwtTokenProvider: JwtTokenProvider,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userService: UserService
) {
    fun hasAuthByUserId(userId: Long): Boolean {
        val authenticatedUser = getAuthenticatedUser()
        if(authenticatedUser.role == Role.ADMIN) return true
        if(userId != authenticatedUser.id) throw ForbiddenException("userId ${authenticatedUser.id} cannot access to userId${userId}'s resource")
        return true
    }
    fun hasAdminAuth(userId: Long): Boolean {
        val authenticatedUser = getAuthenticatedUser()
        if(authenticatedUser.role != Role.ADMIN) throw ForbiddenException("can access only admin user")
        return true
    }
    fun getAuthenticatedUser(): com.narea.mall.entity.User =
        userService.getUser(SecurityContextHolder.getContext().authentication.name)

    @Transactional
    fun login(loginRequest: LoginRequest): TokenResponse {
        val user = userService.getUser(loginRequest.email)
        val authenticationToken = UsernamePasswordAuthenticationToken(
            User(user.email, user.password, arrayListOf()),
            loginRequest.password
        )
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        val accessToken = jwtTokenProvider.generateAccessToken(authentication)
        val refreshToken = jwtTokenProvider.generateRefreshToken()
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
    @Transactional
    fun reissue(tokenRequest: ReissueRequest):TokenResponse {
        val authentication = jwtTokenProvider.getAuthenticationFromAccessToken(tokenRequest.accessToken)
        val prevRefreshToken = refreshTokenRepository.getRefreshTokenByEmail(authentication.name) ?: throw BadCredentialsException("존재하지 않거나 만료된 refreshToken 입니다.")
        if(tokenRequest.refreshToken != prevRefreshToken.token) throw BadCredentialsException("적합하지 않은 refreshToken 입니다.")
        val newAccessToken = jwtTokenProvider.generateAccessToken(authentication)
        val newRefreshToken = jwtTokenProvider.generateRefreshToken()
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