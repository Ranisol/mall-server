package com.narea.mall.repository

import com.narea.mall.entity.ReviewFile
import com.narea.mall.entity.Reviews
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository: JpaRepository<Reviews, Long> {
    fun findAllByItemId(item_id: Long, pageable: Pageable):Page<Reviews>
    fun findAllByUserId(user_id: Long, pageable: Pageable):Page<Reviews>
}

@Repository
interface ReviewFileRepository: JpaRepository<ReviewFile, Long>

