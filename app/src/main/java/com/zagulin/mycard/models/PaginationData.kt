package com.zagulin.mycard.models

data class PaginationData<T>(val dataList: List<T>?,val error: Throwable?, val pageNum: Int, val pageSize: Int)
