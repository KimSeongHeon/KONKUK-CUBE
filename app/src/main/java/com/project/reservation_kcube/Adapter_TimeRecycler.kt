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
        p0.interval.text = "최대 " + successivetime(p1)
        p0.all.setOnClickListener{
            Log.v("onclick","click")
            var dragView = fragment.view!!.findViewById<LinearLayout>(R.id.up_reserve)
            var drag = fragment.view!!.findViewById<SlidingUpPanelLayout>(R.id.sliding_layout)
            Log.v("drag",drag.toString())
            drag.panelState = SlidingUpPanelLayout.PanelState.EXPANDED
            dragView.visibility = View.VISIBLE
            var time = data.get(p1)
            fragment.select_clock = time
            var script = click_reserve(time,info)
            (context as MainActivity).mWebView.loadUrl(script)
            fragment.successive_time = (successivetime(p1).split(' ')[0].toDouble())
        }
    }
    fun successivetime(position:Int):String{
        var ret = 1.0;
        var pre_hour = data[position].split(':')[0]
        var pre_minute = data[position].split(":")[1]
        Log.v("pre_hour",pre_hour)
        Log.v("pre_minute",pre_minute)
        Log.v("Position",position.toString())
        for(i in position+1 until position+6){
            if(i >= data.size) break;
            var current_hour = data[i].split(':')[0]
            var current_minute = data[i].split(':')[1]
            if(pre_minute == "30"){
                Log.v("current",current_hour.toString())
                Log.v("pre",pre_hour.toString())
                if(current_hour.toInt() == pre_hour.toInt() + 1) ret+=1
                else break
            }
            else if(pre_minute == "00"){
                if(current_hour == pre_hour && current_minute.toInt() == pre_minute.toInt() + 30) ret+=1
                else break
            }
            pre_hour = current_hour
            pre_minute = current_minute
        }
        return (ret.div(2)).toString() + " 시간"
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
