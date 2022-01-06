package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import javax.persistence.*

@Entity
@Table
class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Int = 0
    @Column(nullable = false)
    var title:String = EMPTY_STRING
    @Column(nullable = false)
    var content:String = EMPTY_STRING
    @Column(nullable = false)
    var hearts:Long = 0
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "review")
    var files:List<ReviewFile> = emptyList()
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    var item:Item = Item()
    @ManyToOne
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

