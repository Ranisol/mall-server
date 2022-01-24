package com.narea.mall.auth

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse



class JwtFilter(
    private val jwtTokenProvider: JwtTokenProvider
)  : OncePerRequestFilter() {

    companion object {
        const val BEARER_PREFIX = "Bearer "
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val jwt = request.getHeader(AUTHORIZATION_HEADER)?.substring(7)
        if(jwt != null && jwtTokenProvider.validateToken(jwt)){
            val authentication = jwtTokenProvider.getAuthenticationFromAccessToken(jwt)
            SecurityContextHolder.getContext().authentication = authentication
        }
        filterChain.doFilter(request, response)
    }


}