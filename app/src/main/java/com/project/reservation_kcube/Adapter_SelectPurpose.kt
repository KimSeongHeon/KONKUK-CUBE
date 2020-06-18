package com.project.reservation_kcube

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView

class Adapter_SelectPurpose(var data:Array<String>)
    : RecyclerView.Adapter<Adapter_SelectPurpose.ViewHolder>() {
    var selectedPosition = 0;
    lateinit var context: Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_SelectPurpose.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_purpose,p0,false)
        context = p0.context
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_SelectPurpose.ViewHolder, p1: Int) {
        p0.purpose.text = data[p1];
        if(selectedPosition == p1){
            p0.purpose.setTextColor(Color.DKGRAY)
            p0.purpose.setTypeface(null, Typeface.BOLD)
            p0.linear.setBackgroundResource(R.drawable.selected_background_border)
        }
        else {
            p0.purpose.setTextColor(Color.DKGRAY)
            p0.purpose.setTypeface(null, Typeface.NORMAL)
            p0.linear.setBackgroundResource(R.drawable.notselected_background_border)
        }
        p0.linear.setOnClickListener {
            var script = select_purpose_script(p1)
            (context as MainActivity).mWebView.loadUrl(script)
            selectedPosition = p1
            notifyDataSetChanged()
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var linear: LinearLayout
        var purpose: TextView
        init{
            linear = itemView.findViewById(R.id.purpose_linear)
            purpose = itemView.findViewById(R.id.purpose_text)
        }
    }

}
