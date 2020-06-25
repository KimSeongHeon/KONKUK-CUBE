package com.project.reservation_kcube

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.fragment.app.Fragment
import com.project.reservation_kcube.LoginActivity.Companion.auto
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab1
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab2
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab4
import com.project.reservation_kcube.MainActivity.Companion.my_dept
import com.project.reservation_kcube.MainActivity.Companion.my_name
import com.project.reservation_kcube.MainActivity.Companion.my_sid
import kotlinx.android.synthetic.main.fragment_tab4.*

class FragmentTab4: Fragment() {
    lateinit var name:TextView
    lateinit var dept:TextView
    lateinit var sid:TextView
    lateinit var togglebutton:ToggleButton
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab4,container,false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init_variable()
        init()
        init_listener()
    }
    fun init_variable(){
        name = view!!.findViewById(R.id.my_name_text)
        dept = view!!.findViewById(R.id.my_dept_text)
        sid = view!!.findViewById(R.id.my_sid_text)
        togglebutton = view!!.findViewById(R.id.auto_login_toggle)
    }
    fun init(){
        name.text  = my_name
        dept.text = "학과 : " + my_dept.replace(" ","")
        sid.text = "학번 : " +  my_sid.replace(" ","")
        if(LoginActivity.auto == 0) togglebutton.setBackgroundResource(R.drawable.toggle_off)
        else togglebutton.setBackgroundResource(R.drawable.toggle_on)
    }
    fun init_listener(){
        logout_text.setOnClickListener{
            Toast.makeText(context,"로그아웃하였습니다.",Toast.LENGTH_SHORT).show()
            LoginActivity.DB.execSQL("DELETE FROM "     + LoginActivity.TABLE_NAME + ";")
            fragmentTab1 = FragmentTab1()
            fragmentTab2 = null
            fragmentTab4 = null
            var intent = Intent(context,LoginActivity::class.java)
            startActivity(intent)
        }
        togglebutton.setOnClickListener{
            if(togglebutton.isChecked) {
                togglebutton.setBackgroundResource(R.drawable.toggle_off)
                LoginActivity.DB.execSQL("UPDATE INFO SET AUTO= " + "0")
                auto = 0
            }
            else {
                togglebutton.setBackgroundResource(R.drawable.toggle_on)
                LoginActivity.DB.execSQL("UPDATE INFO SET AUTO= " + "1")
                auto = 1
            }

        }
    }
}