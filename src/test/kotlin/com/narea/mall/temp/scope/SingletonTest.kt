package com.narea.mall.temp.scope

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


class SingletonTest {
    @Test
    fun singletonBeanFind(){
       val ac = AnnotationConfigApplicationContext(SingletonBean::class.java)
        Assertions.assertThat(ac.getBean(SingletonBean::class.java)).isSameAs(ac.getBean(SingletonBean::class.java))
        ac.close()
    }
    companion object {
        @Scope("singleton")
        class SingletonBean {
            @PostConstruct
            fun init() = println("SingletonBean.init")
            @PreDestroy
            fun destroy() = println("SingletonBean.destroy")
        }
    }
}