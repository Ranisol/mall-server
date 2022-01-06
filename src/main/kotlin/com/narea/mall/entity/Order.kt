package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*


@Entity
@Table
class Orders {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Column(nullable = false)
    var status:String = EMPTY_STRING
    var amount:Long = 0
    @ManyToOne()
    var user:User = User()
    @OneToMany(mappedBy = "orders")
    var orderItems:List<OrdersItem> = emptyList()
}