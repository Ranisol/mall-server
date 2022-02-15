package com.narea.mall.dto

import com.narea.mall.RequestMapper
import com.narea.mall.ResponseMapper
import com.narea.mall.entity.Role
import com.narea.mall.entity.User
import com.narea.mall.utils.EMPTY_STRING
import com.narea.mall.utils.NOT_BE_EMPTY
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.factory.Mappers
import java.time.LocalDateTime
import javax.validation.constraints.NotBlank

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
    var name:String? = null,
    var mobileNumber: String? = null,
    var password:String? = null,
)


data class UserResponse (
    var id:Long = 0,
    var name:String = EMPTY_STRING,
    var email:String = EMPTY_STRING,
    var mobileNumber:String? = EMPTY_STRING,
    var role:String = Role.USER.toString(),
    var createDate: LocalDateTime? = null,
    var basket: BasketResponse = BasketResponse()
)


@Mapper
interface UserMapper : RequestMapper<UserCreateRequest, User>, ResponseMapper<UserResponse, User> {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }
}
fun User.toResponse() = UserMapper.INSTANCE.fromEntity(this)
fun UserCreateRequest.toEntity() = UserMapper.INSTANCE.toEntity(this)
