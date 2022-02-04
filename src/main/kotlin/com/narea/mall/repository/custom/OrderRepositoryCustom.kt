package com.narea.mall.repository.custom

import com.narea.mall.dto.OrderResponse
import com.narea.mall.entity.*
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class OrderParams (
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val deliveryStatus: DeliveryStatus? = null,
    val orderStatus: OrderStatus? = null
)

@Repository
interface OrderRepositoryCustom {
    fun findOrdersByParamsAndUserId(userId: Long, params: OrderParams, pageable: Pageable): Page<Orders>
   // fun findOrderResponsesByParams(params: OrderParams, pageable: Pageable): Page<OrderResponse>
}

class OrderRepositoryImpl (
    private val jpaQueryFactory: JPAQueryFactory
): QuerydslRepositorySupport(Orders::class.java), OrderRepositoryCustom {

    val orders: QOrders = QOrders.orders
    val user: QUser = QUser.user
    val delivery: QDelivery = QDelivery.delivery

    override fun findOrdersByParamsAndUserId(userId: Long, params: OrderParams, pageable: Pageable): Page<Orders> { //
        pageable.sort
        val query = jpaQueryFactory.from(orders)
            .where(
                orders.user.id.eq(userId),
                eqOrderStatus(params.orderStatus),
                betweenDate(params.startDate, params.endDate),
                eqDeliveryStatus(params.deliveryStatus)
            )

        val totalEl = query.fetchCount()
        val results: List<Orders> = querydsl!!.applyPagination(pageable, query).fetch() as List<Orders>
        return PageImpl(results, pageable, totalEl)
    }

    private fun eqOrderStatus(orderStatus: OrderStatus?): BooleanExpression? {
        if(orderStatus == null) return null
        return orders.orderStatus.eq(orderStatus)
    }

    private fun betweenDate(
        startDate: LocalDate?,
        endDate: LocalDate?
    ): BooleanExpression? {
        if(startDate == null) return null
        if(endDate == null) return null
        val start = LocalDateTime.of(startDate, LocalTime.MIN) // 해당 날짜의 맨 처음
        val end = LocalDateTime.of(endDate, LocalTime.MAX)  // 해당 날짜의 맨 마지막
        return orders.createdDate.between(start, end)
    }

    private fun eqDeliveryStatus(deliveryStatus: DeliveryStatus?): BooleanExpression? {
        if(deliveryStatus == null) return null
        return orders.delivery.status.eq(deliveryStatus)
    }

}