package com.project.reservation_kcube

import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    val ENTRY_URL ="http://mwein.konkuk.ac.kr/common/user/login.do";
    val SUCCESS_LOGIN_URL = "https://mwein.konkuk.ac.kr/index.do"
    val NOTICE_URL = "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findUseInfo.do"
    val FIRST_RESERVE_URL = "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do"
    val FIND_TABLE_URL = "http://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do";
    lateinit var user_id:String
    lateinit var user_pw:String
    lateinit var mWebView:WebView
    lateinit var WebSetting:WebSettings
    val fragmentManager = supportFragmentManager;
    val fragmentTab1:FragmentTab1 = FragmentTab1()
    val fragmentTab2:FragmentTab2 = FragmentTab2()
    val fragmentTab3:FragmentTab3 = FragmentTab3();
    val fragmentTab4:FragmentTab4 = FragmentTab4()
    companion object{
        lateinit var update_time:String
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init_variable()
        init_tab()
        login()
        access()
        parsing_data()
    }
    fun init_variable(){
        var intent = intent;
        //user_id = intent.getStringExtra("user_id")
        //user_pw = intent.getStringExtra("user_pw")
        user_id = "rlat302"
        user_pw = "tjdgjs789"
        mWebView = findViewById(R.id.background_webview)
        WebSetting = mWebView.settings;
        mWebView.addJavascriptInterface(MyJavaScriptInterface(this),"android")
        mWebView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                if(url.equals(ENTRY_URL)){
                    var script = login_script(user_id,user_pw);
                    view!!.loadUrl(script);
                }
                if(url.equals(SUCCESS_LOGIN_URL)){
                    var script = access_script()
                    view!!.loadUrl(script)
                }
                if(url.equals(NOTICE_URL)){
                    view!!.loadUrl(go_reserve_tab_script())
                }
                if(url.equals(FIRST_RESERVE_URL)){
                    Log.v("aaa", select_all_script())
                    view!!.loadUrl(select_all_script())
                }
                /*if(url.equals(FIND_TABLE_URL)){
                    //view!!.loadUrl(show_button_click())
                    view!!.loadUrl(parsing_building());
                    view!!.loadUrl(parsing_date())
                    view!!.loadUrl(parsing_table())
                }*/
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.v("url",url)
            }
        }
        WebSetting.javaScriptEnabled = true;
    }
    fun init_tab(){
        var transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout, fragmentTab1).commitAllowingStateLoss()
        var bottomNavigation = findViewById<BottomNavigationView>(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(object:ItemClickListener(fragmentManager,fragmentTab1,fragmentTab2,fragmentTab3,fragmentTab4){})
    }
    fun init_adapter(){
    }
    fun login(){
        mWebView.loadUrl(ENTRY_URL)
        Toast.makeText(applicationContext,"로그인에 성공하였습니다",Toast.LENGTH_SHORT).show()
    }
    fun access(){
        mWebView.loadUrl(SUCCESS_LOGIN_URL)
        mWebView.loadUrl(NOTICE_URL)
        mWebView.loadUrl(FIRST_RESERVE_URL)
    }
    fun parsing_data(){
        mWebView.loadUrl(FIND_TABLE_URL)
        set_updatetime()
    }
    fun set_updatetime(){
        var now = System.currentTimeMillis()
        var date = Date(now)
        val sdfNow = SimpleDateFormat("HH:mm:ss")
        update_time = sdfNow.format(date)
    }
}
