package com.narea.mall.temp.bean

import com.narea.mall.repository.UserRepository
import com.narea.mall.service.UserService
import org.assertj.core.api.Assertions.assertThat
import org.springframework.boot.test.context.SpringBootTest
import org.junit.jupiter.api.Test
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

open class ParentServiceTest
@Service
class ChildrenServiceTest1: ParentServiceTest()
@Service
class ChildrenServiceTest2: ParentServiceTest()

@SpringBootTest
class BeanFindTest(
   private val context: ApplicationContext,
) {
    @Test
    fun `빈 타입으로 조회하기`() {
        val userService = context.getBean(UserService::class.java)
        assertThat(userService).isInstanceOf(UserService::class.java)
    }

    @Test
    fun `빈 이름으로 조회하기`() {
        val userService = context.getBean("userService");
        println(userService)
        assertThat(userService).isInstanceOf(UserService::class.java)
    }

    @Test
    fun `특정 타입을 모두 조회하기`() {
        val bean = context.getBeansOfType(UserRepository::class.java)
        println(bean)
    }

    @Test
    fun `부모 타입으로 조회시 자식들도 같이 조회된다`() {
        val bean = context.getBeansOfType(ParentServiceTest::class.java)
        assertThat(bean.size).isEqualTo(2)
    }

    @Test
    fun `부모 타입으로 상속관계 조회시 자식이 둘 이상이라면 빈 이름을 지정한다`() {
        val bean = context.getBean("childrenServiceTest1")
        println(bean)
        assertThat(bean).isInstanceOf(ChildrenServiceTest1::class.java)
    }

    @Test
    fun `부모 타입으로 모두 조회하기`() {
        val bean = context.getBeansOfType(Object::class.java)
        bean.keys.forEach { println("key : [$it] ----- value : [${bean[it]}]") }
    }

    @Test
    fun `빈 설정 메타정보 확인`() {
        val beanDefinitionNames = context.beanDefinitionNames
        beanDefinitionNames.forEach { println("beanDefinitionName = $it") }
    }
}




