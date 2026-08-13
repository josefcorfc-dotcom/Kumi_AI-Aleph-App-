package com.example.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SystemLogDao {
    @Query("SELECT * FROM system_logs ORDER BY timestamp DESC LIMIT 20")
    fun getRecentLogs(): Flow<List<SystemLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: SystemLogEntity)

    @Query("DELETE FROM system_logs")
    suspend fun clearLogs()
}

@Dao
interface NeuroMantraDao {
    @Query("SELECT * FROM neuro_mantras ORDER BY timestamp DESC LIMIT 1")
    fun getLatestMantra(): Flow<NeuroMantraEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMantra(mantra: NeuroMantraEntity)
}
