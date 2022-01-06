package com.narea.mall.entity

import javax.persistence.*

@Entity
@Table
class OrdersItem {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    var count:Long = 0
    @ManyToOne()
    var item:Item = Item()
    @ManyToOne()
    var orders:Orders = Orders()
}

