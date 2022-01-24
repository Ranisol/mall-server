package com.narea.mall.dto

import com.narea.mall.entity.Basket
import com.narea.mall.entity.BasketItem


data class BasketItemAddRequest (
        var count:Int,
        var itemId: Long
)


data class BasketResponse (
        var id: Long = 0,
        var priceAll: Long = 0,
        var basketItemList: List<BasketItemResponse>
)

data class BasketItemResponse (
        var id: Long = 0,
        var count: Int = 0,
        var price: Long = 0,
        var item: ItemResponse = ItemResponse()
)

fun Basket.toResponse():BasketResponse = this.let { basket ->
        BasketResponse(
                id = basket.id,
                priceAll = basket.getAllBasketItemPrice(),
                basketItemList = basket.basketItems.map { basketItem ->
                        basketItem.toResponse()
                }
        )
}

fun BasketItem.toResponse(): BasketItemResponse = this.let { basketItem ->
        BasketItemResponse(
                id = basketItem.id,
                count = basketItem.count,
                price = basketItem.getBasketItemPrice(),
                item = basketItem.item.toResponse()
        )
}