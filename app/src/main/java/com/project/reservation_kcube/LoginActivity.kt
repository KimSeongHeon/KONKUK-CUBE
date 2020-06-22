package com.project.reservation_kcube
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginActivity : AppCompatActivity() {
    val ENTRY_URL ="https://mwein.konkuk.ac.kr/common/user/login.do?rtnUrl=b58d750fbf5545f5f4baae3e7ae341ede04e949dfc43bbbdb06e08b6703dbf1a333241441b84e41988376ce5965bd88885ee223f45da2f3072ba774883c6cfcc";
    lateinit var editID: EditText
    lateinit var editPW:EditText
    lateinit var loginButton: Button
    lateinit var mWebView: WebView
    lateinit var WebSetting:WebSettings
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init_variable()
        init_listener()
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
                    var intent = Intent(applicationContext,MainActivity::class.java);
                    intent.putExtra("user_id",editID.text.toString());
                    intent.putExtra("user_pw",editPW.text.toString());
                    startActivity(intent);
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
