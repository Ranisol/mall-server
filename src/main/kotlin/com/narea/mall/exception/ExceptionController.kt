package com.narea.mall.exception

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.security.SecurityException
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MaxUploadSizeExceededException
import javax.validation.ConstraintViolationException

@RestController
@ControllerAdvice
class ExceptionController {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable::class)
    fun handleThrowable(e: Throwable): MallErrorResponse {
        e.printStackTrace()
        return MallErrorResponse.create(e)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MaxUploadSizeExceededException::class)
    fun handleMethodArgumentNotValidException(e: MaxUploadSizeExceededException): MallErrorResponse =
        MallErrorResponse(e.javaClass.name, e.message ?: "Maximum upload size exceeded")

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(e: BadRequestException): MallErrorResponse = MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): MallErrorResponse =
        MallErrorResponse.create(e)

    // entity 생성시, entity내의 unique 한 field가 겹쳤을 때, 발생하는 예외
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(e: DataIntegrityViolationException): MallErrorResponse =
        MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(NotImplementedError::class)
    fun handleNotImplementedError(e: NotImplementedError): MallErrorResponse =
        MallErrorResponse(e.javaClass.name, "Not Allow Method")

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(e: NotFoundException): MallErrorResponse = MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistException::class)
    fun handleAlreadyExistException(e: AlreadyExistException): MallErrorResponse = MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ConflictException::class)
    fun handleConflictException(e: ConflictException): MallErrorResponse = MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(e: ForbiddenException): MallErrorResponse = MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): MallErrorResponse = MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): MallErrorResponse =
        MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(e: ConstraintViolationException): MallErrorResponse =
        MallErrorResponse.create(e)

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(BadCredentialsException::class)
    fun handleAccessBadCredentialsException(e: BadCredentialsException): MallErrorResponse =
        MallErrorResponse.create(e)


    // 인증 예외
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(SecurityException::class)
//    fun handleJwtSecurityException(e: SecurityException) =
//        MallErrorResponse.create(e.javaClass.name, "잘못된 JWT 서명입니다.")

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(MalformedJwtException::class)
    fun handleMalformedJwtException(e: MalformedJwtException) =
        MallErrorResponse.create(e.javaClass.name, "잘못된 JWT 서명입니다.")

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ExpiredJwtException::class)
    fun handleMalformedJwtException(e: ExceptionHandler) =
        MallErrorResponse.create(e.javaClass.name, "만료된 JWT 서명입니다.")

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(UnsupportedJwtException::class)
    fun handleMalformedJwtException(e: UnsupportedJwtException) =
        MallErrorResponse.create(e.javaClass.name, "지원되지 않는 서명입니다.")


    data class MallErrorResponse(
        val exception: String,
        val message: String?
    ) {
        companion object {
            fun create(throwable: Throwable): MallErrorResponse {
                return MallErrorResponse(
                    exception = throwable.javaClass.name,
                    message = throwable.message
                )
            }
            fun create(exception:String, message: String):MallErrorResponse {
                return MallErrorResponse(
                    exception = exception,
                    message = message
                )
            }
        }
    }
}