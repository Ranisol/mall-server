package com.narea.mall.service


import com.narea.mall.dto.UserCreateRequest
import com.narea.mall.entity.User
import com.narea.mall.repository.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.test.assertEquals

//@SpringBootTest
class UserServiceTest {

//    @Test
//    fun test() {
//        userRepository.save(User())
//    }
//    @Test
//    fun `유저 생성이 작동하는지 확인`() {
//        // when
//        val userRequest = UserCreateRequest(
//            name = "test",
//            email = "aamm10@naver.com"
//        )
//        // then
//        userService.createUser(userRequest)
//        val user = userService.getUser("aamm10@naver.com")
//        println("check user is exist: $user")
//        assertEquals(userRequest.email, user.email)
//    }

    @Test
    fun testTest() {
        val expiredDate:LocalDate = LocalDate.of(2022, 3, 25)
        println(expiredDate)
        val activatedDate: LocalDateTime = LocalDateTime.of(
            LocalDate.of(2022, 3, 23),
            LocalTime.MIN
        )
        println(activatedDate)
        var between = Duration.between(expiredDate, activatedDate)
        println(between)
    }

}