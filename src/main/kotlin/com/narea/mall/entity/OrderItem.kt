package com.narea.mall.entity

import com.narea.mall.exception.BadRequestException
import javax.persistence.*

@Entity
@Table
class OrdersItem {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    var count:Int = 0
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var item:Item = Item()
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var orders:Orders = Orders()

    fun getTotalPrice() = count * item.price
    fun order () {
        item.inventory = item.inventory - count // 팬텀 읽기 문제?
        if(item.inventory < 0) throw BadRequestException("${item.name}: item inventory not enough")
        // exception이 발생했을 때, 이미 차감된 애들에 대해서는 롤백해야하는지?
    }
    fun cancel () {
        item.inventory = item.inventory + count
    }
}

