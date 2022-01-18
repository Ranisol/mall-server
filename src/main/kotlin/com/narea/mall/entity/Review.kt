package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

@Entity
@Table
class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int = 0
    var title:String = EMPTY_STRING
    var content:String = EMPTY_STRING
    var hearts:Long = 0
    var hidden:Boolean = false
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "review", orphanRemoval = true, cascade = [CascadeType.ALL])
    var files:List<ReviewFile> = emptyList()
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var item:Item = Item()
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var user:User = User()
}

@Entity
@Table
class ReviewFile:BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Column(nullable = false)
    var name:String = EMPTY_STRING;
    @ManyToOne(optional = false)
    var review:Reviews = Reviews()
}

