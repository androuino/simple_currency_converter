package com.intellisrc.mobiledeveloperchallenge.room.dao

import androidx.lifecycle.LiveData
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity
import javax.inject.Inject

class RatesDaoImpl @Inject constructor(private val ratesDao: RatesDao) : RatesDaoInterface {
    override fun getRates(): LiveData<List<RatesEntity>> {
        return ratesDao.getRates()
    }

    override fun insert(ratesEntity: RatesEntity) {
        ratesDao.insert(ratesEntity)
    }

    override fun update(ratesEntity: RatesEntity) {
        ratesDao.update(ratesEntity)
    }

    override fun countAll(): Int {
        return ratesDao.countAll()
    }
}