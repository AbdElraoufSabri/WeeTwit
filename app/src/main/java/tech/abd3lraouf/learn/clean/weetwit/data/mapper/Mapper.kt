package tech.abd3lraouf.learn.clean.weetwit.data.mapper

interface Mapper<DOMAIN, DATA> {
    fun mapToDomain(data: DATA): DOMAIN
    fun mapToData(domain: DOMAIN): DATA
}