package com.project.reservation_kcube

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject

class FragmentTab2: Fragment() {
    lateinit var reserve_list_rcyview: RecyclerView
    lateinit var reserve_list_adapter:Adapter_ReserveListRecycler
    lateinit var progressBar:ProgressBar
    var load = false
    var reserve_list_layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL,false)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.v("onAcitivity2","created")
        return inflater.inflate(R.layout.fragment_tab2,container,false);
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.v("onAcitivity2","createdActivity")
        load = true
        reserve_list_rcyview = view!!.findViewById<RecyclerView>(R.id.reserve_list_rcyview)
        progressBar = view!!.findViewById(R.id.loading_progressBar2)
        super.onActivityCreated(savedInstanceState)
    }
    fun init_variable(){

    }
    fun display_reserveList(value:Array<String>){
        var json_arr = JSONArray()
        for(i in 0 until value.size){
            try{
                json_arr.put(i, JSONObject(value[i]))
            }catch (ex:Exception){
                Log.e("JSONARRAY",ex.toString());
            }
        }
        var data = arrayListOf<Data_ReserveInfo>()
        for(i in 0 until json_arr.length()){
            var jsonObject = json_arr.getJSONObject(i);
            var click_day = jsonObject.getString("click_day").toString();
            var day = jsonObject.getString("day").toString()
            var time = jsonObject.getString("time").toString()
            var location = jsonObject.getString("location").toString()
            var accept = jsonObject.getString("accept").toString()
            data.add(Data_ReserveInfo(click_day,day,time,location,accept))
        }
        reserve_list_layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL,false)
        reserve_list_rcyview.layoutManager = reserve_list_layoutManager
        reserve_list_adapter = Adapter_ReserveListRecycler(data.toTypedArray())
        reserve_list_rcyview.adapter = reserve_list_adapter
        reserve_list_adapter.notifyDataSetChanged()
        reserve_list_rcyview.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}