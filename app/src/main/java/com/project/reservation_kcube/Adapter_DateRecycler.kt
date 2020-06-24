package com.project.reservation_kcube

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_tab1.*
import kotlinx.android.synthetic.main.tab1_content_main.*

class Adapter_DateRecycler(var data:Array<String>,var fragmentTab1: FragmentTab1)
    : RecyclerView.Adapter<Adapter_DateRecycler.ViewHolder>() {
    lateinit var context: Context
    var selectedPosition:Int = 0
    var copy_data = data
    var mSelectedItems = SparseBooleanArray(0)
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_DateRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_date, p0, false)
        context = p0.context;
        selectedPosition = (context as MainActivity).date_index;
        mSelectedItems.put(0,true)
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_DateRecycler.ViewHolder, p1: Int) {
        p0.date.text = data[p1].split(" ").get(2).removePrefix("일")
        when(p1){
            0->{
                p0.day.text = "오늘"
            }
            1->{
                p0.day.text = "내일"
            }
            else->{
                p0.day.text = data[p1].split(" ").get(3).get(1).toString()
            }
        }
        if(selectedPosition == p1){
            p0.date.setBackgroundResource(R.drawable.green_circle)
            p0.date.setTextColor(Color.parseColor("#FFFFFF"))
            (context as MainActivity).top_date_textview.text = data[p1]
        }
        else{
            p0.date.setBackgroundResource(0)
            p0.date.setTextColor(Color.parseColor("#000000"))
        }
        p0.date.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                fragmentTab1.date_recyclerview_scroll = fragmentTab1.when_layoutManager.findFirstCompletelyVisibleItemPosition()
                selectedPosition = p1
                fragmentTab1.building_adapter.selectedPosition = 0;
                fragmentTab1.building_recycler.smoothScrollToPosition(fragmentTab1.building_adapter.selectedPosition)
                (context as MainActivity).date_index = p1;
                fragmentTab1.accordian_text.visibility = View.GONE
                fragmentTab1.arcodian_recycler.visibility = View.GONE
                fragmentTab1.progressBar.visibility = View.VISIBLE
                //MainActivity.progressDialog.show()
                //(context as MainActivity).mWebView.loadUrl(update_date_script(p1.toString()))
                //https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do?paramStart=paramStart&rsvYmd=2020.08.05&buildAll=Y&_buildAll=on&_buildList[1]=on&_buildList[2]=on&_buildList[3]=on&_buildList[4]=on&_buildList[5]=on&_buildList[6]=on
                var split = data[selectedPosition].split(" ")
                var str = split[0].split("년")[0] + "." +split[1].split("월")[0] + "." + split[2].split("일")[0]
                Log.v("str",str)
                (context as MainActivity).mWebView.loadUrl("https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do?" +
                        "paramStart=paramStart&rsvYmd=${str}&buildAll=Y&_buildAll=on&_buildList[1]=on&_buildList[2]=on&_buildList[3]=on&_buildList[4]=on&_buildList[5]=on&_buildList[6]=on")
                fragmentTab1.building_adapter.notifyDataSetChanged()
                (context as MainActivity).set_updatetime()
                fragmentTab1.setUpdateTime()
                notifyDataSetChanged()
            }
        })
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var date:TextView
        var day:TextView
        init{
            date = itemView.findViewById(R.id.date_textview)
            day = itemView.findViewById(R.id.day_textview)
        }
    }

}