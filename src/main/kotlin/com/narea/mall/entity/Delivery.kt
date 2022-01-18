package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

enum class DeliveryStatus(label:String) {
    PREPARED("PREPARED"),
    SHIPPING("SHIPPING"),
    COMPLETED("COMPLETED")
}

@Entity
@Table
class Delivery {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Enumerated(EnumType.STRING)
    var status:DeliveryStatus = DeliveryStatus.PREPARED
    var address:String = EMPTY_STRING

    @ManyToOne(fetch = FetchType.LAZY, optional = false) // 한번의 주문에 여러개의 배송 있을 수 있음
    @JoinColumn(nullable = false)
    var order:Orders = Orders()
}