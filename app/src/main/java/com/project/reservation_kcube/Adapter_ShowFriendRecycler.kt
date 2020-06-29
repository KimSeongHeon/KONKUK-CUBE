package com.project.reservation_kcube

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter_ShowFriendRecycler(var data:Array<Data_FriendInfo>)
    :RecyclerView.Adapter<Adapter_ShowFriendRecycler.ViewHolder>(){
    lateinit var context: Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_ShowFriendRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_favorite_friend,p0,false)
        context = p0.context
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_ShowFriendRecycler.ViewHolder, p1: Int) {
        p0.name.text = data[p1].name
        p0.s_id.text = data[p1].s_id
        p0.linear.setOnClickListener {
            (context as MainActivity).mWebView.loadUrl(one_click_add_script(data[p1].s_id))
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var linear: LinearLayout
        var s_id: TextView
        var name:TextView
        init{
            linear = itemView.findViewById(R.id.favorite_friend_linear)
            s_id = itemView.findViewById(R.id.s_id_text)
            name = itemView.findViewById(R.id.frined_name_text)
        }
    }

}
