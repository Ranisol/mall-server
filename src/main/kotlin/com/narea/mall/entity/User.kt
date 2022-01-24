package com.narea.mall.entity
import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import java.time.LocalDateTime
import javax.persistence.*


enum class Role (role:String) {
    USER("ROLE_USER"),

}

@Entity
@Table(name = "users")
class User:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    @Column(nullable = false)
    var name:String = EMPTY_STRING
    @Column(nullable = false, unique = true)
    var email:String = EMPTY_STRING
    @Column(nullable = false)
    var mobileNumber:String = EMPTY_STRING
    @Column(nullable = false)
    var password: String = EMPTY_STRING
    var role: String = "ROLE_USER"

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true) // 라이프사이클 동일하게
    var userAddresses:List<UserAddress> = emptyList()

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var basket:Basket? = null // 엔티티간 상호 참조 문제, 유저 생성시마다 넣어주는걸로 변경
}

@Entity
@Table
class UserAddress:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    var id:Long = 0
    var isDefault:Boolean = false
    var address:String = EMPTY_STRING
    var expectedArrivalDate: LocalDateTime? = null
    @ManyToOne(fetch = FetchType.LAZY)
    var user:User = User()
}