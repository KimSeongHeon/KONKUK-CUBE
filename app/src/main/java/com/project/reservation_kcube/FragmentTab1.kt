package com.project.reservation_kcube

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.project.reservation_kcube.MainActivity.Companion.update_time
import kotlinx.android.synthetic.main.fragment_tab1.*
import org.json.JSONArray
import org.json.JSONObject

class FragmentTab1: Fragment() {
    lateinit var building_recycler:RecyclerView
    lateinit var date_recycler:RecyclerView
    lateinit var  date_adapter:Adapter_DateRecycler
    lateinit var building_adapter:Adapter_BuildingRecycler
    var Building_data:Array<String> = arrayOf();
    var Date_data:Array<String> = arrayOf()
    var scroll_position = 0;
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab1,container,false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        building_recycler = view!!.findViewById(R.id.where_recyclerview)
        date_recycler = view!!.findViewById(R.id.when_recyclerview)
        if(Building_data.size != 0) dispay_building(Building_data)
        if(Date_data.size != 0) display_date(Date_data)
        setToolbar()
    }
    fun dispay_building(value:Array<String>){
        Building_data = value;
        val where_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
        building_adapter = Adapter_BuildingRecycler(value)
        building_recycler.layoutManager = where_layoutManager
        building_recycler.adapter = building_adapter
        building_adapter.notifyDataSetChanged()
    }
    fun display_date(value:Array<String>){
        Date_data = value;
        top_date_textview.text= value[0]
        val when_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
        date_adapter = Adapter_DateRecycler(value,this)
        date_recycler.layoutManager = when_layoutManager
        date_recycler.adapter = date_adapter
        date_adapter.notifyDataSetChanged()
        setUpdateTime()
    }
    fun display_table(value:Array<String>){
        var json_arr = JSONArray()
        for(i in 0 until value.size){
            try{
                json_arr.put(i,JSONObject(value[i]))
            }catch (ex:Exception){
            }
        }
    }
    fun setToolbar(){
        var toolbar = activity!!.findViewById<Toolbar>(R.id.title_toolbar)
        var activity = activity as MainActivity
        activity.setSupportActionBar(toolbar)
        activity!!.findViewById<AppBarLayout>(R.id.top_date_Appbarlayout).addOnOffsetChangedListener(object:AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                var params:WindowManager.LayoutParams = activity!!.window.attributes
            }

        })
    }
    fun setUpdateTime(){
        updtetime_text.text = update_time + " 업데이트 됨"
    }


}