package com.narea.mall.auth

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors


@Component
class JwtTokenProvider(@Value("\${jwt.secret}") secretKey: String) {

    companion object {

        private const val ROLES = "roles"
        private const val ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 30 // 30분
        private const val REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7 // 7일
    }

    private val key: Key = Keys.hmacShaKeyFor(
        Decoders.BASE64.decode(secretKey)
    )
    private val signatureAlgorithm = SignatureAlgorithm.HS256

    fun generateAccessToken(authentication: Authentication):String {
        val accessTokenExpiresIn = Date(Date().time + ACCESS_TOKEN_EXPIRE_TIME)
        return  Jwts.builder()
            .setSubject(authentication.name) // payload "sub": "name"
            .claim(ROLES, authentication.authorities.joinToString(",") { it.authority }) // payload "roles": "ROLE_USER"
            .setExpiration(accessTokenExpiresIn) // payload "exp": 1516239022
            .signWith(key, signatureAlgorithm) // header "alg": "HS256"
            .compact()
    }
    fun generateRefreshToken(authentication: Authentication):String {
        val refreshTokenExpiresIn = Date(Date().time + REFRESH_TOKEN_EXPIRE_TIME)
        return Jwts.builder()
            .setExpiration(Date(Date().time + REFRESH_TOKEN_EXPIRE_TIME))
            .signWith(key, signatureAlgorithm)
            .compact()
    }
    fun getAuthenticationFromAccessToken(accessToken: String): Authentication {
        // 토큰 복호화
        val claims = parseClaims(accessToken)
        println(claims)
        val authorities: Collection<GrantedAuthority?> =
            Arrays.stream(claims[ROLES].toString().split(",").toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())
        val principal: UserDetails = User(claims.subject, "", authorities)
        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    fun validateToken(token: String): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: SecurityException) {
           println("잘못된 JWT 서명입니다.")
        } catch (e: MalformedJwtException) {
           println("잘못된 JWT 서명입니다.")
        } catch (e: ExpiredJwtException) {
           println("만료된 JWT 토큰입니다.")
        } catch (e: UnsupportedJwtException) {
           println("지원되지 않는 JWT 토큰입니다.")
        } catch (e: IllegalArgumentException) {
           println("JWT 토큰이 잘못되었습니다.")
        }
        return false
    }

    private fun parseClaims(accessToken: String): Claims {
        return try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).body
        } catch (e: ExpiredJwtException) {
            e.claims
        }
    }
}