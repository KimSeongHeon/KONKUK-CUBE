package com.project.reservation_kcube

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface

class MyJavaScriptInterface(context:Context){
    var mcontext:Context = context;
    @JavascriptInterface
    fun get_building(value:Array<String>){
        Thread(object :Runnable{
            override fun run() {
                (mcontext as MainActivity).runOnUiThread(object :Runnable{
                    override fun run() {
                        (mcontext as MainActivity).fragmentTab1.dispay_building(value)
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
                        (mcontext as MainActivity).fragmentTab1.display_date(value)
                    }
                })
            }
        }).start()
    }
    @JavascriptInterface
    fun bye(value:String){
        Log.v("bye",value)
    }
}