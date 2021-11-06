package com.spring.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@Configuration
class SecurityConfig: WebSecurityConfigurerAdapter() {
    override fun configure(http: HttpSecurity) {
        /**
          가장 기본적인 설정, 모든 요청에 대해 인증을 폼 형식으로 요구함
          http.authorizeRequests().anyRequest().authenticated.and().formLogin().and.httpBasic()
          */

        /**
         * 요청마다 다르게 설정함
         * - private에 대해서는 폼 형식을 통해 인증을 요구하고,
         * - public에 대해서는 모든 요청을 허용하는식.
         * - 참고로 모든 루트를 포함한 모든 서브 요청에 대해서 적용하려면, 와일드카드 (**)를 사용해야함.
        http.authorizeRequests()
        .antMatchers("/private").authenticated()
        .antMatchers("/public").permitAll()
        .and().formLogin().and().httpBasic()
         */

        /**
         * 모든 요청에 대해 거부하려면
         * http.authorizeRequests().anyRequest().denyAll().and().formLogin().and().httpBasic()
         * */


        http.authorizeRequests()
            .antMatchers("/account/**").authenticated()
            .antMatchers("/test").permitAll()
            .and().formLogin().and().httpBasic()
    }
}