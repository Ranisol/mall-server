package com.narea.mall.service
import com.narea.mall.*
import com.narea.mall.entity.Role
import com.narea.mall.entity.User
import com.narea.mall.exception.NotFoundException
import com.narea.mall.repository.RoleRepository
import com.narea.mall.repository.UserRepository
import com.narea.mall.response.UserResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
):UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email) ?: throw NotFoundException("user not found")
        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            user.role.map {
                SimpleGrantedAuthority(it.name)
            }
        )
    }

    fun getUser(email: String) = userRepository.findByEmail(email) ?: throw NotFoundException("user not exist:$email")
    fun getUser(userId: Long) =
        userRepository.findByIdOrNull(userId) ?: throw NotFoundException("user not found $userId")

    fun getUsers(): List<User> = userRepository.findAll()
    fun createUser(userCreateRequest: UserCreateRequest): UserResponse {
        return userCreateRequest.toEntity()
            .apply { password = passwordEncoder.encode(userCreateRequest.password) }
            .let { user -> userRepository.save(user)
            }.toResponse()
    }

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

    fun addRoleToUser(userId: Long, roleId: Long): UserResponse {
        val user = userRepository.findByIdOrNull(userId) ?: throw NotFoundException("user not exist: $userId")
        val role = roleRepository.findByIdOrNull(roleId) ?: throw NotFoundException("role not found: $roleId")
        return user.apply { this.role.add(role) }.let { user -> userRepository.save(user) }.toResponse()
    }

    fun getRole(roleId: Long) = roleRepository.findByIdOrNull(roleId) ?: throw NotFoundException("role not found")
    fun getRoles() = roleRepository.findAll()
    fun createRole(roleName: String): Role = Role()
        .apply { name = roleName }
        .let { role -> roleRepository.save(role) }

    fun updateRole(roleId: Long, roleName: String) {
        var role = roleRepository.findByIdOrNull(roleId) ?: throw NotFoundException("not found")
        return role.apply {
            name = roleName
        }.let { role -> roleRepository.save(role) }
    }

}