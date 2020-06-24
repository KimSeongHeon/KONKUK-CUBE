package com.project.reservation_kcube

import android.content.Context
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab1
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab2
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab3
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab4
import kotlinx.android.synthetic.main.tab1_content_main.*

open class ItemClickListener(fragmentManager: FragmentManager, context:Context): BottomNavigationView.OnNavigationItemSelectedListener {
    var fragmentManager = fragmentManager
    var context = context
    var mainActivity = (context as MainActivity)
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        when(p0.itemId){
            R.id.reserve_item->{
                if(fragmentTab1 == null){
                    fragmentTab1 = FragmentTab1()
                    fragmentManager.beginTransaction().add(R.id.frameLayout, fragmentTab1!!).commit()
                }
                if(fragmentTab1 != null) fragmentManager.beginTransaction().show(fragmentTab1!!).commit()
                if(fragmentTab2 != null) fragmentManager.beginTransaction().hide(fragmentTab2!!).commit()
                if(fragmentTab3 != null) fragmentManager.beginTransaction().hide(fragmentTab3!!).commit()
                if(fragmentTab4 != null) fragmentManager.beginTransaction().hide(fragmentTab4!!).commit()
                mainActivity.load_check = false
                mainActivity.tab_check = fragmentTab1!!.date_adapter.selectedPosition
                //fragmentTab1!!.btn_hide.performClick()
                var split = fragmentTab1!!.date_adapter.copy_data[fragmentTab1!!.date_adapter.selectedPosition].split(" ")
                var str = split[0].split("년")[0] + "." +split[1].split("월")[0] + "." + split[2].split("일")[0]
                Log.v("str",str)
                mainActivity.mWebView.loadUrl("https://mwein.konkuk.ac.kr/ptfol/cmnt/cube/findCubeResveStep1.do?" +
                        "paramStart=paramStart&rsvYmd=${str}&buildAll=Y&_buildAll=on&_buildList[1]=on&_buildList[2]=on&_buildList[3]=on&_buildList[4]=on&_buildList[5]=on&_buildList[6]=on")
                fragmentTab1!!.arcodian_recycler.visibility = View.GONE
                fragmentTab1!!.progressBar.visibility = View.VISIBLE
                fragmentTab1!!.accordian_text.visibility = View.GONE
            }
            R.id.reserve_list_item->{
                if(fragmentTab2 == null){
                    fragmentTab2 = FragmentTab2()
                    fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentTab2!!).commit()
                }
                if(fragmentTab1 != null) fragmentManager.beginTransaction().hide(fragmentTab1!!).commit()
                if(fragmentTab2 != null) fragmentManager.beginTransaction().show(fragmentTab2!!).commit()
                if(fragmentTab3 != null) fragmentManager.beginTransaction().hide(fragmentTab3!!).commit()
                if(fragmentTab4 != null) fragmentManager.beginTransaction().hide(fragmentTab4!!).commit()
                mainActivity.load_check2 = false
                mainActivity.mWebView2.loadUrl(mainActivity.FIND_CUBE_RESERVE_LIST_URL)
                if(fragmentTab2!!.load){
                    fragmentTab2!!.reserve_list_rcyview.visibility = View.GONE
                    fragmentTab2!!.progressBar.visibility = View.VISIBLE
                }
                else{
                    //progressDialog.show()
                }
            }
            /*R.id.search_item->{
                if(fragmentTab3 == null){
                    fragmentTab3 = FragmentTab3()
                    fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentTab3!!).commit()
                }
                if(fragmentTab1 != null) fragmentManager.beginTransaction().hide(fragmentTab1!!).commit()
                if(fragmentTab2 != null) fragmentManager.beginTransaction().hide(fragmentTab2!!).commit()
                if(fragmentTab3 != null) fragmentManager.beginTransaction().show(fragmentTab3!!).commit()
                if(fragmentTab4 != null) fragmentManager.beginTransaction().hide(fragmentTab4!!).commit()
            }*/
            R.id.report_item->{
                if(fragmentTab4 == null){
                    fragmentTab4 = FragmentTab4()
                    fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentTab4!!).commit()
                }
                if(fragmentTab1 != null) fragmentManager.beginTransaction().hide(fragmentTab1!!).commit()
                if(fragmentTab2 != null) fragmentManager.beginTransaction().hide(fragmentTab2!!).commit()
                if(fragmentTab3 != null) fragmentManager.beginTransaction().hide(fragmentTab3!!).commit()
                if(fragmentTab4 != null) fragmentManager.beginTransaction().show(fragmentTab4!!).commit()
            }
        }
        return true;
    }
}