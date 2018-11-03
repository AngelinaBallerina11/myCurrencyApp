package com.angelinaandronova.mycurrencyapp.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
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
import java.net.UnknownHostException
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
                if (recyclerview.visibility == View.GONE) recyclerview.visibility = View.VISIBLE
                adapter?.run {
                    when {
                        items.isEmpty() && viewModel.items.isEmpty() -> addInitialSet(it)
                        !viewModel.editMode -> addRates(it)
                    }
                }
            }
        })
        viewModel.error.observe(this, Observer { error ->
            error?.let {
                when (it) {
                    is UnknownHostException -> {
                        showSnackBar()
                        recyclerview.visibility = View.GONE
                    }
                    else -> Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun fetchData() {
        progress_bar.visibility = View.VISIBLE
        if (isOnline()) {
            viewModel.startFetchingRates()
        } else {
            showSnackBar()
        }
    }

    private fun isOnline(): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }

    private fun showSnackBar() {
        if (isAdded) {
            progress_bar.visibility = View.GONE
            Snackbar.make(main, activity!!.resources.getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE)
                .setAction(activity!!.resources.getString(R.string.retry)) { fetchData() }
                .show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progress_bar.visibility = View.VISIBLE
        adapter = RatesAdapter(activity!!, viewModel.items, this)
        recyclerview.adapter = adapter
        recyclerview.layoutManager = LinearLayoutManager(activity)
        fetchData()
    }

    override fun onItemClicked() {
        recyclerview.smoothScrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.items = adapter.items
    }

}
