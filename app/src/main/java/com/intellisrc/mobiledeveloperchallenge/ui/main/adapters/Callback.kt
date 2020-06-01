package com.intellisrc.mobiledeveloperchallenge.ui.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.intellisrc.mobiledeveloperchallenge.data.LatestRatesDataModel
import timber.log.Timber

class Callback(
    private val oldList: MutableList<LatestRatesDataModel>,
    private val newList: MutableList<LatestRatesDataModel>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        try {
            return oldList[oldItemPosition].rates == newList[newItemPosition].rates
        } catch (ex: Exception) {
            Timber.tag(TAG).e("Error in areItemsTheSame()->${ex.message}")
        }
        return false
    }

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        try {
            val old = oldList[oldItemPosition]
            val new = newList[newItemPosition]
            return old.rates == new.rates
        } catch (ex: Exception) {
            Timber.tag(TAG).e("Error in areContentsTheSame()->${ex.message}")
        }
        return false
    }

    companion object {
        const val TAG = "Callback"
    }
}