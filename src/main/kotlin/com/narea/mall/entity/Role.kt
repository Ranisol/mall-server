package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

@Entity
@Table
class Role {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    var name:String = EMPTY_STRING
}


@Table
@Entity
class RefreshToken {
    @Id
    var email:String = EMPTY_STRING
    var token:String = EMPTY_STRING
}