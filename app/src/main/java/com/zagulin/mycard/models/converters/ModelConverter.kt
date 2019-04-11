package com.zagulin.mycard.models.converters


interface ModelConverter<From, To> {
    fun convert(item: From): To
}