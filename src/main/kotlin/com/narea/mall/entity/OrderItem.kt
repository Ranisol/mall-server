package com.narea.mall.entity

import com.narea.mall.exception.BadRequestException
import com.narea.mall.exception.ItemInventoryLackException
import javax.persistence.*

@Entity
@Table
class OrdersItem (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    var count:Int = 0,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var item:Item = Item(),
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var orders:Orders = Orders()
):BaseTimeEntity() {
    fun getTotalPrice() = count * item.price
    fun order () {
        item.inventory -= count
        if(item.inventory < 0) throw ItemInventoryLackException("${item.name}: item inventory not enough")
    }
    fun cancel () {
        item.inventory += count
    }
}

