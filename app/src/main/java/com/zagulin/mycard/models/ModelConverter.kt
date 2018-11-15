package com.zagulin.mycard.models


interface ModelConverter<From, To> {
    fun convert(item: From): To
}