package tech.abd3lraouf.learn.clean.weetwit.domain.entity

data class StatusEntity(
    val createdAt: String,
    val favoriteCount: Int,
    val retweetCount: Int,
    val text: String,
    val user: UserEntity
)