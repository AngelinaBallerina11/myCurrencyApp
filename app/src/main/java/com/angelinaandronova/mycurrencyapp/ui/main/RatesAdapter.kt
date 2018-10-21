package com.angelinaandronova.mycurrencyapp.ui.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.angelinaandronova.mycurrencyapp.R
import com.angelinaandronova.mycurrencyapp.network.rates.model.Currency
import com.angelinaandronova.mycurrencyapp.utils.GlideApp
import kotlinx.android.synthetic.main.list_item_currency.view.*

class RatesAdapter(val context: Context, val items: ArrayList<CurrencyData>) : RecyclerView.Adapter<RatesViewHolder>() {

    companion object {
        const val STRING_RESOURCE = "string"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatesViewHolder {
        return RatesViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.list_item_currency, parent, false)
        )
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RatesViewHolder, position: Int) {
        val currentItem = items[position]

        GlideApp
            .with(context)
            .load(getImageUrl(currentItem))
            .fallback(R.drawable.ic_launcher_foreground)
            .centerCrop()
            .into(holder.flagImage)
        holder.code.text = currentItem.code
        holder.currencyFullName.text = getCurrencyNameFromCode(currentItem.code)
        holder.rate.setText(currentItem.exchangeRate.toString())
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

}

class RatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val flagImage: ImageView = view.imageView_country_flag
    val code: TextView = view.textView_currency_code
    val currencyFullName: TextView = view.textView_currency_full_text
    val rate: EditText = view.editText_rate
}

