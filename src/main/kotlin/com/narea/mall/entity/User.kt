package com.narea.mall.entity
import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.commons.lang3.StringUtils
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table
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
}
