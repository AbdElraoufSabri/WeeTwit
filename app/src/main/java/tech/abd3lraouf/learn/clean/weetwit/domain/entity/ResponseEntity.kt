package tech.abd3lraouf.learn.clean.weetwit.domain.entity

data class ResponseEntity(
    val metadataEntity: MetadataEntity,
    var statusList: List<StatusEntity>
)