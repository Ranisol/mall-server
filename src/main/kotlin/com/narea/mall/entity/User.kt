package com.narea.mall.entity
import com.narea.mall.utils.EMPTY_STRING
import org.apache.commons.lang3.StringUtils
import java.time.LocalDateTime
import javax.persistence.*



@Entity
@Table(name = "users")
class User:BaseTimeEntity() {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0
    @Column(nullable = false)
    var name:String = StringUtils.EMPTY
    @Column(nullable = false, unique = true)
    var email:String = StringUtils.EMPTY
    @Column(nullable = false)
    var mobileNumber:String = StringUtils.EMPTY
    @Column(nullable = false)
    var password: String = StringUtils.EMPTY
    @ManyToMany(fetch = FetchType.EAGER)
    var role: MutableList<Role> = emptyList<Role>().toMutableList()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true) // 라이프사이클 동일하게
    var userAddresses:List<UserAddress> = emptyList()

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL], orphanRemoval = true)
    var basket:Basket? = null
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