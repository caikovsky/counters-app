package com.cornershop.counterstest.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "list_counters")
data class Counter(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = "",
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "count")
    val count: Int = 0
)