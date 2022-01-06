package com.narea.mall.temp.scope

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

class PrototypeTest {
    @Test
    fun prototypeBeanFind(){
        val ac = AnnotationConfigApplicationContext(PrototypeBean::class.java)
        Assertions.assertThat(ac.getBean(PrototypeBean::class.java)).isNotSameAs(ac.getBean(PrototypeBean::class.java))
        // destroy가 안불려짐
        ac.close()
    }
    companion object {
        @Scope("prototype")
        class PrototypeBean {
            @PostConstruct
            fun init() = println("SingletonBean.init")
            @PreDestroy
            fun destroy() = println("SingletonBean.destroy")
        }
    }
}