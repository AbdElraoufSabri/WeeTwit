package tech.abd3lraouf.learn.clean.weetwit.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val name: String,
    @SerializedName("profile_image_url_https")
    val profileImageUrl: String,
    @SerializedName("screen_name")
    val screenName: String
)