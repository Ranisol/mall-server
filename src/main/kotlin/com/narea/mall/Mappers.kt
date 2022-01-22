package com.narea.mall

import com.narea.mall.entity.Item
import com.narea.mall.entity.ItemFile
import com.narea.mall.entity.User
import com.narea.mall.request.ItemCreateRequest
import com.narea.mall.response.ItemFileResponse
import com.narea.mall.response.ItemResponse
import com.narea.mall.response.UserResponse
import org.mapstruct.Mapper
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

@Mapper
interface ItemMapper: RequestMapper<ItemCreateRequest, Item>, ResponseMapper<ItemResponse, Item> {
    companion object {
        val INSTANCE: ItemMapper = Mappers.getMapper(ItemMapper::class.java)
    }
}
fun Item.toResponse() = ItemMapper.INSTANCE.fromEntity(this)
fun ItemCreateRequest.toEntity() = ItemMapper.INSTANCE.toEntity(this)

@Mapper
interface ItemFileMapper: ResponseMapper<ItemFileResponse, ItemFile> {
    companion object {
        val INSTANCE: ItemFileMapper = Mappers.getMapper(ItemFileMapper::class.java)
    }
}
fun ItemFile.toResponse() = ItemFileMapper.INSTANCE.fromEntity(this)
