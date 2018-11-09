package com.zagulin.mycard.di

import com.zagulin.mycard.common.pagination.FeedPaginator
import com.zagulin.mycard.common.pagination.Paginator
import com.zagulin.mycard.models.ModelConvertor
import com.zagulin.mycard.models.NewItemModelConverter
import com.zagulin.mycard.repositories.FeedRepository
import com.zagulin.mycard.repositories.NYTTopStoriesFeedRepository
import toothpick.config.Module


object FeedModule : Module() {
    init {
        bind(FeedRepository::class.java)
                .to(NYTTopStoriesFeedRepository::class.java).singletonInScope()
        bind(Paginator::class.java)
                .to(FeedPaginator::class.java).instancesInScope()
        bind(ModelConvertor::class.java)
                .to(NewItemModelConverter::class.java).instancesInScope()
    }
}