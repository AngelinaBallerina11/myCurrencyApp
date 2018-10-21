package com.angelinaandronova.mycurrencyapp.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.angelinaandronova.mycurrencyapp.MyApplication
import com.angelinaandronova.mycurrencyapp.R
import com.angelinaandronova.mycurrencyapp.di.modules.ViewModelFactory
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject

class MainFragment : Fragment() {

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyApplication.component.inject(this)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(MainViewModel::class.java)
        viewModel.currencyData.observe(this, android.arch.lifecycle.Observer { currencies ->
            currencies?.let {
                adapter?.run {
                    items.clear()
                    items.addAll(it)
                    notifyDataSetChanged()
                }
            }
        })


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = RatesAdapter(activity!!, arrayListOf<CurrencyData>())
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun showToast(msg: String) {
        Toast.makeText(activity!!, msg, Toast.LENGTH_SHORT).show()
    }

}
