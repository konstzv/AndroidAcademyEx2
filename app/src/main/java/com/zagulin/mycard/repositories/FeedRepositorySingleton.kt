package com.zagulin.mycard.repositories

class FeedRepositorySingleton private constructor() {
    companion object {
        val instance: FeedRepository by lazy { NYTTopStoriesFeedRepository() }
    }
}