package com.project.reservation_kcube

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab1
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab2
import com.project.reservation_kcube.MainActivity.Companion.my_dept
import com.project.reservation_kcube.MainActivity.Companion.my_name
import com.project.reservation_kcube.MainActivity.Companion.my_sid

class MyJavaScriptInterface(context:Context){
    var mcontext:Context = context;
    @JavascriptInterface
    fun get_my_name(name:String,dept:String,id:String){
        Log.v("show_your_info",name+dept+id);
        my_name = name
        my_dept = dept
        my_sid = id
    }
    @JavascriptInterface
    fun get_building(value:Array<String>){
        Thread(object :Runnable{
            override fun run() {
                (mcontext as MainActivity).runOnUiThread(object :Runnable{
                    override fun run() {
                        fragmentTab1!!.dispay_building(value)
                    }
                })
            }
        }).start()
        Log.v("hello",value.size.toString())
    }
    @JavascriptInterface
    fun get_date(value:Array<String>){
        Log.v("get_date",value.size.toString())
        Log.v("get_date",value[0].toString())
        Thread(object :Runnable{
            override fun run() {
                (mcontext as MainActivity).runOnUiThread(object :Runnable{
                    override fun run() {
                        fragmentTab1!!.display_date(value)
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun get_table(value:Array<String>){
        Log.v("get_table",value.size.toString())
        Thread(object :Runnable{
            override fun run() {
                (mcontext as MainActivity).runOnUiThread(object :Runnable{
                    override fun run() {
                        fragmentTab1!!.display_table(value)
                    }
                })
            }
        }).start()

    }
    @JavascriptInterface
    fun show_reserve_data(date:String,location:String,location_info:String,name:String,possible_time:String,purpose:Array<String>,userInfo:String){
        Log.v("show_Reserve_data",date + location + location_info + name)
        Thread(object:Runnable{
            override fun run(){
                (mcontext as MainActivity).runOnUiThread(object : Runnable{
                    override fun run(){
                        fragmentTab1!!.display_reserve_data(date,location,location_info,name,possible_time,purpose,userInfo)
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun show_alert(str:String){
        Log.v("str",str)
        Thread(object:Runnable{
            override fun run(){
                (mcontext as MainActivity).runOnUiThread(object : Runnable{
                    override fun run(){
                        Toast.makeText(mcontext,str,Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun select_friends_script(id:Array<String>,name:Array<String>){
        Thread(object:Runnable{
            override fun run(){
                (mcontext as MainActivity).runOnUiThread(object : Runnable{
                    override fun run(){
                        fragmentTab1!!.display_friend(id,name);
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun print_error(error:String){
        Log.v("error",error)
        Thread(object:Runnable{
            override fun run(){
                (mcontext as MainActivity).runOnUiThread(object : Runnable{
                    override fun run(){
                        Toast.makeText(mcontext,error,Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun print_success(str:String){
        Log.v("str",str)
        Thread(object:Runnable{
            override fun run(){
                (mcontext as MainActivity).runOnUiThread(object : Runnable{
                    override fun run(){
                        Toast.makeText(mcontext,str,Toast.LENGTH_SHORT).show()
                        fragmentTab1!!.view!!.findViewById<LinearLayout>(R.id.up_reserve).findViewById<ImageView>(R.id.btn_hide).performClick()
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun print_reserve(str:Array<String>) {
        Log.v("str", str[0])
        Thread(object : Runnable {
            override fun run() {
                (mcontext as MainActivity).runOnUiThread(object : Runnable {
                    override fun run() {
                        fragmentTab2!!.display_reserveList(str)
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun save_friend(arr:Array<String>){
        Log.v("arr.size",arr.size.toString())
        fragmentTab1!!.save_frienddata(arr)
    }
    @JavascriptInterface
    fun say_load(query:String){
        Log.v("query",query)
        (mcontext as MainActivity).mWebView.post(object :Runnable{
            override fun run() {
                (mcontext as MainActivity).mWebView.loadUrl(Search_friend_script(query))
            }
        })
    }
}