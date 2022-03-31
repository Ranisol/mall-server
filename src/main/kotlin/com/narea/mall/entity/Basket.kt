package com.narea.mall.entity

import javax.persistence.*


@Entity
@Table
class Basket (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    @OneToOne(fetch = FetchType.LAZY)
    var user:User = User(),
    @OneToMany(mappedBy = "basket", cascade = [CascadeType.ALL], orphanRemoval = true)
    var basketItems: MutableList<BasketItem> = arrayListOf()
):BaseTimeEntity() {
    fun getAllBasketItemPrice(): Int =
        basketItems.sumOf { basketItem -> basketItem.getBasketItemPrice() }
}

