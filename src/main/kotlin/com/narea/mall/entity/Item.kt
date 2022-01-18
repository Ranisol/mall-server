package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import javax.persistence.*

@Entity
@Table
class Item:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Column(nullable = false)
    var name:String = StringUtils.EMPTY
    var timeToDelivery: Int? = null
    @Column(nullable = false)
    var description:String = StringUtils.EMPTY
    var price:Long = 0
    var inventory:Long = 0
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = [CascadeType.ALL])
    var files:List<ItemFile> = emptyList()
}

@Entity
@Table
class ItemFile:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Column(nullable = false)
    var name:String = EMPTY_STRING
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var item:Item = Item()
}

