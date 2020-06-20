package com.project.reservation_kcube

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView

class Adapter_AddFriendRecycler(var id:Array<String>,var name:Array<String>): RecyclerView.Adapter<Adapter_AddFriendRecycler.ViewHolder>() {
    lateinit var context:Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_AddFriendRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_friend, p0, false)
        context = p0.context
        return ViewHolder(v);
    }

    override fun getItemCount(): Int {
        return id.size
    }

    override fun onBindViewHolder(p0: Adapter_AddFriendRecycler.ViewHolder, p1: Int) {
        p0.id.text = id[p1]
        p0.name.text = name[p1]
        p0.cancel.setOnClickListener {
            val script = delete_friend_script(p1)
            (context as MainActivity).mWebView.loadUrl(script)
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var id: TextView
        var name: TextView
        var cancel:ImageButton
        init{
            id = itemView.findViewById(R.id.friend_id_textview)
            name = itemView.findViewById(R.id.friend_name_textview)
            cancel = itemView.findViewById(R.id.cancel_imgbutton)
        }
    }

}
