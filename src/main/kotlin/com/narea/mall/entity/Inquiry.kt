package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import javax.persistence.*

@Entity
@Table
class Inquiry:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    @Column(nullable = false)
    var title:String = StringUtils.EMPTY
    @Column(nullable = false)
    val content:String = StringUtils.EMPTY
    @OneToMany( fetch = FetchType.LAZY, mappedBy = "inquiry")
    var files:List<InquiryFile> = emptyList()
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    val item:Item = Item()
    @ManyToOne
    val user:User = User()


}

@Entity
@Table
class InquiryFile:BaseTimeEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long = 0
    @Column(nullable = false)
    var name:String = EMPTY_STRING;
    @ManyToOne(optional = false)
    var inquiry:Inquiry = Inquiry()
}