package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

enum class OrderStatus(label: String) {
    ORDER("ORDER"),
    CANCEL("CANCEL"),
    REFUND("REFUND")
}

@Entity
@Table
class Orders {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0

    @Column(nullable = false)
    var status:String = EMPTY_STRING

    @Enumerated(EnumType.STRING)
    var orderStatus:OrderStatus = OrderStatus.CANCEL

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var user:User = User()

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = false)
    var deliveries:List<Delivery> = emptyList()

    @OneToMany(mappedBy = "orders", orphanRemoval = true, cascade = [CascadeType.ALL])
    var orderItems:List<OrdersItem> = emptyList()


    // 주문 가격
    fun getTotalPrice() = orderItems.sumOf { it.getTotalPrice() }

    // 주문 상태 변화
    fun order() {
        orderItems.map { orderItem -> orderItem.order() }
        orderStatus = OrderStatus.ORDER
    }
    fun cancel() {
        orderItems.map { orderItem -> orderItem.cancel() }
        orderStatus = OrderStatus.CANCEL
    }
    fun refund() {
        orderStatus = OrderStatus.REFUND
    }
}