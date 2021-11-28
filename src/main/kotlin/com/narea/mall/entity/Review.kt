package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import javax.persistence.*

@Entity
@Table
class Review:BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Column(nullable = false)
    var title:String = StringUtils.EMPTY
    @Column(nullable = false)
    var content:String = StringUtils.EMPTY
    @Column(nullable = false)
    var like:Long = 0
    @ManyToOne(optional = false)
    var item:Item = Item()
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "review")
    var files:MutableList<ReviewFile> = mutableListOf()
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
    var review:Review = Review()
}

