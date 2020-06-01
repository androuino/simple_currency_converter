package com.intellisrc.mobiledeveloperchallenge.ui.main.adapters

import androidx.recyclerview.widget.DiffUtil
import com.intellisrc.mobiledeveloperchallenge.data.HistoricalDataModel
import com.intellisrc.mobiledeveloperchallenge.data.RateDataModel
import timber.log.Timber

class Callback(
    private val oldList: MutableList<RateDataModel>,
    private val newList: MutableList<RateDataModel>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        try {
            return oldList[oldItemPosition].rate == newList[newItemPosition].rate
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
            return old.rate == new.rate
        } catch (ex: Exception) {
            Timber.tag(TAG).e("Error in areContentsTheSame()->${ex.message}")
        }
        return false
    }

    companion object {
        const val TAG = "Callback"
    }
}