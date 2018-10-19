package com.zagulin.mycard

import java.io.Serializable


class Category(
        val id: Int = 0,
        var name: String? = null
) : Serializable {
    companion object {
        private val serialVersionUid: Long = 1
    }
}