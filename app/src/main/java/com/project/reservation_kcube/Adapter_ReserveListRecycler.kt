package com.project.reservation_kcube

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakao.kakaolink.v2.KakaoLinkResponse
import com.kakao.kakaolink.v2.KakaoLinkService
import com.kakao.message.template.*
import com.kakao.network.ErrorResult
import com.kakao.network.callback.ResponseCallback

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
            kakaoLink(p1)
        }
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
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
    fun kakaoLink(p1:Int) {
        var url = "";
        if(data[p1].location.contains("공학관")) url = "https://assets.kung.kr/buildings/engineering.png"
        else if(data[p1].location.contains("상허")) url = "https://assets.kung.kr/buildings/economics.png"
        else if(data[p1].location.contains("생명과학관")){
            if(data[p1].location.contains("동물")) url = "https://assets.kung.kr/buildings/animal-life.png"
            else url = "https://assets.kung.kr/buildings/environment.png"
        }
        val params = FeedTemplate
            .newBuilder(
                ContentObject.newBuilder(
                    data[p1].location,
                    url,
                    LinkObject.newBuilder().setWebUrl("https://developers.kakao.com")
                        .setMobileWebUrl("https://developers.kakao.com").build()
                )
                    .setDescrption("날짜 : " + data[p1].reserve_day + "\n" + "예약 시간 : " + data[p1].time)
                    .build()
            )
            .setSocial(
                SocialObject.newBuilder().setLikeCount(10).setCommentCount(20)
                    .setSharedCount(30).setViewCount(40).build()
            )
            .addButton(
                ButtonObject(
                    "웹에서 보기",
                    LinkObject.newBuilder().setWebUrl("https://wein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveList2.do").setMobileWebUrl(
                        "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveList2.do?paramStart=paramStart"
                    ).build()
                )
            )
            .addButton(
                ButtonObject(
                    "앱에서 보기", LinkObject.newBuilder()
                        .setWebUrl("'https://seongheon.kim")
                        .setMobileWebUrl("https://seongheon.kim")
                        .setAndroidExecutionParams("key1=value1")
                        .setIosExecutionParams("key1=value1")
                        .build()
                )
            )
            .build()
        val serverCallbackArgs: MutableMap<String, String> =
            HashMap()
        serverCallbackArgs["user_id"] = "\${current_user_id}"
        serverCallbackArgs["product_id"] = "\${shared_product_id}"

        KakaoLinkService.getInstance().sendDefault(
            context,
            params,
            serverCallbackArgs,
            object : ResponseCallback<KakaoLinkResponse?>() {
                override fun onFailure(errorResult: ErrorResult) {
                    Log.e("error",errorResult.toString())
                }

                override fun onSuccess(result: KakaoLinkResponse?) { // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                }
            })
    }

}
