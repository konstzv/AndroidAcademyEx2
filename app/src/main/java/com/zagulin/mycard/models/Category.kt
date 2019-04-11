package com.zagulin.mycard.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
class Category(
        @PrimaryKey
        val id: Int,
        var name: String
)  {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        return id == other.id


    }
    @Suppress("detekt.MagicNumber")
    override fun hashCode(): Int {
        var result = 31 *id.hashCode()
        result = 31 * result + name.hashCode()
        return result
    }
}

