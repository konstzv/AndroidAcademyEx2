package com.zagulin.mycard

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Category(val id: Int = 0, var name: String? = null): Parcelable