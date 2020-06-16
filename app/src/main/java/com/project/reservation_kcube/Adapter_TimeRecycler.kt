package com.project.reservation_kcube

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.sothree.slidinguppanel.SlidingUpPanelLayout

class Adapter_TimeRecycler(var data:Array<String>,var fragment:FragmentTab1,var info:Pair<Int,Int>)
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
        p0.all.setOnClickListener{
            Log.v("onclick","click")
            var dragView = fragment.view!!.findViewById<LinearLayout>(R.id.up_reserve)
            var drag = fragment.view!!.findViewById<SlidingUpPanelLayout>(R.id.sliding_layout)
            Log.v("drag",drag.toString())
            drag.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            dragView.visibility = View.VISIBLE
            var time = data.get(p1)
            var script = click_reserve(time,info)
            (context as MainActivity).mWebView.loadUrl(script)
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var clock: TextView
        var interval:TextView
        var all:LinearLayout
        init{
            all = itemView.findViewById(R.id.time_all_linearlayout)
            clock = itemView.findViewById(R.id.clock_textview)
            interval = itemView.findViewById(R.id.time_textview)
        }
    }

}
