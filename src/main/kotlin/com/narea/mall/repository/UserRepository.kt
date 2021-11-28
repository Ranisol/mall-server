package com.narea.mall.repository

import com.narea.mall.entity.Role
import com.narea.mall.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository:JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}

@Repository
interface RoleRepository: JpaRepository<Role, Long> {
    fun findByName(name:String): Role?
}
