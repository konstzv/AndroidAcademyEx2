package com.zagulin.mycard.common

import com.zagulin.mycard.models.NewsItem


interface OnNewsItemClickListener {
    fun onItemClick(item: NewsItem)
}