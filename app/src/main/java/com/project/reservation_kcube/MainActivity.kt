package com.project.reservation_kcube

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    val ENTRY_URL ="https://mwein.konkuk.ac.kr/common/user/login.do?rtnUrl=b58d750fbf5545f5f4baae3e7ae341ede04e949dfc43bbbdb06e08b6703dbf1a333241441b84e41988376ce5965bd888d74f5d50831f0ca83fa3e9f0fce1a911f8dd5aae9b92896cf1f0d04192324c6b74558d1061dbba81fde92d24f92f9f29ddac597fb6e379a6126fd3efced0cc6f06f525bc009bb711abcbe4b30c5f19772dc9bae76065beef1cf442a0edde98dc98efc87783c405374e10d9ee30060a08949fdf09259cab03647325c5d8300efe45bdc038285774d6ffb724d379fd68dc1cbc880dbb9e803db6232157fbc67b44";
    val SUCCESS_LOGIN_URL = "https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do?paramStart=paramStart&rsvYmd=2020.05.21&buildAll=Y&_buildAll=on&_buildList[1]=on&_buildList[2]=on&_buildList[3]=on&_buildList[4]=on&_buildList[5]=on&_buildList[6]=on";
    lateinit var user_id:String
    lateinit var user_pw:String
    lateinit var mWebView:WebView
    lateinit var WebSetting:WebSettings
    val fragmentManager = supportFragmentManager;
    val fragmentTab1:FragmentTab1 = FragmentTab1()
    val fragmentTab2:FragmentTab2 = FragmentTab2()
    val fragmentTab3:FragmentTab3 = FragmentTab3();
    val fragmentTab4:FragmentTab4 = FragmentTab4()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init_variable()
        init_tab()
        login()
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
                if(url.equals(ENTRY_URL)){
                    var script = login_script(user_id,user_pw);
                    view!!.loadUrl(script);
                }
                if(url.equals(SUCCESS_LOGIN_URL)){
                   var buildingParsingScript = parsing_building()
                    var dateParsingScript = parsing_date()
                    view!!.loadUrl(buildingParsingScript);
                    view!!.loadUrl(dateParsingScript)
                }
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
        val building_layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        //val building_adapter = Wordbook_Recycler_adapter(read_File())
        //building_recyclerview.layoutManager = building_layoutManager
        //building_recyclerview.adapter = building_adapter
    }
    fun login(){
        mWebView.loadUrl(ENTRY_URL)
        Toast.makeText(applicationContext,"로그인에 성공하였습니다",Toast.LENGTH_SHORT).show()
    }
    fun parsing_data(){
        mWebView.loadUrl(SUCCESS_LOGIN_URL)
    }
}
