package com.project.reservation_kcube

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Adapter_RoomRecycler(var data:Array<Int>,var time_info:MutableMap<Pair<Int,Int>,ArrayList<String>>,var building:Int,var room_info:MutableMap<Int,Data_roomInfo>,var fragement1:FragmentTab1)
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
        Log.v("check",room_info.get(data.get(p1)).toString())
        p0.room_num.text = convertRoomNumToName(data.get(p1)) + "("+ room_info.get(data.get(p1))!!.acceptanceNum + ")"
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var room_num:TextView
        var rcyview:RecyclerView
        init{
            room_num = itemView.findViewById(R.id.room_textview)
            rcyview = itemView.findViewById(R.id.room_recyclerview)
        }
        fun onBind(position: Int){
            time_adapter = Adapter_TimeRecycler(time_info[Pair(building,data[position])]!!.toTypedArray(),fragement1,Pair(building,data[position]))
            rcyview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            rcyview.adapter = time_adapter
        }
    }

}
