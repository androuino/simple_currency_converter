package com.intellisrc.mobiledeveloperchallenge.room.dao

import androidx.lifecycle.LiveData
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity

interface RatesDaoInterface {
    fun getRates(): LiveData<List<RatesEntity>>
    fun insert(ratesEntity: RatesEntity)
    fun update(ratesEntity: RatesEntity)
    fun countAll(): Int
}