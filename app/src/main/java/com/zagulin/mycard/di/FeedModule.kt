package com.zagulin.mycard.di

import com.zagulin.mycard.common.pagination.FeedPaginator
import com.zagulin.mycard.common.pagination.Paginator
import com.zagulin.mycard.models.converters.NewsItemNetworkToNewItemModelConverter
import com.zagulin.mycard.repositories.FeedRepository
import com.zagulin.mycard.repositories.LocalFeedRepository
import com.zagulin.mycard.repositories.NYTTopStoriesFeedRepository
import com.zagulin.mycard.repositories.NYTTopStoriesFeedRepositoryBackedByDb
import toothpick.config.Module


object FeedModule : Module() {
    init {
        bind(FeedRepository::class.java)
                .to(NYTTopStoriesFeedRepositoryBackedByDb::class.java).singletonInScope()
        bind(Paginator::class.java)
                .to(FeedPaginator::class.java).instancesInScope()

    }
}