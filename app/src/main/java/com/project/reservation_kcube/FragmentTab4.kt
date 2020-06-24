package com.project.reservation_kcube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.project.reservation_kcube.MainActivity.Companion.my_dept
import com.project.reservation_kcube.MainActivity.Companion.my_name
import com.project.reservation_kcube.MainActivity.Companion.my_sid

class FragmentTab4: Fragment() {
    lateinit var name:TextView
    lateinit var dept:TextView
    lateinit var sid:TextView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab4,container,false);
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init_variable()
        init()
    }
    fun init_variable(){
        name = view!!.findViewById(R.id.my_name_text)
        dept = view!!.findViewById(R.id.my_dept_text)
        sid = view!!.findViewById(R.id.my_sid_text)
    }
    fun init(){
        name.text  = my_name
        dept.text = "학과 : " + my_dept.replace(" ","")
        sid.text = "학번 : " +  my_sid.replace(" ","")
    }
}