package com.narea.mall.service

import com.narea.mall.repository.DeliveryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class DeliveryService(
    private val deliveryRepository: DeliveryRepository
) {

}