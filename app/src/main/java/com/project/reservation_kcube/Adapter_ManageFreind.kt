package com.project.reservation_kcube

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class Adapter_ManageFreind(var data:Array<Data_FriendInfo>)
    :RecyclerView.Adapter<Adapter_ManageFreind.ViewHolder>(){
    lateinit var context: Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_ManageFreind.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_friend,p0,false)
        context = p0.context
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_ManageFreind.ViewHolder, p1: Int) {
        p0.id.text = data[p1].s_id
        p0.name.text = data[p1].name
        p0.cancel.setOnClickListener {
            var arrList = data.toCollection(ArrayList())
            arrList.removeAt(p1)
            LoginActivity.DB.execSQL("DELETE FROM " + LoginActivity.TABLE_NAME2 + " where id = \'${LoginActivity.id}\' and s_id = \'${data[p1].s_id}\';")
            Toast.makeText(context,"삭제되었습니다.",Toast.LENGTH_SHORT).show()
            data = arrList.toTypedArray()
            notifyItemRemoved(p1)
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var id: TextView
        var name: TextView
        var cancel: ImageButton
        init{
            id = itemView.findViewById(R.id.friend_id_textview)
            name = itemView.findViewById(R.id.friend_name_textview)
            cancel = itemView.findViewById(R.id.cancel_imgbutton)
        }
    }

}
