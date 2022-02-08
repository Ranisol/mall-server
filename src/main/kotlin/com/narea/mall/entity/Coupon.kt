package com.narea.mall.entity

import com.narea.mall.utils.EMPTY_STRING
import java.time.LocalDateTime
import javax.persistence.*

/** 쿠폰 보류, 추후 필요시 구현 */
//enum class CouponType(label: String) {
//    SPECIFIC("특정 아이템"), OVERALL("모든 아이템")
//}
//
//enum class DiscountType(label: String) {
//    FIXED_RATE("정률"), FLAT_RATE("정액")
//}
//
//@Entity
//@Table
//class Coupon (
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    var id: Long = 0,
//    var description: String = EMPTY_STRING,
//    var startDate: LocalDateTime = LocalDateTime.now(),
//    var endDate: LocalDateTime = LocalDateTime.now(),
//    @OneToMany(mappedBy = "coupon", cascade = [CascadeType.ALL], orphanRemoval = true)
//    var targetItems: List<CouponTargetItem> = emptyList(), // 쿠폰 적용 대상
//    var couponType: CouponType = CouponType.SPECIFIC,
//    var minOrderAmount: Number? = null,
//    var maxDiscountAmount: Number? = null,
//    var discountValue: Int = 0,
//    var discountType: DiscountType = DiscountType.FIXED_RATE
//)
//
//
//@Entity
//@Table
//class CouponTargetItem (
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    var id: Long = 0,
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    var coupon: Coupon = Coupon(),
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    var item: Item = Item(),
//)
//
//// 유저에 발행된 쿠폰
//@Entity
//@Table
//class CouponIssue (
//    @Id @GeneratedValue(strategy = GenerationType.AUTO)
//    var id: Long = 0,
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    var user: User = User(),
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    var coupon: Coupon = Coupon(),
//    var used: Boolean = false,
//)
