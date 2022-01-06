package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

@Entity
@Table
class ShippingAddress:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    var id:Long = 0
    var isDefault:Boolean = false
    var address:String = EMPTY_STRING
    @ManyToOne()
    var user:User = User()
}