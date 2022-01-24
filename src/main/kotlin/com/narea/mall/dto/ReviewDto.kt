package com.narea.mall.dto

import com.narea.mall.RequestMapper
import com.narea.mall.ResponseMapper
import com.narea.mall.entity.ReviewFile
import com.narea.mall.entity.Reviews
import com.narea.mall.utils.EMPTY_STRING
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.factory.Mappers
import javax.validation.constraints.NotBlank

// request
data class ReviewCreateRequest (
    @field:NotBlank
    var title:String = EMPTY_STRING,
    @field:NotBlank
    var content:String = EMPTY_STRING,
    var heart:Int = 0,
)

data class ReviewUpdateRequest (
    var title:String? = null,
    var content:String? = null,
    var hearts: Int? = null
)

// response
data class ReviewResponse (
    var id: Long = 0,
    var title: String = EMPTY_STRING,
    var hearts: Int = 0,
    var hidden: Boolean = false,
    var files: List<ReviewFileResponse> = emptyList(),
    var username: String = EMPTY_STRING
)

data class UserReviewResponse (
    var id: Long = 0,
    var title: String = EMPTY_STRING,
    var hearts: Int = 0,
    var hidden: Boolean = false,
    var files: List<ReviewFileResponse> = emptyList(),
    var item: ItemResponse,
)

data class ReviewFileResponse (
    var id: Long = 0,
    var name: String = EMPTY_STRING
)


@Mapper
interface ReviewsMapper: RequestMapper<ReviewCreateRequest, Reviews> {
    companion object {
        val INSTANCE: ReviewsMapper = Mappers.getMapper(ReviewsMapper::class.java)
    }
    @Mappings(
        Mapping(source = "user.name", target = "username")
    )
    fun fromEntity(entity: Reviews): ReviewResponse
}
@Mapper
interface UserReviewMapper: ResponseMapper<UserReviewResponse, Reviews> {
    companion object {
        val INSTANCE: UserReviewMapper = Mappers.getMapper(UserReviewMapper::class.java)
    }
}

fun ReviewCreateRequest.toEntity() = ReviewsMapper.INSTANCE.toEntity(this)
fun Reviews.toResponse() = ReviewsMapper.INSTANCE.fromEntity(this)
fun Reviews.toUserResponse() =
    UserReviewMapper.INSTANCE.fromEntity(this).also { res ->
        res.item.categoryList =
            this.item.itemCategories.map {
                itemCategory ->  itemCategory.category // TODO: Mapstruct 에서 item categories -> categoryList 로 바꿔주는거 찾아보기
            }
    }



@Mapper
interface ReviewFileMapper: ResponseMapper<ReviewFileResponse, ReviewFile> {
    companion object {
        val INSTANCE: ReviewFileMapper = Mappers.getMapper(ReviewFileMapper::class.java)
    }
}
fun ReviewFile.toResponse() = ReviewFileMapper.INSTANCE.fromEntity(this)
