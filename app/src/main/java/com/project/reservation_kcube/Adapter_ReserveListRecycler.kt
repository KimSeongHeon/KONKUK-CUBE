package com.project.reservation_kcube

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class Adapter_ReserveListRecycler(var data:Array<Data_ReserveInfo>)  : RecyclerView.Adapter<Adapter_ReserveListRecycler.ViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): Adapter_ReserveListRecycler.ViewHolder {
        val v = LayoutInflater.from(p0.context).inflate(R.layout.card_reserve,p0,false)
        context = p0.context
        return ViewHolder(v)    }

    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        if(data[p1].isReserve.contains("취소")){
            p0.is_reserve.setImageResource(R.drawable.cancel_reserve)
            p0.is_reserve.setBackgroundResource(R.drawable.cancel_background_border)
        }
        else if(data[p1].isReserve.contains("예약") || data[p1].isReserve.contains("완료")){
            p0.is_reserve.setImageResource(R.drawable.complete_reserve)
            p0.is_reserve.setBackgroundResource(R.drawable.selected_background_border)
        }
        else if(data[p1].isReserve.contains("승인대기")){
            p0.is_reserve.setImageResource(R.drawable.wait_reserve)
            p0.is_reserve.setBackgroundResource(R.drawable.wait_background_border)
        }
        p0.click_day.text = "예약 신청일 : " + data[p1].click_date
        p0.location.text = data[p1].location
        p0.date.text = "예약일 : " + data[p1].reserve_day
        p0.time.text=  "대여 시간 : " + data[p1].time
        if(data[p1].location.contains("공학관")){
            p0.building_img.setImageResource(R.drawable.engineering_building)
        }
        else if(data[p1].location.contains("상허")){
            p0.building_img.setImageResource(R.drawable.economics_building)
        }
        else if(data[p1].location.contains("생명과학관")){
            if(data[p1].location.contains("동물")){
                p0.building_img.setImageResource(R.drawable.animallife_building)
            }
            else{
                p0.building_img.setImageResource(R.drawable.environment_building)
            }
        }
        p0.kakao.setOnClickListener{
            Toast.makeText(context,"아직 준비중입니다.",Toast.LENGTH_SHORT).show()
        }
    }
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var is_reserve:ImageView
        var click_day: TextView
        var building_img:ImageView
        var location:TextView
        var date:TextView
        var time:TextView
        var kakao:ImageView
        init{
            is_reserve = itemView.findViewById(R.id.is_reserve_imageview)
            click_day = itemView.findViewById(R.id.click_day_text)
            building_img = itemView.findViewById(R.id.building_img)
            location = itemView.findViewById(R.id.reserve_building_text)
            date = itemView.findViewById(R.id.reserve_date_text)
            time = itemView.findViewById(R.id.reserve_time_text)
            kakao = itemView!!.findViewById(R.id.send_kakao_btn)
        }
    }

}
