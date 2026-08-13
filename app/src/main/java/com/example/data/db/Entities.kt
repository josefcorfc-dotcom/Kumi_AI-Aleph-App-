package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "system_logs")
data class SystemLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "neuro_mantras")
data class NeuroMantraEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val mantra: String,
    val persona: String,
    val memoShadow: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
