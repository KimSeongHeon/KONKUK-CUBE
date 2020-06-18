package com.project.reservation_kcube

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
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
import kotlinx.android.synthetic.main.up_reserve.view.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.min


class FragmentTab1: Fragment() {
    lateinit var building_recycler:RecyclerView
    lateinit var date_recycler:RecyclerView
    lateinit var arcodian_recycler:RecyclerView
    lateinit var select_time_recycler:RecyclerView
    lateinit var select_purpose_recycler:RecyclerView
    lateinit var add_friend_recycler:RecyclerView
    lateinit var  date_adapter:Adapter_DateRecycler
    lateinit var building_adapter:Adapter_BuildingRecycler
    lateinit var arcodian_adapter:Adapter_ArcodianRecycler
    lateinit var select_time_adapter:Adapter_SelectTime
    lateinit var select_purpose_adapter:Adapter_SelectPurpose
    lateinit var add_friend_adapter:Adapter_AddFriendRecycler
    var Building_data:Array<String> = arrayOf();
    var Date_data:Array<String> = arrayOf()
    var date_recyclerview_scroll = 0;
    var when_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
    var where_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
    var arcodian_layoutManager = LinearLayoutManager(this.context,LinearLayout.VERTICAL,false)
    var select_time_layoutManager = GridLayoutManager(this.context, 2)
    var select_purpose_layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    var add_friend_layoutManager = GridLayoutManager(this.context, 2)
    var successive_time:Double = 0.0
    var select_clock = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.v("onView","crated")
        return inflater.inflate(R.layout.fragment_tab1,container,false);
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.v("onAcitivity","crated")
        super.onActivityCreated(savedInstanceState)
        building_recycler = view!!.findViewById(R.id.where_recyclerview)
        date_recycler = view!!.findViewById(R.id.when_recyclerview)
        select_time_recycler = view!!.findViewById(R.id.select_time_recycler)
        select_purpose_recycler = view!!.findViewById(R.id.select_purpose_recycler)
        add_friend_recycler = view!!.findViewById(R.id.add_friend_recycler)
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
    fun display_reserve_data(date:String,location:String,location_info:String,name:String,possible_time:String,purpose:Array<String>,userInfo:String){
        Log.v("display","reserve_data")
        var parent = view!!.findViewById<LinearLayout>(R.id.up_reserve)
        parent.name_text.text = name
        parent.cube_text.text = location
        parent.cube_info_text.text = location_info.split(',')[0]
        parent.date_text.text = date
        parent.user_info_text.text = "(1) " + userInfo.split(')')[0] + ')'
        Log.v("possible_time",possible_time)
        parent.possible_text.text = "(1) 대여가능 잔여시간 : " + (possible_time.split(":")[1].split("시간")[0].toDouble() + 0.5).toString() + "시간"
        var remain_time =  (possible_time.split(":")[1].split("시간")[0].toDouble() + 0.5)
        var time = arrayOf("0.5시간","1시간","1.5시간","2시간","2.5시간","3시간")
        select_time_layoutManager = GridLayoutManager(this.context,3)
        select_time_recycler.layoutManager = select_time_layoutManager
        select_time_adapter = Adapter_SelectTime(time,(min(remain_time,successive_time)/0.5).toInt(),this,select_clock)
        select_time_recycler.adapter = select_time_adapter
        select_time_adapter.notifyDataSetChanged()
        select_purpose_layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
        select_purpose_recycler.layoutManager = select_purpose_layoutManager
        select_purpose_adapter = Adapter_SelectPurpose(purpose)
        select_purpose_recycler.adapter = select_purpose_adapter
        select_purpose_adapter.notifyDataSetChanged()
    }
    fun display_friend(id:Array<String>,name:Array<String>){
        Log.v("id",id.size.toString())
        Log.v("name",name.get(0))
        add_friend_layoutManager = GridLayoutManager(this.context, 2)
        add_friend_recycler.layoutManager = add_friend_layoutManager
        add_friend_adapter = Adapter_AddFriendRecycler(id,name)
        add_friend_recycler.adapter = add_friend_adapter
        add_friend_adapter.notifyDataSetChanged()
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
        var parent = view!!.findViewById<LinearLayout>(R.id.up_reserve)
        var lower_linear = parent.findViewById<LinearLayout>(R.id.lower_linear)
        var title = parent.findViewById<FrameLayout>(R.id.reserve_title_linear)
        var hide_button = parent.findViewById<Button>(R.id.btn_hide)
        var search_view = parent.findViewById<android.support.v7.widget.SearchView>(R.id.search_friend_view)
        var submit_btn = parent.findViewById<Button>(R.id.submit_btn)
        sliding_layout.setOnDragListener(null)
        sliding_layout.isTouchEnabled = false
        parent.setOnClickListener(null)
        parent.setOnDragListener(null)
        lower_linear.setOnClickListener(null)
        lower_linear.setOnDragListener(null)
        title.setOnDragListener(null)
        submit_btn.setOnClickListener {
            (context as MainActivity).mWebView.loadUrl(final_submit_script())
        }
        hide_button.setOnClickListener {
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
            parent.visibility = View.GONE
            (context as MainActivity).mWebView.loadUrl((context as MainActivity).FIRST_RESERVE_URL)
        }
        search_view.setOnClickListener {
            search_view.setIconified(false);
        }
        search_view.setOnSearchClickListener {
            (context as MainActivity).mWebView.loadUrl(open_friend_script())
        }
        search_view.setOnQueryTextListener(object:android.support.v7.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                Log.v("asf",p0)
                var query = ""
                if(p0 == null) query = ""
                else query = p0
                (context as MainActivity).mWebView.loadUrl(Search_friend_script(query))
                search_view.setQuery("",false)
                search_view.clearFocus()
                search_view.onActionViewCollapsed()
                return true;
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })

    }
}