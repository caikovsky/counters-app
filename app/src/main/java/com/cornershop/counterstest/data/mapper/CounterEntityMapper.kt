package com.cornershop.counterstest.data.mapper

import com.cornershop.counterstest.data.local.CounterEntity
import com.cornershop.counterstest.domain.model.Counter
import javax.inject.Inject

class CounterEntityMapper @Inject constructor() : EntityMapper<CounterEntity, Counter> {
    override fun mapFromEntity(entity: CounterEntity): Counter {
        return Counter(entity.id, entity.title, entity.count)
    }

    override fun mapToEntity(domainModel: Counter): CounterEntity {
        return CounterEntity(
            id = domainModel.id,
            title = domainModel.title,
            count = domainModel.count
        )
    }

    fun mapFromEntityList(entities: List<CounterEntity>): List<Counter> {
        return entities.map { mapFromEntity(it) }
    }

    fun mapToEntityList(entities: List<Counter>): List<CounterEntity> {
        return entities.map { mapToEntity(it) }
    }
}


