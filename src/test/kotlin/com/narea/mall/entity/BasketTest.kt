package com.narea.mall.entity

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test


class BasketTest {
    @Test
    fun `test itemPrice`() {
        val basketItem1 = BasketItem(
            item = Item(price = 1000),
            count = 10
        )
        val basketItem2 = BasketItem(
            item = Item(price = 2000),
            count = 5
        )
        val basketItem3 = BasketItem(
            item = Item(price = 3000),
            count = 10
        )
        val basket = Basket(
            basketItems = arrayListOf(basketItem1, basketItem2, basketItem3)
        )
        Assertions.assertEquals(
            10_000, basketItem1.getBasketItemPrice(), "basketItem1"
        )
        Assertions.assertEquals(
            10_000, basketItem2.getBasketItemPrice(), "basketItem2"
        )
        Assertions.assertEquals(
            30_000, basketItem3.getBasketItemPrice(), "basketItem3"
        )
        Assertions.assertEquals(
            50_000, basket.getAllBasketItemPrice(), "basket"
        )
    }
}