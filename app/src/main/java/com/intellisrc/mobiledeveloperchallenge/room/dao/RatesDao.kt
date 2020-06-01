package com.intellisrc.mobiledeveloperchallenge.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity

@Dao
interface RatesDao {
    @androidx.room.Query("SELECT * from rates_table ORDER BY id ASC")
    fun getRates(): LiveData<List<RatesEntity>>

    @Insert(onConflict = REPLACE)
    fun insert(ratesEntityList: RatesEntity)

    @Update(onConflict = REPLACE)
    fun update(ratesEntity: RatesEntity)

    @Query("select count(*) from rates_table")
    fun countAll(): Int
}