package com.narea.mall.response

import com.narea.mall.entity.Role
import com.narea.mall.utils.EMPTY_STRING
import java.time.LocalDateTime

data class RoleResponse (
    var id:Long = 0,
    var name:String = EMPTY_STRING
)

data class UserResponse (
    var id:Long = 0,
    var name:String = EMPTY_STRING,
    var email:String = EMPTY_STRING,
    var mobileNumber:String? = EMPTY_STRING,
    var role:MutableList<Role> = emptyList<Role>().toMutableList(),
    var createDate: LocalDateTime? = null
)

