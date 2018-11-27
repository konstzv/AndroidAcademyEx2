package com.zagulin.mycard.models.converters

import com.zagulin.mycard.models.NewsItem
import com.zagulin.mycard.models.NewsItemDb
import javax.inject.Inject


open class NewsItemToNewItemDbModelConverter @Inject constructor() :
        ModelConverter<NewsItem, NewsItemDb> {
    override fun convert(item: NewsItem): NewsItemDb {
        return NewsItemDb(
                id = item.id
                ,title = item.title
                , imageUrl = item.imageUrl
                , thumbnailUrl = item.thumbnailUrl
                , fullTextUrl = item.fullTextUrl
                , previewText = item.previewText
                , publishDate = item.publishDate
                , categoryId = item.category?.id
        )
    }


}