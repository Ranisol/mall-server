package com.narea.mall.dto

import com.narea.mall.ResponseMapper
import com.narea.mall.entity.Delivery
import com.narea.mall.entity.DeliveryStatus
import com.narea.mall.utils.EMPTY_STRING
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers

data class DeliveryUpdateRequest(
    var waybill: String? = null,
    var status: DeliveryStatus? = null,
    var address: String? = null,
    var courierName: String? = null,
)

data class DeliveryResponse(
    var id: Long = 0,
    var waybill: String = EMPTY_STRING,
    var status: DeliveryStatus = DeliveryStatus.PREPARED,
    var address: String = EMPTY_STRING,
    var courierName: String = EMPTY_STRING
)

@Mapper
interface DeliveryMapper: ResponseMapper<DeliveryResponse, Delivery> {
    companion object {
        val INSTANCE: DeliveryMapper = Mappers.getMapper(DeliveryMapper::class.java)
    }
}

fun Delivery.toResponse() = DeliveryMapper.INSTANCE.fromEntity(this)
