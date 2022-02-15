package com.narea.mall.dto

import com.narea.mall.entity.DeliveryStatus
import com.narea.mall.entity.OrderStatus
import com.narea.mall.entity.Orders
import com.narea.mall.entity.OrdersItem
import java.time.LocalDate

data class OrderCreateRequest(
    var count: Int,
    var itemId: Long
)

data class OrderResponse(
    var id: Long = 0,
    var orderStatus: OrderStatus = OrderStatus.ORDER,
    var orderItems: List<OrderItemResponse> = emptyList(),
    var totalPrice: Int = 0,
    var delivery: DeliveryResponse? = null
)

data class OrderItemResponse(
    var id: Long = 0,
    var count: Int = 0,
    var itemsPrice: Int = 0,
    var item: ItemResponse = ItemResponse()
)

data class OrderParams (
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val deliveryStatus: DeliveryStatus? = null,
    val orderStatus: OrderStatus? = null
)

fun Orders.toResponse(): OrderResponse =
    this.let { orders ->
        OrderResponse(
            id = orders.id,
            orderStatus = orders.orderStatus,
            totalPrice = orders.getTotalPrice(),
            orderItems = orders.orderItems.map { it.toResponse() },
            delivery = orders.delivery?.toResponse()
        )
    }

fun OrdersItem.toResponse(): OrderItemResponse =
    this.let { ordersItem ->
        OrderItemResponse(
            id = ordersItem.id,
            count = ordersItem.count,
            itemsPrice = ordersItem.getTotalPrice(),
            item = ordersItem.item.toResponse()
        )
    }