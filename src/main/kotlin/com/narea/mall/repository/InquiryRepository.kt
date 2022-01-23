package com.narea.mall.repository

import com.narea.mall.entity.Inquiry
import com.narea.mall.entity.InquiryFile
import com.narea.mall.entity.InquiryReply
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InquiryRepository: JpaRepository<Inquiry, Long> {
    fun findAllByUserId(userId:Long, pageable: Pageable):Page<Inquiry>
    fun findAllByItemId(ItemId:Long, pageable: Pageable):Page<Inquiry>
}

@Repository
interface InquiryFileRepository: JpaRepository<InquiryFile, Long>

@Repository
interface InquiryReplyRepository: JpaRepository<InquiryReply, Long>