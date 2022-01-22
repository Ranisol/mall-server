package com.narea.mall.request

import com.narea.mall.utils.NOT_BE_EMPTY
import org.apache.commons.lang3.StringUtils
import javax.validation.constraints.NotBlank

data class ItemCreateRequest (
    @field:NotBlank(message = NOT_BE_EMPTY)
    var name: String = StringUtils.EMPTY,
    var description:String = StringUtils.EMPTY,
    var price:Long = 0,
    var inventory:Long = 0
)

data class ItemUpdateRequest (
    var name: String? = null,
    var description:String? = null,
    var price:Long? = null,
    var inventory:Long? = null
)