package com.zagulin.mycard.models.converters

import com.zagulin.mycard.models.FeedItem
import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.models.NewsItemDb
import javax.inject.Inject


open class NewsItemDbToNewItemModelConverter @Inject constructor() :
        ModelConverter<NewsItemDb, FeedItem> {
    override fun convert(item: NewsItemDb): NewsItem {
        return NewsItem(
                id =item.id
                ,title = item.title
                , imageUrl = item.imageUrl
                , thumbnailUrl = item.thumbnailUrl
                , fullText = item.fullText
                , previewText = item.previewText
                , publishDate = item.publishDate
        )
    }

}