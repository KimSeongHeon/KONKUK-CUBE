package com.project.reservation_kcube

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.MenuItem
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab1
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab2
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab3
import com.project.reservation_kcube.MainActivity.Companion.fragmentTab4

open class ItemClickListener(fragmentManager:FragmentManager):BottomNavigationView.OnNavigationItemSelectedListener {
    var fragmentManager = fragmentManager
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
            }
            R.id.search_item->{
                if(fragmentTab2 == null){
                    Log.v("tab2 click"," null click")
                    fragmentTab2 = FragmentTab2()
                    fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentTab2!!).commit()
                }
                if(fragmentTab1 != null) fragmentManager.beginTransaction().hide(fragmentTab1!!).commit()
                if(fragmentTab2 != null) fragmentManager.beginTransaction().show(fragmentTab2!!).commit()
                if(fragmentTab3 != null) fragmentManager.beginTransaction().hide(fragmentTab3!!).commit()
                if(fragmentTab4 != null) fragmentManager.beginTransaction().hide(fragmentTab4!!).commit()
            }
            R.id.my_info_item->{
                if(fragmentTab3 == null){
                    fragmentTab3 = FragmentTab3()
                    fragmentManager.beginTransaction().add(R.id.frameLayout,fragmentTab3!!).commit()
                }
                if(fragmentTab1 != null) fragmentManager.beginTransaction().hide(fragmentTab1!!).commit()
                if(fragmentTab2 != null) fragmentManager.beginTransaction().hide(fragmentTab2!!).commit()
                if(fragmentTab3 != null) fragmentManager.beginTransaction().show(fragmentTab3!!).commit()
                if(fragmentTab4 != null) fragmentManager.beginTransaction().hide(fragmentTab4!!).commit()
            }
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