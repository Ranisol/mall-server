package com.narea.mall
import com.narea.mall.entity.Role
import com.narea.mall.utils.EMPTY_STRING
import java.time.LocalDateTime

data class UserResponse (
    var id:Long = 0,
    var name:String = EMPTY_STRING,
    var email:String = EMPTY_STRING,
    var mobileNumber:String? = EMPTY_STRING,
    var role:MutableList<Role> = emptyList<Role>().toMutableList(),
    var createDate: LocalDateTime? = null
)

data class RoleResponse (
    var id:Long = 0,
    var name:String = EMPTY_STRING
        )

data class TokenResponse (
    var accessToken:String = EMPTY_STRING,
    var refreshToken:String = EMPTY_STRING,
)