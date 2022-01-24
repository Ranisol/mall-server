package com.narea.mall.exception

class NotFoundException(message: String) : RuntimeException(message)
class AlreadyExistException(message: String) : RuntimeException(message)
class ForbiddenException(message: String) : RuntimeException(message)
class BadRequestException(message: String) : RuntimeException(message)
class ConflictException(message: String) : RuntimeException(message)

// user
class UserEmailExistException(message: String) : RuntimeException(message)

// item
class ItemInventoryLackException(message: String) : RuntimeException(message)
