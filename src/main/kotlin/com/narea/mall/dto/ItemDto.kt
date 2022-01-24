package com.narea.mall.dto

import com.narea.mall.RequestMapper
import com.narea.mall.ResponseMapper
import com.narea.mall.entity.Category
import com.narea.mall.entity.Item
import com.narea.mall.entity.ItemFile
import com.narea.mall.utils.NOT_BE_EMPTY
import org.apache.commons.lang3.StringUtils
import org.mapstruct.*
import org.mapstruct.factory.Mappers
import javax.validation.constraints.NotBlank


// request
data class ItemCreateRequest (
    @field:NotBlank(message = NOT_BE_EMPTY)
    var name: String = StringUtils.EMPTY,
    var description:String = StringUtils.EMPTY,
    var price:Long = 0,
    var inventory:Long = 0,
    var categoryIds: List<Long> = emptyList()
)

data class ItemUpdateRequest (
    var name: String? = null,
    var description:String? = null,
    var price:Long? = null,
    var inventory:Long? = null,
    var categories:List<Long>? = null,
)

// response
data class ItemResponse (
    var id:Long = 0,
    var name:String = StringUtils.EMPTY,
    var description:String = StringUtils.EMPTY,
    var price:Long = 0,
    var inventory:Long = 0,
    var files:List<ItemFileResponse> = emptyList(),
    var categoryList:List<Category> = emptyList()
)

data class ItemFileResponse (
    var id: Long = 0,
    var name: String = StringUtils.EMPTY
)

@Mapper
interface ItemMapper: RequestMapper<ItemCreateRequest, Item>, ResponseMapper<ItemResponse, Item> {
    companion object {
        val INSTANCE: ItemMapper = Mappers.getMapper(ItemMapper::class.java)
    }
}
fun Item.toResponse() =
    ItemMapper.INSTANCE.fromEntity(this).also { res ->
        res.categoryList = this.itemCategories.map { itemCategory -> itemCategory.category }
    }

fun ItemCreateRequest.toEntity() = ItemMapper.INSTANCE.toEntity(this)

@Mapper
interface ItemFileMapper: ResponseMapper<ItemFileResponse, ItemFile> {
    companion object {
        val INSTANCE: ItemFileMapper = Mappers.getMapper(ItemFileMapper::class.java)
    }
}
fun ItemFile.toResponse() = ItemFileMapper.INSTANCE.fromEntity(this)
