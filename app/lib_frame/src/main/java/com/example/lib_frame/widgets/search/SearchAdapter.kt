package com.example.lib_frame.widgets.search

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build.VERSION_CODES
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.example.lib_frame.R
import java.util.Locale

class SearchAdapter(
    context: Context?,
    private var suggestions: Array<String>,
    private var suggestionIcon: Drawable?,
    private var ellipsize: Boolean
) : BaseAdapter(), Filterable {
    private var data: ArrayList<String>
    private var inflater: LayoutInflater

    init {
        inflater = LayoutInflater.from(context)
        data = ArrayList()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence): FilterResults {
                val filterResults = FilterResults()
                if (!TextUtils.isEmpty(constraint)) {

                    // Retrieve the autocomplete results.
                    val searchData: MutableList<String> = ArrayList()
                    for (string in suggestions) {
                        if (string.lowercase(Locale.getDefault())
                                .startsWith(constraint.toString().lowercase(Locale.getDefault()))
                        ) {
                            searchData.add(string)
                        }
                    }

                    // Assign the data to the FilterResults
                    filterResults.values = searchData
                    filterResults.count = searchData.size
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                if (results.values != null) {
                    data = results.values as ArrayList<String>
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View, parent: ViewGroup): View {
        val viewHolder: SuggestionsViewHolder = convertView.tag as SuggestionsViewHolder
        val currentListData = getItem(position) as String
        viewHolder.textView.text = currentListData
        if (ellipsize) {
            viewHolder.textView.setSingleLine()
            viewHolder.textView.ellipsize = TextUtils.TruncateAt.END
        }
        return convertView
    }

    private inner class SuggestionsViewHolder(convertView: View) {
        var textView: TextView
        var imageView: ImageView? = null

        init {
            textView = convertView.findViewById(R.id.suggestion_text)
            if (suggestionIcon != null) {
                imageView = convertView.findViewById(R.id.suggestion_icon)
                imageView?.setImageDrawable(suggestionIcon)
            }
        }
    }
}