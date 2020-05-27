package com.project.reservation_kcube

import android.support.design.widget.BottomNavigationView
import android.support.v4.app.FragmentManager
import android.view.MenuItem

open class ItemClickListener(fragmentManager:FragmentManager, tab1:FragmentTab1, tab2:FragmentTab2, tab3:FragmentTab3, tab4:FragmentTab4):BottomNavigationView.OnNavigationItemSelectedListener {
    var fragmentManager = fragmentManager
    var tab1 = tab1;
    var tab2 = tab2;
    var tab3 = tab3
    var tab4 = tab4
    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        var transaction = fragmentManager.beginTransaction()
        when(p0.itemId){
            R.id.reserve_item->{
                transaction.replace(R.id.frameLayout,tab1).commitAllowingStateLoss()
            }
            R.id.search_item->{
                transaction.replace(R.id.frameLayout,tab2).commitAllowingStateLoss()
            }
            R.id.my_info_item->{
                transaction.replace(R.id.frameLayout,tab3).commitAllowingStateLoss()
            }
            R.id.report_item->{
                transaction.replace(R.id.frameLayout,tab4).commitAllowingStateLoss()
            }
        }
        return true;
    }
}