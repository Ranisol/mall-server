package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import javax.persistence.*

@Entity
@Table
class Inquiry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Int = 0
    @Column(nullable = false)
    val title:String = StringUtils.EMPTY
    @Column(nullable = false)
    val content:String = StringUtils.EMPTY
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "inquiry")
    var files:MutableList<InquiryFile> = mutableListOf()
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    val item:Item = Item()

}

@Entity
@Table
class InquiryFile {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Int = 0
    @Column(nullable = false)
    var name:String = EMPTY_STRING;
    @ManyToOne(optional = false)
    var inquiry:Inquiry = Inquiry()
}