package com.project.reservation_kcube

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Adapter_RoomRecycler(var data:Array<Int>,var time_info:MutableMap<Pair<Int,Int>,ArrayList<String>>,var building:Int)
    : RecyclerView.Adapter<Adapter_RoomRecycler.ViewHolder>() {
    lateinit var time_adapter:Adapter_TimeRecycler
    lateinit var context: Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_RoomRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_room,p0,false)
        context = p0.context
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_RoomRecycler.ViewHolder, p1: Int) {
        p0.onBind(p1)
        p0.room_num.text = data.get(p1).toString() + " 호실"
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var room_num:TextView
        var rcyview:RecyclerView
        init{
            room_num = itemView.findViewById(R.id.room_textview)
            rcyview = itemView.findViewById(R.id.room_recyclerview)
        }
        fun onBind(position: Int){
            time_adapter = Adapter_TimeRecycler(time_info[Pair(building,data[position])]!!.toTypedArray())
            rcyview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            rcyview.adapter = time_adapter
        }
    }

}
