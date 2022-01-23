package com.narea.mall.entity

import javax.persistence.*

@Entity
@Table
class ItemCategory(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var item: Item = Item(),
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var category:Category = Category()
)