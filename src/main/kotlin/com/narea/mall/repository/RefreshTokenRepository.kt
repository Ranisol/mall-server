package com.narea.mall.repository

import com.narea.mall.entity.RefreshToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository:JpaRepository<RefreshToken, String> {
    fun getRefreshTokenByEmail(email:String):RefreshToken?
}