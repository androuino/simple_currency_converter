package com.intellisrc.mobiledeveloperchallenge.ui.main.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.intellisrc.mobiledeveloperchallenge.R
import com.intellisrc.mobiledeveloperchallenge.data.CurrencyModel
import com.intellisrc.mobiledeveloperchallenge.data.HistoricalDataModel
import com.intellisrc.mobiledeveloperchallenge.data.RateDataModel
import com.intellisrc.mobiledeveloperchallenge.ui.main.CustomRecyclerView
import com.intellisrc.mobiledeveloperchallenge.ui.main.MainFragmentViewModel
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.lang.Exception
import java.lang.IllegalArgumentException
import io.reactivex.Observable as ioObservable

class RvCurrencies internal constructor(
    private val currencyList: MutableList<RateDataModel>,
    private val viewModel: MainFragmentViewModel
) : CustomRecyclerView() {
    private var context: Context? = null
    private var itemPosition = 0
    private lateinit var rvCurrencies: RecyclerView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        this.context = parent.context
        return when (viewType) {
            R.layout.rv_currency_items -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_currency_items, parent, false)
                ViewHolder(view)
            }
            else -> throw IllegalArgumentException(parent.context.getString(R.string.illegal_argument_exception))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.rv_currency_items -> (holder as ViewHolder).bind(currencyList, position)
        }
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemViewType(position: Int): Int {
        return R.layout.rv_currency_items
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        rvCurrencies = recyclerView
    }

    fun updateRatesInfo(newList: MutableList<RateDataModel>, activity: Activity?) {
        val callback = Callback(this.currencyList, newList)
        val diff = getDiff(callback)
            .subscribeOn(Schedulers.newThread())
            .subscribe { data ->
                this.currencyList.clear()
                this.currencyList.addAll(newList)
                activity?.runOnUiThread { data.dispatchUpdatesTo(this@RvCurrencies) }
            }
    }

    private fun getDiff(callback: Callback): ioObservable<DiffUtil.DiffResult> {
        return ioObservable.fromCallable { DiffUtil.calculateDiff(callback) } // FIXME: It's crashing here, perhaps I'll encapsulate it with try and catch block
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val parentLayout: ConstraintLayout = itemView.findViewById(R.id.parentLayout)
        private val tvCurrency: TextView = itemView.findViewById(R.id.tvCurrency)
        private val tvRate: TextView = itemView.findViewById(R.id.tvRate)

        fun bind(list: MutableList<RateDataModel>, position: Int) {
            val (currency, rate, currencyType) = list[position]
            tvCurrency.text = currency
            tvRate.text = rate.toString()
            if (currencyType.isNotEmpty()) {
                parentLayout.setBackgroundResource(R.color.colorRed)
            } else
                parentLayout.setBackgroundResource(R.color.colorRvItem)
        }
    }

    fun getItemPosition(): Int = itemPosition

    companion object {
        const val TAG = "RvCurrencies"
    }
}
