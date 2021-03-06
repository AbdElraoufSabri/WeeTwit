package tech.abd3lraouf.learn.clean.weetwit.data.model

import com.google.gson.annotations.SerializedName

data class ResponseModel(
    @SerializedName("search_metadata")
    val metadataModel: MetadataModel,
    @SerializedName("statuses")
    var statusList: List<StatusModel>
)