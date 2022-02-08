package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*



@Table
@Entity
class RefreshToken:BaseTimeEntity() {
    @Id
    var email:String = EMPTY_STRING
    var token:String = EMPTY_STRING
}