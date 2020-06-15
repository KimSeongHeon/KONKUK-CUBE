package com.project.reservation_kcube

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Adapter_TimeRecycler(var data:Array<String>)
    : RecyclerView.Adapter<Adapter_TimeRecycler.ViewHolder>(){
    lateinit var context: Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_TimeRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_time,p0,false)
        data = data.sortedArray()
        context = p0.context
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(p0: Adapter_TimeRecycler.ViewHolder, p1: Int) {
        p0.clock.text = data.get(p1)
        p0.interval.text = "대기"
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var clock: TextView
        var interval:TextView
        init{
            clock = itemView.findViewById(R.id.clock_textview)
            interval = itemView.findViewById(R.id.time_textview)
        }
    }

}
