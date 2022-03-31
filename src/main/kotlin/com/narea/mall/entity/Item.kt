package com.narea.mall.entity


import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import javax.persistence.*

@Entity
@Table
class Item(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0,
    @Column(nullable = false)
    var name:String = StringUtils.EMPTY,
    @Column(nullable = false)
    var description:String = StringUtils.EMPTY,
    var price: Int = 0,
    var inventory: Int = 0,
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = [CascadeType.ALL])
    var files:List<ItemFile> = emptyList(),
    @OneToMany(mappedBy = "item", orphanRemoval = true, cascade = [CascadeType.ALL]) // itemCategory 는 전적으로 카테고리에서는 참조하지 않고, 아이템에서만 참조됨
    var itemCategories: List<ItemCategory> = emptyList(),
    @Version
    var version: Long = 0
):BaseTimeEntity()

@Entity
@Table
class ItemFile(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0,
    @Column(nullable = false, length = 512)
    var name:String = EMPTY_STRING,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var item:Item = Item()
):BaseTimeEntity()

