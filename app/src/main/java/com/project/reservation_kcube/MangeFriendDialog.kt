package com.project.reservation_kcube

import android.app.Dialog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MangeFriendDialog(var context: Context?) {
    var friend_info = arrayListOf<Data_FriendInfo>()
    fun callFunction() {
        val dlg = Dialog(context)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.manage_friend_dialog)
        dlg.show()
        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        loadFriend()
        val friend_list = dlg.findViewById<RecyclerView>(R.id.friend_list_rcyview)
        val frined_text = dlg.findViewById<TextView>(R.id.no_friend_text)
        val okButton = dlg.findViewById(R.id.okButton) as Button
        if(friend_info.size == 0){
            friend_list.visibility = View.GONE
            frined_text.visibility = View.VISIBLE
        }
        else{
            friend_list.visibility = View.VISIBLE
            frined_text.visibility = View.GONE
            var show_friend_layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
            friend_list.layoutManager = show_friend_layoutManager
            var manage_friend_adapter = Adapter_ManageFreind(friend_info.toTypedArray())
            friend_list.adapter = manage_friend_adapter
            manage_friend_adapter.notifyDataSetChanged()
        }
        okButton.setOnClickListener {
            dlg.dismiss()
        }
    }
    fun loadFriend(){
        var ReadDB: SQLiteDatabase = (context as MainActivity).openOrCreateDatabase(LoginActivity.DATABASE_NAME, Context.MODE_PRIVATE,null)
        Log.v("id",LoginActivity.id)
        var c = ReadDB.rawQuery("SELECT * FROM " + LoginActivity.TABLE_NAME2 + " WHERE ID = \"${LoginActivity.id}\" ORDER BY date desc" ,null)
        if(c.moveToFirst()){
            do{
                var id = c.getString(c.getColumnIndex("ID"));
                var s_id = c.getString(c.getColumnIndex("S_ID"))
                var name = c.getString(c.getColumnIndex("NAME"))
                var date = c.getString(c.getColumnIndex("date"))
                Log.v("load_friend_data",id+s_id+name+date)
                friend_info.add(Data_FriendInfo(id,s_id,name,date))
            }while(c.moveToNext())
        }
    }
}
