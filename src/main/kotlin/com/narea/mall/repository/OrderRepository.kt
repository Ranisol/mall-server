package com.narea.mall.repository

import com.narea.mall.entity.OrderHistory
import com.narea.mall.entity.Orders
import com.narea.mall.entity.OrdersItem
import com.narea.mall.repository.custom.OrderRepositoryCustom
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository: JpaRepository<Orders, Long>, OrderRepositoryCustom {
    fun findAllByUserId(userId: Long, pageable: Pageable): Page<Orders>
}

@Repository
interface OrderItemRepository: JpaRepository<OrdersItem, Long>

@Repository
interface OrderHistoryRepository: JpaRepository<OrderHistory, Long>