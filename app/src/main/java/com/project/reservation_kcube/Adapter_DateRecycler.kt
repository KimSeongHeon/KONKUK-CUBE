package com.project.reservation_kcube

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_tab1.*

class Adapter_DateRecycler(var data:Array<String>)
    :RecyclerView.Adapter<Adapter_DateRecycler.ViewHolder>() {
    lateinit var context: Context
    var selectedPosition = 0;
    var mSelectedItems = SparseBooleanArray(0)
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_DateRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_date, p0, false)
        context = p0.context;
        mSelectedItems.put(0,true)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_DateRecycler.ViewHolder, p1: Int) {
        p0.date.text = data[p1].split(" ").get(2).removePrefix("일")
        when(p1){
            0->{
                p0.day.text = "오늘"
            }
            1->{
                p0.day.text = "내일"
            }
            else->{
                p0.day.text = data[p1].split(" ").get(3).get(1).toString()
            }
        }
        if(selectedPosition == p1){
            p0.date.setBackgroundResource(R.drawable.green_circle)
            p0.date.setTextColor(Color.parseColor("#FFFFFF"))
            (context as MainActivity).top_date_textview.text = data[p1]
        }
        else{
            p0.date.setBackgroundResource(0)
            p0.date.setTextColor(Color.parseColor("#000000"))
        }
        p0.date.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                Log.v("asf","click")
                selectedPosition = p1
                notifyDataSetChanged()
            }
        })
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date:TextView
        var day:TextView
        init{
            date = itemView.findViewById(R.id.date_textview)
            day = itemView.findViewById(R.id.day_textview)
        }
    }

}