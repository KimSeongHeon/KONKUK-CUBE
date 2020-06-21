package com.project.reservation_kcube

import android.animation.ValueAnimator
import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class Adapter_ArcodianRecycler(var data:Array<Int>, var room_num_info:MutableMap<Int,HashSet<Int>>, var time_info:MutableMap<Pair<Int,Int>,ArrayList<String>>, var room_info: MutableMap<Int, Data_roomInfo>, var fragment1:FragmentTab1)
    : RecyclerView.Adapter<Adapter_ArcodianRecycler.ViewHolder>() {
    var selectedPosition = -1;
    var selectedItems = SparseBooleanArray()
    lateinit var context: Context
    lateinit var room_adapter:Adapter_RoomRecycler
    interface OnItemClickListner {
        fun OnItemClick(holder: Adapter_ArcodianRecycler.ViewHolder, view: View, data: Int, position: Int)
    }
    var ItemClickListener: OnItemClickListner? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_ArcodianRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.buildinglayout, p0, false)
        context = p0.context
        return ViewHolder(v)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: Adapter_ArcodianRecycler.ViewHolder, p1: Int) {
        p0.onBind(p1)
        Log.v("p1",p1.toString())
        p0.changeVisibillity(selectedItems.get(p1))
        p0.title.text = convertToname(data[p1]);
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var title: TextView
        var list: LinearLayout
        var rcyview:RecyclerView
        var up_down:ImageView
        init{
            title = itemView.findViewById(R.id.building_title_text)
            list = itemView.findViewById(R.id.roomlist_linearlayout)
            rcyview = itemView.findViewById(R.id.room_recyclerview)
            up_down = itemView.findViewById(R.id.up_down_imageview)
            title.setOnClickListener{
                val position = adapterPosition;
                ItemClickListener?.OnItemClick(this,it,data[position],position)
            }
        }
        override fun onClick(v: View?) {
            when(v!!.id){
                R.id.building_title_text ->{
                    if(selectedItems.get(adapterPosition)){
                        Log.d("cardview","closed")
                        selectedItems.delete(adapterPosition);
                    }else{
                        Log.d("cardview","open")
                        selectedItems.delete(selectedPosition)
                        selectedItems.put(adapterPosition,true)
                    }
                    if(selectedPosition != -1) notifyItemChanged(selectedPosition)
                    notifyItemChanged(adapterPosition)
                    selectedPosition = adapterPosition
                    Log.v("selectedPosittion",selectedPosition.toString())
                }
            }
        }
        fun onBind(position:Int){
            title.setOnClickListener(this)
            Log.v("room_num_info",room_num_info.size.toString())
            Log.v("position",data.get(position).toString())
            room_adapter = Adapter_RoomRecycler(room_num_info[data.get(position)]!!.toTypedArray(),time_info,data[position],room_info,fragment1)
            rcyview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
            rcyview.adapter = room_adapter
            list.visibility = View.VISIBLE
        }
        fun changeVisibillity(isExpanded: Boolean){
            Log.v("isExpanded",isExpanded.toString())
            val dpValue = ViewGroup.LayoutParams.WRAP_CONTENT
            val d = context.resources.displayMetrics.density
            val height = (dpValue * d).toInt()
            Log.v("dpValue",dpValue.toString())
            Log.v("d",d.toString())
            Log.v("height",height.toString())
            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            val va = if (isExpanded) ValueAnimator.ofInt(0, height) else ValueAnimator.ofInt(height, 0)
            // Animation이 실행되는 시간, n/1000초
            va.duration = 600
            va.addUpdateListener { animation ->
                // value는 height 값
                Log.v("addUpdate","LIstener")
                val value = animation.animatedValue as Int
                // imageView의 높이 변경
                list.getLayoutParams().height = value
                list.requestLayout()
                // imageView가 실제로 사라지게하는 부분
                list.setVisibility(if (isExpanded) View.VISIBLE else View.GONE)
            }
            // Animation start
            va.start()
        }
    }
}