package com.intellisrc.mobiledeveloperchallenge.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "rates_table")
data class RatesEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int? = null,
    var currency: String? = null,
    var rate: Double? = null,
    var currencyType: String? = null
)