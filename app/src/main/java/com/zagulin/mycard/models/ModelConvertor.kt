package com.zagulin.mycard.models


interface ModelConvertor<From, To> {
    fun convert(item: From): To
}