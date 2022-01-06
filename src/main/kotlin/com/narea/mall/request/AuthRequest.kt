package com.narea.mall.request

import com.narea.mall.utils.NOT_BE_EMPTY
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.lang.annotation.ElementType
import java.lang.annotation.RetentionPolicy
import javax.servlet.http.HttpServletRequest
import javax.validation.constraints.NotBlank
import kotlin.reflect.KClass


data class LoginRequest(
    @field:NotBlank(message = NOT_BE_EMPTY)
    var email: String,
    @field:NotBlank(message = NOT_BE_EMPTY)
    var password:String
)
data class ReissueRequest(
    @field:NotBlank(message = NOT_BE_EMPTY)
    var accessToken: String,
    @field:NotBlank(message = NOT_BE_EMPTY)
    var refreshToken:String,

)
