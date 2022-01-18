package com.narea.mall.entity

import javax.persistence.*


@Entity
@Table
class Basket:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Column(nullable = false)
    var count:Long = 0
    @OneToOne(fetch = FetchType.LAZY)
    var user:User = User()
    @OneToMany(mappedBy = "basket", cascade = [CascadeType.ALL], orphanRemoval = true)
    var basketItems: List<BasketItem> = emptyList()
}