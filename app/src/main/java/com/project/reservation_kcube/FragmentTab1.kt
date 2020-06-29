package com.project.reservation_kcube

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.reservation_kcube.LoginActivity.Companion.DB
import com.project.reservation_kcube.LoginActivity.Companion.TABLE_NAME2
import com.project.reservation_kcube.MainActivity.Companion.progressDialog
import com.project.reservation_kcube.MainActivity.Companion.update_time
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_tab1.*
import kotlinx.android.synthetic.main.tab1_content_main.*
import kotlinx.android.synthetic.main.up_reserve.view.*
import org.json.JSONArray
import org.json.JSONObject
import kotlin.math.min


class FragmentTab1: Fragment() {
    lateinit var building_recycler: RecyclerView
    lateinit var date_recycler:RecyclerView
    lateinit var arcodian_recycler:RecyclerView
    lateinit var select_time_recycler:RecyclerView
    lateinit var select_purpose_recycler:RecyclerView
    lateinit var add_friend_recycler:RecyclerView
    lateinit var show_friend_recycler:RecyclerView
    lateinit var  date_adapter:Adapter_DateRecycler
    lateinit var building_adapter:Adapter_BuildingRecycler
    lateinit var arcodian_adapter:Adapter_ArcodianRecycler
    lateinit var select_time_adapter:Adapter_SelectTime
    lateinit var select_purpose_adapter:Adapter_SelectPurpose
    lateinit var add_friend_adapter:Adapter_AddFriendRecycler
    lateinit var show_friend_adapter:Adapter_ShowFriendRecycler
    lateinit var progressBar:ProgressBar
    var Building_data:Array<String> = arrayOf();
    var Date_data:Array<String> = arrayOf()
    var date_recyclerview_scroll = 0;
    var when_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
    var where_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
    var arcodian_layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
    var select_time_layoutManager = GridLayoutManager(this.context, 2)
    var select_purpose_layoutManager = LinearLayoutManager(this.context,LinearLayoutManager.HORIZONTAL,false)
    var add_friend_layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
    var show_friend_layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
    var successive_time:Double = 0.0
    var possible_my_time = 0.0
    var select_clock = ""
    var building_info = HashSet<Int>()
    var room_num_info = mutableMapOf<Int,HashSet<Int>>()
    var time_info = mutableMapOf<Pair<Int,Int>,ArrayList<String>>()
    var room_info = mutableMapOf<Int,Data_roomInfo>()
    var friend_info = arrayListOf<Data_FriendInfo>()
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
        var parent = view!!.findViewById<View>(R.id.include_view)
        add_friend_recycler = view!!.findViewById(R.id.add_friend_recycler)
        arcodian_recycler = parent.findViewById(R.id.acordian_recyclerview)
        progressBar = parent.findViewById(R.id.loading_progressBar)
        show_friend_recycler = view!!.findViewById(R.id.friend_favorite_rcyview)
        Log.v("building data",Building_data.size.toString())
        Log.v("date_data",Date_data.size.toString())
        if(Building_data.size != 0) dispay_building(Building_data)
        if(Date_data.size != 0) display_date(Date_data)
        load_friendData()
        init_variable()
        //setToolbar()
    }
    fun load_friendData(){
        friend_info.clear()
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
    fun dispay_building(value:Array<String>){
        where_layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL,false)
        building_recycler.layoutManager = where_layoutManager
        building_adapter = Adapter_BuildingRecycler(value,this)
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
        progressDialog.dismiss()
        accordian_text.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        arcodian_recycler.visibility = View.GONE
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
        building_info = HashSet<Int>()
        room_num_info = mutableMapOf<Int,HashSet<Int>>()
        time_info = mutableMapOf<Pair<Int,Int>,ArrayList<String>>()
        room_info = mutableMapOf<Int,Data_roomInfo>()
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
            if(room_info[room_num] == null) room_info.put(room_num, Data_roomInfo(jsonObject.getString("userMin").toInt(),jsonObject.getString("acceptanceNmpr").toString()))
            else room_info[room_num] = Data_roomInfo(jsonObject.getString("userMin").toInt(),jsonObject.getString("acceptanceNmpr"))
        }
        Log.v("building",building_info.size.toString())
        if(building_info.size == 0){
            arcodian_recycler.visibility = View.GONE
            accordian_text.visibility = View.VISIBLE
        }
        else{
            accordian_text.visibility = View.GONE
            arcodian_recycler.visibility = View.VISIBLE
            arcodian_layoutManager =  LinearLayoutManager(this.context, RecyclerView.VERTICAL,false)
            arcodian_adapter = Adapter_ArcodianRecycler(building_info.toTypedArray(),room_num_info,time_info,room_info,this)
            arcodian_recycler.layoutManager = arcodian_layoutManager
            arcodian_recycler.adapter = arcodian_adapter
            arcodian_adapter.notifyDataSetChanged()
        }
        progressBar.visibility = View.GONE
        //arcodian_recycler.visibility = View.VISIBLE
        setUpdateTime()
    }
    fun display_reserve_data(date:String,location:String,location_info:String,name:String,possible_time:String,purpose:Array<String>,userInfo:String){
        Log.v("display","reserve_data")
        var parent = view!!.findViewById<LinearLayout>(R.id.up_reserve)
        parent.name_text.text = name
        parent.cube_text.text = location
        parent.cube_info_text.text = location_info.split(',')[0]
        parent.date_text.text = date
        parent.user_info_text.text = "(1) " + userInfo.split(')')[0] + ')'
        possible_my_time = (possible_time.split(":")[1].split("시간")[0].toDouble())
        parent.possible_text.text = "(1) 대여가능 잔여시간 : " + (possible_my_time).toString() + "시간"
        if(possible_my_time == 0.0){
            parent.findViewById<TextView>(R.id.from_time_to_time_text).visibility = View.INVISIBLE
            parent.findViewById<TextView>(R.id.impossible_text).visibility = View.VISIBLE
            parent.findViewById<RecyclerView>(R.id.select_time_recycler).visibility = View.GONE
        }
        else{
            parent.findViewById<TextView>(R.id.from_time_to_time_text).visibility = View.VISIBLE
            parent.findViewById<TextView>(R.id.impossible_text).visibility = View.GONE
            parent.findViewById<RecyclerView>(R.id.select_time_recycler).visibility = View.VISIBLE
        }
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
        Log.v("friend_info",friend_info.size.toString())
        load_friendData()
        show_friend_layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
        show_friend_recycler.layoutManager = show_friend_layoutManager
        show_friend_adapter = Adapter_ShowFriendRecycler(friend_info.toTypedArray())
        show_friend_recycler.adapter = show_friend_adapter
        show_friend_adapter.notifyDataSetChanged()
    }
    fun display_friend(id:Array<String>,name:Array<String>){
        add_friend_layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)
        add_friend_recycler.layoutManager = add_friend_layoutManager
        add_friend_adapter = Adapter_AddFriendRecycler(id,name)
        add_friend_recycler.adapter = add_friend_adapter
        add_friend_adapter.notifyDataSetChanged()
    }

    fun save_frienddata(arr:Array<String>){
        Log.v("size",arr.size.toString())
        for(i in 0 until arr.size){
            Log.v("asf",arr[i] + add_friend_adapter.ret[i])
            DB.execSQL("INSERT OR IGNORE INTO "+ TABLE_NAME2 + "(ID,S_ID,NAME,date) VALUES"+"(\"${LoginActivity.id}\",\"${arr[i]}\",\"${add_friend_adapter.ret[i]}\",CURRENT_TIMESTAMP)" ) //없을 경우
            DB.execSQL("UPDATE " + TABLE_NAME2 + " set date = CURRENT_TIMESTAMP " + "where id = \"${LoginActivity.id}\" and s_id = \"${arr[i]}\"") //있을 경우
        }
        load_friendData()
    }
    /*fun setToolbar(){
        var toolbar = activity!!.findViewById<Toolbar>(R.id.title_toolbar)
        var activity = activity as MainActivity
        activity.setSupportActionBar(toolbar)
        activity!!.findViewById<AppBarLayout>(R.id.top_date_Appbarlayout).addOnOffsetChangedListener(object:AppBarLayout.OnOffsetChangedListener{
            override fun onOffsetChanged(p0: AppBarLayout?, p1: Int) {
                var params:WindowManager.LayoutParams = activity!!.window.attributes
            }
        })
    }*/
    fun setUpdateTime(){
        updtetime_text.text = update_time + " 업데이트 됨"
    }
    fun init_variable(){
        var sliding_layout = view!!.findViewById<SlidingUpPanelLayout>(R.id.sliding_layout)
        var parent = view!!.findViewById<LinearLayout>(R.id.up_reserve)
        var lower_linear = parent.findViewById<LinearLayout>(R.id.lower_linear)
        var title = parent.findViewById<FrameLayout>(R.id.reserve_title_linear)
        var hide_button = parent.findViewById<ImageView>(R.id.btn_hide)
        var search_view = parent.findViewById<SearchView>(R.id.search_friend_view)
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
            var imm = this.context!!.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(search_view.windowToken,0)
            search_view.setQuery("",false)
            parent.findViewById<ScrollView>(R.id.scrollView).smoothScrollTo(0,0)
            add_friend_adapter = Adapter_AddFriendRecycler(arrayOf(),arrayOf())
            add_friend_recycler.adapter = add_friend_adapter //초기화
            add_friend_adapter.notifyDataSetChanged()
            sliding_layout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
            arcodian_recycler.visibility = View.GONE
            accordian_text.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            when_recyclerview.setBackgroundColor(Color.WHITE)
            (context as MainActivity).mWebView.loadUrl((context as MainActivity).FIRST_RESERVE_URL)
        }
        search_view.setOnClickListener {
            search_view.setIconified(false);
        }
        search_view.setOnSearchClickListener {
            (context as MainActivity).mWebView.loadUrl(open_friend_script())
        }
        search_view.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
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