package com.narea.mall.entity

import javax.persistence.*

@Entity
@Table
class BasketItem {
    @Id @GeneratedValue
    var id:Long = 0
    var count:Int = 0
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var item:Item = Item()
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var basket:Basket = Basket()

    fun getBasketItemPrice() = count * item.price

}