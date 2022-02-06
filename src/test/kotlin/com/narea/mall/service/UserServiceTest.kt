package com.narea.mall.service


import com.narea.mall.dto.UserCreateRequest
import com.narea.mall.entity.User
import com.narea.mall.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class UserServiceTest(
    @Autowired val userService: UserService,
    @Autowired val userRepository: UserRepository
) {
    @Test
    fun test() {
        userRepository.save(User())
    }

    @Test
    fun `유저 생성이 작동하는지 확인`() {
        // when
        val userRequest = UserCreateRequest(
            name = "test",
            email = "aamm10@naver.com"
        )
        // then
        userService.createUser(userRequest)
        val user = userService.getUser("aamm10@naver.com")
        println("check user is exist: $user")
        assertEquals(userRequest.email, user.email)
    }

}