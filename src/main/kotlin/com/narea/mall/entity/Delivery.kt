package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

enum class DeliveryStatus(label:String) {
    PREPARED("PREPARED"), // 배송 준비중
    SHIPPING("SHIPPING"),   // 배송중
    COMPLETED("COMPLETED")  // 배송 완료
}

// https://github.com/shlee322/delivery-tracker 참고해 배송 추적 api 구현 계획

@Entity
@Table
class Delivery(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    var waybill: String = EMPTY_STRING, // 운송장 번호
    @Enumerated(EnumType.STRING)
    var status:DeliveryStatus = DeliveryStatus.PREPARED,
    var address:String = EMPTY_STRING,
    var courierName: String = EMPTY_STRING,
    @ManyToOne(fetch = FetchType.LAZY, optional = false) // 한번의 주문에 여러개의 배송 있을 수 있음
    @JoinColumn(nullable = false)
    var order:Orders = Orders(),
    @OneToMany(mappedBy = "delivery")
    var deliveryItems: List<DeliveryItem> = emptyList()
):BaseTimeEntity()
