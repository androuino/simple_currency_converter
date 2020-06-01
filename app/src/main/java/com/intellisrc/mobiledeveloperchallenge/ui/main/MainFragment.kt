package com.intellisrc.mobiledeveloperchallenge.ui.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.intellisrc.mobiledeveloperchallenge.data.RateDataModel
import com.intellisrc.mobiledeveloperchallenge.databinding.FragmentMainBinding
import com.intellisrc.mobiledeveloperchallenge.room.entity.RatesEntity
import com.intellisrc.mobiledeveloperchallenge.ui.CustomLinearLayout
import com.intellisrc.mobiledeveloperchallenge.ui.base.BaseFragment
import com.intellisrc.mobiledeveloperchallenge.ui.main.adapters.RvCurrencies
import com.intellisrc.mobiledeveloperchallenge.utils.Preconditions
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class MainFragment: BaseFragment<MainFragmentViewModel>(), LifecycleOwner {
    private var viewModel: MainFragmentViewModel? = null
    private lateinit var viewBinding: FragmentMainBinding
    private var rvCurrenciesAdapter: RvCurrencies? = null
    private var historicalDataList = ArrayList<RateDataModel>()
    private var ratesEntityList = ArrayList<RatesEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentMainBinding.inflate(inflater, container, false)
        viewBinding.viewmodel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observers()

        etAmount.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewLifecycleOwner.lifecycleScope.launch {
                    delay(1000)
                    viewModel?.getRatesEntity()?.observe(viewLifecycleOwner, Observer {
                        historicalDataList.clear()
                        it.forEach { rate ->
                            var usdRate = 0.0
                            if (rate.currency == "USDUSD")
                                usdRate = rate.rate!!
                            var todayRate = 0.0
                            var convert = 0.0
                            val amount = if (etAmount.text.isNotEmpty())
                                etAmount.text.toString().toDouble()
                            else
                                0.0
                            var fiat = ""

                            if (autoCompleteConvert.text.isNotEmpty() && rate.currency == "USD${autoCompleteConvert.text.toString().toUpperCase()}")
                                todayRate = rate.rate!!

                            if (todayRate != 0.0 && rate.currency == "USD${autoCompleteConvert.text.toString().toUpperCase()}") {
                                if (amount != 0.0) {
                                    convert = amount.div(todayRate)
                                    fiat = "${autoCompleteConvert.text.toString().toUpperCase()}USD"
                                    historicalDataList.add(RateDataModel(fiat, convert, fiat))
                                } else {
                                    convert = rate.rate!!
                                    fiat = rate.currency!!
                                    historicalDataList.add(RateDataModel(fiat, convert))
                                }
                            } else {
                                historicalDataList.add(RateDataModel(rate.currency!!, rate.rate!!))
                            }
                        }
                        rvCurrenciesAdapter?.updateRatesInfo(historicalDataList, activity)
                    })
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun observers() {
        viewModel?.getCurrency?.observe(viewLifecycleOwner, Observer {
            if (it.size > 0) {
                it.let { list ->
                    list.sort()
                    val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, list)
                    autoCompleteConvert.setAdapter(adapter)
                    adapter.notifyDataSetChanged()
                }
            }
        })
        viewModel?.getHistoricalData?.observe(viewLifecycleOwner, Observer {
            historicalDataList.clear()
            it.quotes.forEach { (k, v) ->
                historicalDataList.add(RateDataModel(k, v))
            }
            rvCurrenciesAdapter?.updateRatesInfo(historicalDataList, activity)
        })
    }

    private fun initAdapter() {
        val list = ArrayList<RateDataModel>(1)
        rvCurrenciesAdapter = RvCurrencies(list, viewModel!!)
        rvCurrencies.adapter = rvCurrenciesAdapter
        rvCurrencies.layoutManager = CustomLinearLayout(activity)
        rvCurrenciesAdapter?.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.getCurrency?.removeObservers(viewLifecycleOwner)
        viewModel?.getHistoricalData?.removeObservers(viewLifecycleOwner)
    }

    private fun doCalculation(from: String, to: String, amount: Double) {

    }

    override fun bindViewModel(viewModel: MainFragmentViewModel) {
        Preconditions.checkNotNull(viewModel)
        if (this.viewModel == viewModel) {
            return
        }
        this.viewModel = viewModel
    }

    companion object {
        const val TAG = "MainFragment"
    }
}