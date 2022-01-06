package com.narea.mall.temp.scope

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.ObjectProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Scope
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

class SingletonWithPrototypeTest {
    @Test
    fun prototypeFind(){
        val ac = AnnotationConfigApplicationContext(PrototypeBean::class.java)
        val prototypeBean1 = ac.getBean(PrototypeBean::class.java)
            .apply { addCount() }
            .apply { addCount() }
        Assertions.assertThat(prototypeBean1.count).isEqualTo(2)

    }

    @Test
    fun singletonClientUsePrototypeBean(){
        val ac = AnnotationConfigApplicationContext(PrototypeBean::class.java, ClientBean::class.java)
        val count1 = ac.getBean(ClientBean::class.java).let { it.logic() }
        Assertions.assertThat(count1).isEqualTo(1)
        val count2 = ac.getBean(ClientBean::class.java).let { it.logic() }
        Assertions.assertThat(count2).isEqualTo(1)
    }
    companion object {
        class ClientBean(
            @Autowired private val prototypeBeanProvider:ObjectProvider<PrototypeBean>
        ) {

            fun logic():Int {
                val prototypeBean = prototypeBeanProvider.`object`
                prototypeBean.addCount()
                return prototypeBean.count
            }
        }

        @Scope("prototype")
        class PrototypeBean {
            var count = 0;
            fun addCount(){
                count ++
            }
            @PostConstruct
            fun init() = println("PrototypeBean.init: $count")
            @PreDestroy
            fun destroy() = println("destroy!")
        }
    }
}