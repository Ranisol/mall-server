package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

enum class DeliveryStatus(label:String) {
    PREPARED("PREPARED"), // 배송 준비중
    SHIPPING("SHIPPING"),   // 배송중
    COMPLETED("COMPLETED")  // 배송 완료
}

// https://github.com/shlee322/delivery-tracker 참고해 배송 추적 api 필요시 구현 (보류)

@Entity
@Table
class Delivery(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    var waybill: String = EMPTY_STRING, // 운송장 번호, 보류 후 필요시 구현
    @Enumerated(EnumType.STRING)
    var status:DeliveryStatus = DeliveryStatus.PREPARED,
    var address:String = EMPTY_STRING,
    var courierName: String = EMPTY_STRING,
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    var order:Orders = Orders()
):BaseTimeEntity()


