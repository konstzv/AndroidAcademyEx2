package com.zagulin.mycard.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.zagulin.mycard.models.Category

class CategoryAdapter(context: Context, objects: MutableList<Category>) : ArrayAdapter<Category>(context, 0, objects) {


    private val layoutInflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        val viewHolder: ViewHolder
        if (convertView == null) {
            view = layoutInflater.inflate(android.R.layout.simple_spinner_item, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        viewHolder.bind(getItem(position))
        return view


    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)


    }

    open class ViewHolder(view: View) {

        private val textView = view.findViewById<TextView>(android.R.id.text1)
        fun bind(item: Category) {
            textView.text = item.name
        }
    }

}