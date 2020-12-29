package tech.abd3lraouf.learn.clean.weetwit.data.model

import com.google.gson.annotations.SerializedName

data class MetadataModel(
    @SerializedName("next_results")
    val nextResults: String?,
)