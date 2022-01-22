package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

@Entity
@Table
class Category(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    var name:String = EMPTY_STRING
):BaseTimeEntity()