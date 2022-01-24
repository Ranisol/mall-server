package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import javax.persistence.*

@Entity
@Table
class Inquiry:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id:Long = 0
    var title:String = StringUtils.EMPTY
    var content:String = StringUtils.EMPTY
    var onlyAdminCanSee:Boolean = false
    @OneToMany(mappedBy = "inquiry", orphanRemoval = true, cascade = [CascadeType.ALL]) // OneToMany에는 default로 FetchType이 Lazy로 되어있음, 고아객체제거와 cascade all로 파일과 라이플사이클 동일하게 맞춰줌
    var files:List<InquiryFile> = emptyList()
    @OneToMany(mappedBy = "inquiry", orphanRemoval = true, cascade = [CascadeType.ALL])
    var replies: List<InquiryReply> = emptyList()
    @ManyToOne(optional = false, fetch = FetchType.LAZY) // optional: nullable false와 같은 역할
    @JoinColumn(nullable = false)
    var item:Item = Item()
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    var user:User = User()
}

@Entity
@Table
class InquiryFile (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id:Long = 0,
    @Column(nullable = false)
    var name:String = EMPTY_STRING,
    @ManyToOne(optional = false)
    var inquiry:Inquiry = Inquiry()
):BaseTimeEntity()

@Entity
@Table
class InquiryReply (
    @Id @GeneratedValue
    var id: Long = 0,
    var content:String = EMPTY_STRING,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    var inquiry:Inquiry = Inquiry(),
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user:User = User()
)
