package com.narea.mall

import com.narea.mall.entity.User
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers

interface ResponseMapper<R, E> {
    fun fromEntity(entity: E): R
}

interface RequestMapper<R, E> {
    fun toEntity(request: R): E
}

@Mapper
interface UserMapper : RequestMapper<UserCreateRequest, User>, ResponseMapper<UserResponse, User> {
    companion object {
        val INSTANCE: UserMapper = Mappers.getMapper(UserMapper::class.java)
    }
}
fun User.toResponse() = UserMapper.INSTANCE.fromEntity(this)
fun UserCreateRequest.toEntity() = UserMapper.INSTANCE.toEntity(this)