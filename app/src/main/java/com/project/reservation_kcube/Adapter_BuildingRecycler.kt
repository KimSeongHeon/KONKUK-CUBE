package com.project.reservation_kcube

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab1
import kotlinx.android.synthetic.main.tab1_content_main.*

class Adapter_BuildingRecycler(var data:Array<String>,fragmentTab1: FragmentTab1)
    :RecyclerView.Adapter<Adapter_BuildingRecycler.ViewHolder>() {
    lateinit var context: Context
    lateinit var accordian_adapter:Adapter_ArcodianRecycler
    var selectedPosition = 0;
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_BuildingRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_building, p0, false)
        context = p0.context;
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_BuildingRecycler.ViewHolder, p1: Int) {
        p0.description.text = data[p1]
        if(data[p1].contains("공학관")){
            p0.icon.setImageResource(R.drawable.engineering_building)
        }
        else if(data[p1].contains("상허")){
            p0.icon.setImageResource(R.drawable.economics_building)
        }
        else if(data[p1].contains("생명과학관")){
            if(data[p1].contains("동물")){
                p0.icon.setImageResource(R.drawable.animallife_building)
            }
            else{
                p0.icon.setImageResource(R.drawable.environment_building)
            }
        }
        else if(data[p1].contains("전체")){
            p0.icon.setImageResource(R.drawable.all_building)
        }
        if(selectedPosition == p1){
            p0.description.setTypeface(null,Typeface.BOLD)
            p0.linear.setBackgroundResource(R.drawable.selected_background_border)
        }
        else{
            p0.description.setTypeface(null,Typeface.NORMAL)
            p0.linear.setBackgroundResource(R.drawable.notselected_background_border)
        }
        p0.linear.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if(!fragmentTab1!!.room_num_info.containsKey(converNametoNum(data[p1]))){
                    fragmentTab1!!.arcodian_recycler.visibility = View.GONE
                    fragmentTab1!!.accordian_text.visibility = View.VISIBLE
                }
                else{
                    fragmentTab1!!.arcodian_recycler.visibility = View.VISIBLE
                    fragmentTab1!!.accordian_text.visibility = View.GONE
                    fragmentTab1!!.arcodian_adapter = Adapter_ArcodianRecycler(arrayOf(converNametoNum(data[p1])),
                        fragmentTab1!!.room_num_info, fragmentTab1!!.time_info, fragmentTab1!!.room_info, fragmentTab1!!)
                    fragmentTab1!!.arcodian_recycler.adapter = fragmentTab1!!.arcodian_adapter //초기화
                    fragmentTab1!!.arcodian_adapter.notifyDataSetChanged()
                }
                selectedPosition = p1
                notifyDataSetChanged()
            }
        })
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var linear:LinearLayout
        var icon:ImageView
        var description:TextView
        init{
            linear = itemView.findViewById(R.id.all_linear)
            icon = itemView.findViewById(R.id.building_icon)
            description = itemView.findViewById(R.id.building_description)
        }
    }

}