package com.narea.mall.response

import com.narea.mall.entity.ItemFile
import org.apache.commons.lang3.StringUtils

data class ItemResponse (
    var id:Long = 0,
    var name:String = StringUtils.EMPTY,
    var description:String = StringUtils.EMPTY,
    var price:Long = 0,
    var inventory:Long = 0,
    var files:List<ItemFileResponse> = emptyList()
)

data class ItemFileResponse (
    var id: Long = 0,
    var name: String = StringUtils.EMPTY
)