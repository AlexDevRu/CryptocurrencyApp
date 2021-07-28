package com.example.domain.mappers

interface IMapper<TEntity, TModel> {
    fun toModel(entity: TEntity): TModel

    fun toModel(entities: List<TEntity>): List<TModel> {
        val items = mutableListOf<TModel>()
        for(entity in entities)
            items.add(toModel(entity))
        return items
    }

    fun fromModel(model: TModel): TEntity

    fun fromModel(models: List<TModel>): List<TEntity> {
        val items = mutableListOf<TEntity>()
        for(model in models)
            items.add(fromModel(model))
        return items
    }
}
