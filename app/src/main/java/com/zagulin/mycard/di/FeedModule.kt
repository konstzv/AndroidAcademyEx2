package com.zagulin.mycard.di

import com.zagulin.mycard.common.pagination.FeedPaginator
import com.zagulin.mycard.common.pagination.Paginator
import com.zagulin.mycard.repositories.FeedRepository
import com.zagulin.mycard.repositories.IMainNavigationInteractor
import com.zagulin.mycard.repositories.MainNavigationInteractor
import com.zagulin.mycard.repositories.NYTTopStoriesFeedRepositoryBackedByDb
import toothpick.config.Module


object FeedModule : Module() {
    init {
        bind(FeedRepository::class.java)
                .to(NYTTopStoriesFeedRepositoryBackedByDb::class.java).singletonInScope()
        bind(IMainNavigationInteractor::class.java)
                .to(MainNavigationInteractor::class.java)
        bind(Paginator::class.java)
                .to(FeedPaginator::class.java).instancesInScope()

    }
}