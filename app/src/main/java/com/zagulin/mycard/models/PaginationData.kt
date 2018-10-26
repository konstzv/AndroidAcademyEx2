package com.zagulin.mycard.models

data class PaginationData<T>(val dataList: List<T>, val pageNum: Int, val pageSize: Int)
