package com.project.reservation_kcube
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.sql.SQLException


class LoginActivity : AppCompatActivity() {
    val ENTRY_URL ="https://mwein.konkuk.ac.kr/common/user/login.do?rtnUrl=b58d750fbf5545f5f4baae3e7ae341ede04e949dfc43bbbdb06e08b6703dbf1a333241441b84e41988376ce5965bd88885ee223f45da2f3072ba774883c6cfcc";
    lateinit var editID: EditText
    lateinit var editPW:EditText
    lateinit var loginButton: Button
    lateinit var mWebView: WebView
    lateinit var WebSetting:WebSettings
    lateinit var progressDialog:ProgressDialog
    companion object{
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "UserInfo"
        val TABLE_NAME = "info"
        val TABLE_NAME2 = "FRIEND"
        lateinit var DB:SQLiteDatabase
        lateinit var id:String
        lateinit var pw:String
        var auto = 1;
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init_variable()
        init_listener()
        init_DB()
    }
    fun init_DB(){
        DB = this.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null)
        try{
            DB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (ID VARCHAR(20),PW VARCHAR(50),AUTO NUMBER(1));");
            DB.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 +
                    " (ID VARCHAR(20),S_ID VARCHAR(20),NAME VARCHAR(50),date DATETIME,UNIQUE(ID,S_ID))" +
                    ";");
            var ReadDB:SQLiteDatabase = this.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE,null)
            var c = ReadDB.rawQuery("SELECT * FROM " + TABLE_NAME,null)
            if(c.count == 0){
                Log.v("데이터 없음","not data")
            }
            else{
                c.moveToFirst()
                Log.v("데이터 있음","have data")
                id = c.getString(c.getColumnIndex("ID"))
                pw = c.getString(c.getColumnIndex("PW"))
                auto = c.getInt(c.getColumnIndex("AUTO"))
                if(auto == 1){
                    editID.setText(id)
                    editPW.setText(pw)
                    loginButton.performClick()
                    return
                }
            }
        }catch(e: SQLException){
            Log.e("sql error",e.toString())
        }
    }
    fun init_variable(){
        editID = findViewById<EditText>(R.id.id_edittext)
        editPW = findViewById<EditText>(R.id.pw_edittext)
        loginButton = findViewById(R.id.login_button)
        mWebView = findViewById(R.id.login_webview)
        WebSetting = mWebView.settings;
        mWebView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                if(url.equals(ENTRY_URL)){
                    var script = login_script(editID.text.toString(),editPW.text.toString())
                    view!!.loadUrl(script);
                }
            }
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                if(url.toString().contains("findCubeResveStep1.do")){
                    try{
                        //기존의 데이터를 지움
                        DB.execSQL("DELETE FROM " +  TABLE_NAME + ";")
                        //새로운 id값을 저장하자
                        DB.execSQL("INSERT INTO " + TABLE_NAME + " VALUES(\'"
                        + editID.text.toString()+ "\',\'"+ editPW.text.toString() + "\',"+ auto +");" )
                    }catch (se: SQLiteException){
                        Log.e("login sql error",se.toString())
                    }
                    var intent = Intent(applicationContext,MainActivity::class.java);
                    intent.putExtra("user_id",editID.text.toString());
                    intent.putExtra("user_pw",editPW.text.toString());
                    startActivity(intent);
                    id = editID.text.toString()
                    pw = editPW.text.toString()
                    progressDialog.dismiss()
                }
                else if(url.toString() == "https://mwein.konkuk.ac.kr/common/user/login.do"){
                    Toast.makeText(applicationContext,"로그인 정보가 일치하지 않습니다.",Toast.LENGTH_SHORT).show();
                    editID.text.clear()
                    editPW.text.clear()
                    editID.requestFocus()
                    progressDialog.dismiss()
                }
            }

            override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                Toast.makeText(applicationContext,"인터넷 연결 상태를 확인해주세요",Toast.LENGTH_SHORT).show()
                progressDialog.dismiss()
            }
        }
        WebSetting.javaScriptEnabled = true;
    }
    fun init_listener(){
        loginButton.setOnClickListener {
            progressDialog = ProgressDialog(this)
            progressDialog.setMessage("로그인 중입니다.")
            progressDialog.setCancelable(false)
            progressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Horizontal)
            progressDialog.show()
            mWebView.loadUrl(ENTRY_URL)
            var imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(editID.windowToken,0)
            imm.hideSoftInputFromWindow(editPW.windowToken,0)
        }
    }
}
