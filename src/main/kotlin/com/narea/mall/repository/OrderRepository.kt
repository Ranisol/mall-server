package com.narea.mall.repository

import com.narea.mall.entity.Orders
import com.narea.mall.entity.OrdersItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Orders, Long> {
}

@Repository
interface OrderItemRepository: JpaRepository<OrdersItem, Long>
