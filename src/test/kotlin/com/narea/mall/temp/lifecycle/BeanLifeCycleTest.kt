package com.narea.mall.temp.lifecycle

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


//class BeanLifeCycleTest {
//    @Test
//    fun lifeCycleTest(){
//        val ac = AnnotationConfigApplicationContext(ComponentFilterAppConfig::class.java)
//        val client = ac.getBean(NetworkClient::class.java)
//        client.connect()
//        ac.close()
//    }
//    companion object {
//        class NetworkClient {
//            @PostConstruct
//            fun afterPropertiesSet() {
//                println("initializing!")
//                this.url = "test"
//            }
//            @PreDestroy
//            fun destroy() {
//                println("destroy!")
//            }
//            var url: String? = null
//            init {
//                println("init $url")
//            }
//            // 서비스 시작시 호출
//            fun connect() = println("connect: $url")
//            fun call(message:String) = println("call: $url, message: $message")
//            fun disconnect() = println("close: $url")
//        }
//
//        @Configuration
//        class ComponentFilterAppConfig {
//            @Bean
//            fun networkClient():NetworkClient = NetworkClient().apply { url = "test" }
//        }
//    }
//}

