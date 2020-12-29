package tech.abd3lraouf.learn.clean.weetwit.data.model

import com.google.gson.annotations.SerializedName

data class StatusModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("favorite_count")
    val favoriteCount: Int,
    @SerializedName("retweet_count")
    val retweetCount: Int,
    val text: String,
    val user: UserModel
)