package com.angelinaandronova.mycurrencyapp.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.angelinaandronova.mycurrencyapp.MyApplication
import com.angelinaandronova.mycurrencyapp.R
import com.angelinaandronova.mycurrencyapp.di.modules.ViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment(), RatesClickListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var adapter: RatesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.component.inject(this)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
        viewModel.currencyData.observe(this, android.arch.lifecycle.Observer { currencies ->
            currencies?.let {
                progress_bar.visibility = View.GONE
                adapter?.run {
                    when {
                        items.isEmpty() && viewModel.items.isEmpty() -> addInitialSet(it)
                        !viewModel.editMode -> addRates(it)
                    }
                }
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progress_bar.visibility = View.VISIBLE
        adapter = RatesAdapter(activity!!, viewModel.items, this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    override fun onItemClicked() {
        recyclerview.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.items = adapter.items
    }

}
