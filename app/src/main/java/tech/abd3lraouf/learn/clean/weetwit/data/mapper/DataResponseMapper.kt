package tech.abd3lraouf.learn.clean.weetwit.data.mapper

import tech.abd3lraouf.learn.clean.weetwit.data.model.MetadataModel
import tech.abd3lraouf.learn.clean.weetwit.data.model.ResponseModel
import tech.abd3lraouf.learn.clean.weetwit.data.model.StatusModel
import tech.abd3lraouf.learn.clean.weetwit.data.model.UserModel
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.MetadataEntity
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.ResponseEntity
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.StatusEntity
import tech.abd3lraouf.learn.clean.weetwit.domain.entity.UserEntity
import javax.inject.Inject

class DataResponseMapper @Inject constructor() : Mapper<ResponseEntity, ResponseModel> {
    override fun mapToDomain(data: ResponseModel): ResponseEntity {
        with(data) {
            return ResponseEntity(MetadataEntity(metadataModel.nextResults), statusList.map {
                val user = with(it.user) { UserEntity(name, profileImageUrl, screenName) }
                StatusEntity(it.createdAt, it.favoriteCount, it.retweetCount, it.text, user)
            })
        }
    }

    override fun mapToData(domain: ResponseEntity): ResponseModel {
        with(domain) {
            return ResponseModel(MetadataModel(metadataEntity.nextResults), statusList.map {
                val user = with(it.user) { UserModel(name, profileImageUrl, screenName) }
                StatusModel(it.createdAt, it.favoriteCount, it.retweetCount, it.text, user)
            })
        }
    }
}