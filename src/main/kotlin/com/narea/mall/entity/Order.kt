package com.narea.mall.entity

import com.narea.mall.exception.BadRequestException
import com.narea.mall.exception.ItemInventoryLackException
import java.time.LocalDateTime
import javax.persistence.*

enum class OrderStatus(label: String) {
    READY("READY"),
    ORDER("ORDER"),
    CANCEL("CANCEL"),
    REFUND("REFUND")
}

@Entity
@Table
class Orders (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,

    @Enumerated(EnumType.STRING)
    var orderStatus:OrderStatus = OrderStatus.READY,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user:User = User(),

    @OneToOne(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = false)
    var delivery:Delivery? = null,

    @OneToMany(mappedBy = "orders", orphanRemoval = true, cascade = [CascadeType.ALL])
    var orderItems:List<OrdersItem> = emptyList(),

    /** 쿠폰 로직 보류 */
    //    @OneToMany
    //    var appliedCoupon: List<CouponIssue> = emptyList()
): BaseTimeEntity() {
    // 주문 가격 (할인 있으면 추가)
    fun getTotalPrice() = orderItems.sumOf { it.getTotalPrice() }

    // 주문 상태 변화
    fun order() {
        if(orderStatus != OrderStatus.READY) throw BadRequestException("주문 상태가 READY인 경우에만 가능합니다.")
        orderItems.map { orderItem -> orderItem.order() }
        orderStatus = OrderStatus.ORDER
        delivery = Delivery(order = this)
    }
    fun cancel() {
        if(orderStatus != OrderStatus.ORDER) throw BadRequestException("주문 상태가 ORDER인 경우에만 가능합니다.")
        orderItems.map { orderItem -> orderItem.cancel() }
        orderStatus = OrderStatus.CANCEL
    }
    fun refund() {
        if(orderStatus != OrderStatus.CANCEL) throw BadRequestException("주문 상태가 CANCEL인 경우에만 가능합니다.")
        orderStatus = OrderStatus.REFUND
    }
}

@Entity
@Table
class OrdersItem (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    var count:Int = 0,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var item:Item = Item(),
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
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

@Entity
@Table
class OrderHistory (
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    @ManyToOne
    var orders: Orders = Orders(),
    var orderStatus: OrderStatus = OrderStatus.ORDER,
    var modifiedDate: LocalDateTime = LocalDateTime.now()
)