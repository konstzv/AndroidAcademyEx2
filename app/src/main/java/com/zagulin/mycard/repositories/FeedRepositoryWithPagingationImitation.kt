package com.zagulin.mycard.repositories

import com.zagulin.mycard.models.FeedItem


abstract class FeedRepositoryWithPagingationImitation : FeedRepository {
    fun getPage(list: List<FeedItem>, from: Int, shift: Int): List<FeedItem> {
        val newFeedItems = mutableListOf<FeedItem>()
        for (i in 0 until shift) {
            newFeedItems.add(list[(from + i) % list.size])
        }
        return newFeedItems
    }
}