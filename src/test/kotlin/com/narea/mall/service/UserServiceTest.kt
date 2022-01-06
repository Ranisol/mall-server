package com.narea.mall.service

import com.narea.mall.UserCreateRequest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class UserServiceTest(
    @Autowired val userService: UserService
) {

    @Test
    fun `유저 생성이 작동하는지 확인`() {
        val userRequest = UserCreateRequest(
            name = "test",
            email = "aamm10@naver.com"
        )
        userService.createUser(userRequest)
        val user = userService.getUser("aamm10@naver.com")
        println("check user is exist: $user")
        assertEquals(userRequest.email, user.email)
    }
}