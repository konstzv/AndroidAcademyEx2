package com.zagulin.mycard.models

import java.io.Serializable


class Category(
        val id: Int = 0,
        var name: String? = null
) : Serializable {
    companion object {
        private const val serialVersionUID: Long = 1
    }
}