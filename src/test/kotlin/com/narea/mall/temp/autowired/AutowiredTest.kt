package com.narea.mall.temp.autowired

import com.narea.mall.entity.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.lang.Nullable
import org.springframework.stereotype.Component
import java.util.*

@SpringBootTest
class AutowiredTest{
    @Test
    fun authWiredOption() = AnnotationConfigApplicationContext(TestBean::class.java)
}

class TestBean {
    @Autowired(required = false)
    fun setNoBean1(user: User) = println("noBean1 $user")
    @Autowired
    fun setNoBean2(@Nullable user:User?) = println("noBean2 $user")
    @Autowired
    fun setNoBean3(user: Optional<User>) = println("noBean3 $user")
}

