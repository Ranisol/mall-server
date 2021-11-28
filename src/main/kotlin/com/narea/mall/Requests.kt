package com.narea.mall

import com.narea.mall.utils.EMPTY_STRING
import com.narea.mall.utils.NOT_BE_EMPTY
import javax.validation.constraints.NotBlank


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
    var refreshToken:String
)

data class UserCreateRequest(
    @field:NotBlank(message = NOT_BE_EMPTY)
    var name: String = EMPTY_STRING,
    @field:NotBlank(message = NOT_BE_EMPTY)
    var email: String = EMPTY_STRING,
    var mobileNumber: String = EMPTY_STRING,
    @field:NotBlank(message = NOT_BE_EMPTY)
    var password:String = EMPTY_STRING,
)

data class UserUpdateRequest(
    val name:String? = null,
    val mobileNumber: String? = null,
    val password:String? = null,
)

