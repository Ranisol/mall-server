package com.narea.mall.entity

import javax.persistence.*

@Entity
@Table
class DeliveryItem (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    var count: Int = 0,
    @ManyToOne(fetch = FetchType.LAZY)
    var item: Item = Item(),
    @ManyToOne(fetch = FetchType.LAZY)
    var delivery: Delivery = Delivery()
): BaseTimeEntity()