package com.project.reservation_kcube

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class Adapter_SelectTime(var data:Array<String>,var success:Int,var fragment: FragmentTab1,var starttime:String)
    : RecyclerView.Adapter<Adapter_SelectTime.ViewHolder>() {
    var selectedPosition = 0;
    lateinit var context:Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_SelectTime.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_successive_time,p0,false)
        context = p0.context
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    fun next_time():String{
        var ret:String = ""
        var trans = SimpleDateFormat("HH:mm")
        var date = trans.parse(starttime)
        var cal = Calendar.getInstance()
        cal.time = date
        cal.add(Calendar.MINUTE,(selectedPosition+1) * 30)
        ret = trans.format(cal.time)
        return ret
    }
    override fun onBindViewHolder(p0: Adapter_SelectTime.ViewHolder, p1: Int) {
        if(data[p1].length == 5) p0.time.text = data[p1]
        else p0.time.text = " " + data[p1] + "  "
        if(selectedPosition == -1) fragment.view!!.findViewById<TextView>(R.id.from_time_to_time_text).text = starttime + " ~ "
        else fragment.view!!.findViewById<TextView>(R.id.from_time_to_time_text).text = starttime + " ~ " + next_time()
        Log.v("success",success.toString())
        Log.v("p1",p1.toString())
        if(selectedPosition == p1 && p1<success){
            p0.time.setTextColor(Color.DKGRAY)
            p0.time.setTypeface(null, Typeface.BOLD)
            p0.linear.setBackgroundResource(R.drawable.selected_background_border)
        }
        else if(selectedPosition != p1 && p1<success){
            p0.time.setTextColor(Color.DKGRAY)
            p0.time.setTypeface(null, Typeface.NORMAL)
            p0.linear.setBackgroundResource(R.drawable.notselected_background_border)
        }
        else if(p1>=success){
            p0.time.setTextColor(Color.LTGRAY)
        }
        p0.linear.setOnClickListener {
            if(p1 < success){
                Log.v("fasf",(fragment.view!!.findViewById<TextView>(R.id.possible_text).text.toString().split(":")[1].split("시간")[0].toDouble()).toString())
                var script = select_time_script(p1)
                (context as MainActivity).mWebView.loadUrl(script)
                selectedPosition = p1
                fragment.view!!.findViewById<TextView>(R.id.possible_text).text =
                    "(1) " + "대여가능 잔여시간 : " +
                            (fragment.possible_my_time - (0.5 * selectedPosition)).toString() +"시간"
                notifyDataSetChanged()
            }
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var linear: LinearLayout
        var time:TextView
        init{
            linear = itemView.findViewById(R.id.successive_linear)
            time = itemView.findViewById(R.id.successive_text)
        }
    }

}
