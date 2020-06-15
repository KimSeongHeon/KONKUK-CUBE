package com.project.reservation_kcube

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.project.reservation_kcube.MainActivity.Companion.update_time
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_tab1.*
import org.json.JSONArray
import org.json.JSONObject





class FragmentTab1: Fragment() {
    lateinit var building_recycler:RecyclerView
    lateinit var date_recycler:RecyclerView
    lateinit var arcodian_recycler:RecyclerView
    lateinit var  date_adapter:Adapter_DateRecycler
    lateinit var building_adapter:Adapter_BuildingRecycler
    lateinit var arcodian_adapter:Adapter_ArcodianRecycler
    var Building_data:Array<String> = arrayOf();
    var Date_data:Array<String> = arrayOf()
    var date_recyclerview_scroll = 0;
    var when_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
    var where_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
    var arcodian_layoutManager = LinearLayoutManager(this.context,LinearLayout.VERTICAL,false)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.v("onView","crated")
        return inflater.inflate(R.layout.fragment_tab1,container,false);
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.v("onAcitivity","crated")
        super.onActivityCreated(savedInstanceState)
        building_recycler = view!!.findViewById(R.id.where_recyclerview)
        date_recycler = view!!.findViewById(R.id.when_recyclerview)
        var parent = view!!.findViewById<View>(R.id.include_view)
        arcodian_recycler = parent.findViewById(R.id.acordian_recyclerview)
        Log.v("building data",Building_data.size.toString())
        Log.v("date_data",Date_data.size.toString())
        if(Building_data.size != 0) dispay_building(Building_data)
        if(Date_data.size != 0) display_date(Date_data)
        init_variable()
        setToolbar()
    }
    fun dispay_building(value:Array<String>){
        where_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
        building_recycler.layoutManager = where_layoutManager
        building_adapter = Adapter_BuildingRecycler(value)
        building_recycler.adapter = building_adapter
        building_adapter.notifyDataSetChanged()
    }
    fun display_date(value:Array<String>){
        when_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
        top_date_textview.text= value[(context as MainActivity).date_index]
        date_recycler.layoutManager = when_layoutManager
        date_adapter = Adapter_DateRecycler(value,this)
        date_recycler.adapter = date_adapter
        when_layoutManager.scrollToPosition(date_recyclerview_scroll)
        date_adapter.notifyDataSetChanged()
        setUpdateTime()
    }
    fun display_table(value:Array<String>){
        var json_arr = JSONArray()
        for(i in 0 until value.size){
            try{
                var str = value[i].replace("\\","");
                str = str.substring(1,str.length-1);
                json_arr.put(i,JSONObject(str))
            }catch (ex:Exception){
                Log.e("JSONARRAY",ex.toString());
            }
        }
        var building_info = HashSet<Int>()
        var room_num_info = mutableMapOf<Int,HashSet<Int>>()
        var time_info = mutableMapOf<Pair<Int,Int>,ArrayList<String>>()
        var room_info = mutableMapOf<Pair<Int,Int>,Data_roomInfo>()
        for(i in 0 until json_arr.length()){
            var jsonObject = json_arr.getJSONObject(i);
            var building = jsonObject.getString("buildSeq").toInt();
            var room_num = jsonObject.getString("roomSeq").toInt()
            building_info.add(building)
            if(room_num_info.containsKey(building)) room_num_info[building]!!.add(room_num);
            else room_num_info.put(building, hashSetOf(room_num));
            var key = Pair(building,room_num)
            var value = jsonObject.getString("rsvStartHm")
            if(time_info[key] == null) time_info.put(key, arrayListOf(value))
            else time_info[key]!!.add(value)
        }
        Log.v("building",building_info.size.toString())
        arcodian_layoutManager =  LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL,false)
        arcodian_adapter = Adapter_ArcodianRecycler(building_info.toTypedArray(),room_num_info,time_info,this)
        arcodian_recycler.layoutManager = arcodian_layoutManager
        arcodian_recycler.adapter = arcodian_adapter
        arcodian_adapter.notifyDataSetChanged()
        /*for(i in 0 until building_info.size){
            var layout = building_layout((context as MainActivity).applicationContext)
            var contents_area = layout.findViewById<LinearLayout>(R.id.room_linearlayout);
            layout.findViewById<TextView>(R.id.building_title_text).setOnClickListener {
                if(contents_area.visibility == View.GONE) {
                    contents_area.animate().alpha(1.0f).setDuration(1000)
                    contents_area.visibility = View.VISIBLE
                }
                else {
                    contents_area.animate().alpha(0.0f).setDuration(2000)
                    contents_area.visibility = View.GONE
                }
            }
            contents.addView(layout)
        }*/

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
    fun init_variable(){
        var sliding_layout = view!!.findViewById<SlidingUpPanelLayout>(R.id.sliding_layout)
        var lower_linear = view!!.findViewById<LinearLayout>(R.id.lower_linear)
        var dragView = view!!.findViewById<LinearLayout>(R.id.dragView)
        var title = view!!.findViewById<FrameLayout>(R.id.reserve_title_linear)
        var hide_button = view!!.findViewById<Button>(R.id.btn_hide)
        sliding_layout.setOnDragListener(null)
        sliding_layout.isTouchEnabled = false
        dragView.setOnClickListener(null)
        dragView.setOnDragListener(null)
        lower_linear.setOnClickListener(null)
        lower_linear.setOnDragListener(null)
        title.setOnDragListener(null)
        hide_button.setOnClickListener {
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
            dragView.visibility = View.GONE
        }
    }
}