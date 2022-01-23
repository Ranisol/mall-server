package com.narea.mall.dto

import com.narea.mall.RequestMapper
import com.narea.mall.ResponseMapper
import com.narea.mall.entity.*
import com.narea.mall.utils.EMPTY_STRING
import org.mapstruct.Mapper
import org.mapstruct.factory.Mappers
import javax.validation.constraints.NotBlank


data class InquiryCreateRequest (
    @field:NotBlank
    var title: String = EMPTY_STRING,
    @field:NotBlank
    var content: String = EMPTY_STRING,
)

data class InquiryUpdateRequest (
    var title: String? = null,
    var content: String? = null
)

data class InquiryResponse (
    var id: Long = 0,
    var title: String = EMPTY_STRING,
    var content: String = EMPTY_STRING,
    var onlyAdminCanSee: Boolean = false,
    var files:List<InquiryFileResponse> = emptyList(),
    var replies: List<InquiryReplyResponse> = emptyList(),
    var user: User = User()
)

data class UserInquiryResponse (
    var id: Long = 0,
    var title: String = EMPTY_STRING,
    var content: String = EMPTY_STRING,
    var onlyAdminCanSee: Boolean = false,
    var files:List<InquiryFileResponse> = emptyList(),
    var replies: List<InquiryReplyResponse> = emptyList(),
    var item: Item = Item()
)

data class InquiryFileResponse (
    var id: Long = 0,
    var name: String = EMPTY_STRING,
)

data class InquiryReplyResponse (
    var id: Long = 0,
    var content: String = EMPTY_STRING
)

@Mapper
interface InquiryMapper: ResponseMapper<InquiryResponse, Inquiry>, RequestMapper<InquiryCreateRequest, Inquiry> {
    companion object {
        val INSTANCE: InquiryMapper = Mappers.getMapper(InquiryMapper::class.java)
    }
}
@Mapper
interface UserInquiryMapper: ResponseMapper<UserInquiryResponse, Inquiry> {
    companion object {
        val INSTANCE: UserInquiryMapper = Mappers.getMapper(UserInquiryMapper::class.java)
    }
}

fun InquiryCreateRequest.toEntity() = InquiryMapper.INSTANCE.toEntity(this)
fun Inquiry.toResponse() = InquiryMapper.INSTANCE.fromEntity(this)
fun Inquiry.toUserResponse() = UserInquiryMapper.INSTANCE.fromEntity(this)

@Mapper
interface InquiryFileMapper: ResponseMapper<InquiryFileResponse, InquiryFile> {
    companion object {
        val INSTANCE: InquiryFileMapper = Mappers.getMapper(InquiryFileMapper::class.java)
    }
}
fun InquiryFile.toResponse() = InquiryFileMapper.INSTANCE.fromEntity(this)

@Mapper
interface InquiryReplyMapper: ResponseMapper<InquiryReplyResponse, InquiryReply> {
    companion object {
        val INSTANCE: InquiryReplyMapper = Mappers.getMapper(InquiryReplyMapper::class.java)
    }
}
fun InquiryReply.toResponse() = InquiryReplyMapper.INSTANCE.fromEntity(this)
