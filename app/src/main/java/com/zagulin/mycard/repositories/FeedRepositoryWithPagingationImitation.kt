package com.zagulin.mycard.repositories


abstract class FeedRepositoryWithPagingationImitation : FeedRepository {
    fun getPage(list: List<Any>, from: Int, shift: Int): List<Any> {
        val newFeedItems = mutableListOf<Any>()
        for (i in 0 until shift) {
            newFeedItems.add(list[(from + i) % list.size])
        }
        return newFeedItems
    }
}