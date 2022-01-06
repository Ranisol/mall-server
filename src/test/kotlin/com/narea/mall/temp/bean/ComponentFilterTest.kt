package com.narea.mall.temp.bean

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.FilterType

//@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
//@Retention(AnnotationRetention.RUNTIME)
//@MustBeDocumented
//annotation class MyIncludeComponent
//
//@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS)
//@Retention(AnnotationRetention.RUNTIME)
//@MustBeDocumented
//annotation class MyExcludeComponent
//
//@MyIncludeComponent
//class IncludeBean
//
//@MyExcludeComponent
//class ExcludeBean
//
//@Configuration
//@ComponentScan(
//    includeFilters = [
//        ComponentScan.Filter(
//            type = FilterType.ANNOTATION,
//            classes = [MyIncludeComponent::class]
//        )
//    ],
//    excludeFilters = [
//        ComponentScan.Filter(
//            type = FilterType.ANNOTATION,
//            classes = [MyExcludeComponent::class]
//        )
//    ],
//    basePackages = ["com.narea.mall.temp.bean"]
//)
//class ComponentFilterAppConfig
//
//
//class ComponentFilterTest {
//    @Test
//    fun `includeFilter가 작동하는지 확인합니다`(){
//        val ac = AnnotationConfigApplicationContext(ComponentFilterAppConfig::class.java)
//        val beanA = ac.getBean("includeBean", IncludeBean::class)
//        assertThat(beanA).isNotNull
//    }
//
//    @Test
//    fun `excludeFilter가 작동하는지 확인합니다`(){
//        val ac = AnnotationConfigApplicationContext(ComponentFilterAppConfig::class.java)
//        val beanB = ac.getBean("excludeBean", ExcludeBean::class)
//
//    }
//}
//
