package com.narea.mall.repository

import com.narea.mall.entity.Delivery
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeliveryRepository: JpaRepository<Delivery, Long>