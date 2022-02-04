package com.narea.mall.service
import com.narea.mall.dto.*
import com.narea.mall.entity.Basket
import com.narea.mall.entity.User
import com.narea.mall.exception.NotFoundException
import com.narea.mall.exception.UserEmailExistException
import com.narea.mall.repository.UserRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
):UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw NotFoundException("user not found")
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            arrayListOf(SimpleGrantedAuthority(user.role))
        )
    }

    fun getUser(email: String) = userRepository.findByEmail(email) ?: throw NotFoundException("user not exist:$email")
    fun getUser(userId: Long) =
        userRepository.findByIdOrNull(userId) ?: throw NotFoundException("user not found $userId")
    fun getUserResponse(userId: Long) = getUser(userId).toResponse()
    fun getUsersResponse(pageable: Pageable) = userRepository.findAll(pageable).map { it.toResponse() }
    @Transactional
    fun createUser(userCreateRequest: UserCreateRequest): UserResponse {
        val user = userRepository.findByEmail(userCreateRequest.email)
        if(user != null) throw UserEmailExistException("user email:${userCreateRequest.email} already exist")
        return userCreateRequest.toEntity()
            .apply {
                password = passwordEncoder.encode(userCreateRequest.password)
                basket = Basket(
                    user = this
                )
            }
            .let { userRepository.save(it) }.toResponse()
    }
    @Transactional
    fun createUsers(userCreateRequests: List<UserCreateRequest>): List<UserResponse>{
        return userRepository.saveAll(
            userCreateRequests.map { request -> request.toEntity() }
        ).map { user -> user.toResponse() }

    }

    @Transactional
    fun updateUser(userId: Long, userUpdateRequest: UserUpdateRequest): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException("user not exist")
        return user.apply {
            name = userUpdateRequest.name ?: name
            mobileNumber = userUpdateRequest.mobileNumber ?: mobileNumber
            password =
                userUpdateRequest.password ?: password
                if (userUpdateRequest.password != null) passwordEncoder.encode(password) else password
        }.toResponse()
    }


}