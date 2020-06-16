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
    val TEST_URL = "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do?paramStart=paramStart&rsvYmd=2020.01.15&buildAll=Y&_buildAll=on&_buildList[1]=on&_buildList[2]=on&_buildList[3]=on&_buildList[4]=on&_buildList[5]=on&_buildList[6]=on"
    val ENTRY_URL ="https://mwein.konkuk.ac.kr/common/user/login.do";
    val SUCCESS_LOGIN_URL = "https://mwein.konkuk.ac.kr/index.do"
    val NOTICE_URL = "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findUseInfo.do"
    val FIRST_RESERVE_URL = "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do"
    val SECOND_RESERVE_URL = "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep2.do"
    lateinit var user_id:String
    lateinit var user_pw:String
    lateinit var mWebView:WebView
    lateinit var WebSetting:WebSettings
    var date_index = 0;
    var load_check = false;
    val fragmentManager = supportFragmentManager;
    companion object{
        lateinit var update_time:String
        var fragmentTab1:FragmentTab1? = FragmentTab1()
        var fragmentTab2:FragmentTab2? = null
        var fragmentTab3:FragmentTab3? = null
        var fragmentTab4:FragmentTab4? = null
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
        user_id = intent.getStringExtra("user_id")
        user_pw = intent.getStringExtra("user_pw")
        mWebView = findViewById(R.id.background_webview)
        WebSetting = mWebView.settings;
        mWebView.addJavascriptInterface(MyJavaScriptInterface(this),"android")
        mWebView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                if(url.equals(TEST_URL)){
                    view!!.loadUrl(parsing_building())
                    view!!.loadUrl(parsing_date())
                    view!!.loadUrl(parsing_table())
                }
                if(url.equals(ENTRY_URL)){
                    var script = login_script(user_id,user_pw);
                    view!!.loadUrl(script);
                }
                if(url.equals(SUCCESS_LOGIN_URL)){
                    Log.v("asd","ENTRY_URL")
                    var script = access_script()
                    view!!.loadUrl(script)
                }
                if(url.equals(NOTICE_URL)){
                    view!!.loadUrl(go_reserve_tab_script())
                }
                if(url.equals(FIRST_RESERVE_URL)){
                    if(!load_check) {//첫번째 로드 시
                        view!!.loadUrl(select_all_script())
                        view!!.loadUrl(update_date_script("0"))
                        load_check = true;
                    }
                    else{
                        view!!.loadUrl(parsing_building())
                        view!!.loadUrl(parsing_date())
                        view!!.loadUrl(parsing_table())
                    }
                }
                if(url.equals(SECOND_RESERVE_URL)){
                    view!!.loadUrl(parsing_reserve_data())
                }
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.v("url",url);
            }
        }
        WebSetting.javaScriptEnabled = true;
    }
    fun init_tab(){
        var bottomNavigation = findViewById<BottomNavigationView>(R.id.navigationView)
        bottomNavigation.setOnNavigationItemSelectedListener(object:ItemClickListener(fragmentManager){})
        fragmentManager.beginTransaction().replace(R.id.frameLayout, fragmentTab1!!).commit()
    }
    fun login(){
        Log.v("fun login","called")
        mWebView.loadUrl(ENTRY_URL)
        Toast.makeText(applicationContext,"로그인에 성공하였습니다",Toast.LENGTH_SHORT).show()
    }
    fun access(){
        //mWebView.loadUrl(SUCCESS_LOGIN_URL)
        //mWebView.loadUrl(NOTICE_URL)
        //mWebView.loadUrl(FIRST_RESERVE_URL)
    }
    fun parsing_data(){
        //mWebView.loadUrl(FIND_TABLE_URL)
        set_updatetime()
    }
    fun set_updatetime(){
        var now = System.currentTimeMillis()
        var date = Date(now)
        val sdfNow = SimpleDateFormat("HH:mm:ss")
        update_time = sdfNow.format(date)
    }
}
