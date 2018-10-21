package com.angelinaandronova.mycurrencyapp.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Build
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.angelinaandronova.mycurrencyapp.MyApplication
import com.angelinaandronova.mycurrencyapp.R
import com.angelinaandronova.mycurrencyapp.di.modules.ViewModelFactory
import com.angelinaandronova.mycurrencyapp.network.rates.model.Currency
import com.angelinaandronova.mycurrencyapp.utils.GlideApp
import kotlinx.android.synthetic.main.list_item_currency.view.*
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import java.util.*
import javax.inject.Inject


class RatesAdapter(val context: Context, val items: ArrayList<CurrencyData>, val clickListener: RatesClickListener) :
    RecyclerView.Adapter<RatesAdapter.RatesViewHolder>() {

    companion object {
        const val STRING_RESOURCE = "string"
        const val EURO_DEFAULT_VALUE = 1.0
        const val TWO_DECIMAL_NUMBERS = "%.2f"
    }

    private var viewModel: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    init {
        MyApplication.component.inject(this)
        viewModel = ViewModelProviders.of(context as FragmentActivity, viewModelFactory).get(MainViewModel::class.java)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        return RatesViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.list_item_currency, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        holder.rate.removeTextChangedListener(textWatcher)
        val currentItem = items[position]

        GlideApp
            .with(context)
            .load(getImageUrl(currentItem))
            .fallback(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(holder.flagImage)
        holder.code.text = currentItem.code
        holder.currencyFullName.text = getCurrencyNameFromCode(currentItem.code)
        holder.rate.setText(TWO_DECIMAL_NUMBERS.format(getCurrentLocale(), currentItem.exchangeRate))
        holder.container.setOnClickListener {
            if (position > 0) {
                reorder(currentItem, position)
            }
            holder.rate.requestFocus()
            holder.rate.setSelection(holder.rate.text.length)
            clickListener.onItemClicked()
        }

        if (position == 0) {
            holder.rate.isCursorVisible = true
            holder.rate.isClickable = true
            holder.rate.isFocusable = true
            holder.rate.isFocusableInTouchMode = true
            holder.rate.requestFocus()
            holder.rate.setSelection(holder.rate.text.length)
            holder.rate.addTextChangedListener(textWatcher)
            holder.rate.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    Log.i("TESTING", "hasFocus")
                    showKeyboard(v as EditText)
                }
            }
            holder.rate.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    actionId == EditorInfo.IME_ACTION_NEXT ||
                    (event != null &&
                            event.action == KeyEvent.ACTION_DOWN &&
                            event.keyCode == KeyEvent.KEYCODE_ENTER)
                ) {
                    if (event == null || !event.isShiftPressed) {
                        viewModel.editMode = false
                        holder.rate.clearFocus()
                        hideKeyboard(holder.rate)
                        true
                    }
                }
                false
            }
            holder.rate.setOnClickListener {
                viewModel.editMode = true
                holder.rate.text.clear()
            }
        } else {
            holder.rate.isCursorVisible = false
            holder.rate.isClickable = false
            holder.rate.isFocusable = false
            holder.rate.isFocusableInTouchMode = false
            holder.rate.removeTextChangedListener(textWatcher)
            holder.rate.onFocusChangeListener = null
        }
    }

    private fun reorder(currentItem: CurrencyData, position: Int) {
        val topList = items.subList(0, position)
        val bottomList = items.subList(position + 1, items.size)
        val newList = listOf(currentItem) + topList + bottomList
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    private fun getCurrentLocale(): Locale {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales.get(0);
        } else {
            context.resources.configuration.locale;
        }
    }

    private fun showKeyboard(editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun hideKeyboard(editText: EditText) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(enteredNumber: Editable?) {
            if (!enteredNumber.isNullOrEmpty()) {
                items[0].exchangeRate = enteredNumber.toString().toDouble()
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }

    fun addInitialSet(newItems: ArrayList<CurrencyData>) {
        items.clear()
        items.add(CurrencyData(Currency.EUR.name, EURO_DEFAULT_VALUE))
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun addRates(newItems: ArrayList<CurrencyData>) {
        GlobalScope.launch {
            var factor = 1.0
            items.forEach { _ ->
                newItems.forEach {
                    if (items[0].code == it.code) factor = items[0].exchangeRate!! / it.exchangeRate!!
                    if (items[0].code == Currency.EUR.name) factor = items[0].exchangeRate!! / EURO_DEFAULT_VALUE
                }
            }
            items.forEach { listItem ->
                newItems.forEach {
                    if (listItem.code == it.code && listItem.code != items[0].code) listItem.exchangeRate =
                            (it.exchangeRate!! * factor)
                    if (listItem.code == Currency.EUR.name && listItem.code != items[0].code) listItem.exchangeRate =
                            (EURO_DEFAULT_VALUE * factor)
                }
            }
        }
        notifyDataSetChanged()
    }

    private fun getImageUrl(item: CurrencyData): String? {
        val imageCode = getImageCode(item)
        imageCode?.let { return "https://www.countryflags.io/$imageCode/flat/64.png" }
        return null
    }

    private fun getImageCode(item: CurrencyData): String? {
        if (Currency.values().any { it.name == item.code }) {
            return Currency.values().firstOrNull { it.name == item.code }?.countryFlagCode?.toLowerCase()
        }
        return null
    }

    private fun getCurrencyNameFromCode(code: String): String {
        return context.resources.getString(context.resources.getIdentifier(code, STRING_RESOURCE, context.packageName))
    }

    class RatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val container: ConstraintLayout = view.container
        val flagImage: ImageView = view.imageView_country_flag
        val code: TextView = view.textView_currency_code
        val currencyFullName: TextView = view.textView_currency_full_text
        val rate: EditText = view.editText_rate
    }

}

interface RatesClickListener {
    fun onItemClicked()
}



