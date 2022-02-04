package com.narea.mall.repository

import com.narea.mall.entity.Basket
import com.narea.mall.entity.BasketItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BasketRepository: JpaRepository<Basket, Long> {
    fun findByUserId(userId: Long): Basket?
}

@Repository
interface BasketItemRepository: JpaRepository<BasketItem, Long>