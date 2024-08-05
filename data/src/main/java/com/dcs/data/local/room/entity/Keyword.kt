package com.dcs.data.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "keywords")
data class Keyword(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,

    @ColumnInfo("keyword")
    val keyword: String,
)
