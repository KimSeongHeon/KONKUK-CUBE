package com.project.reservation_kcube

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class FragmentTab2: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.v("onAcitivity2","created")
        return inflater.inflate(R.layout.fragment_tab2,container,false);
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.v("onAcitivity2","createdActivity")
        super.onActivityCreated(savedInstanceState)
    }
}