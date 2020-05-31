package com.project.reservation_kcube

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout


class building_layout : LinearLayout {

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }
    constructor(context: Context) : super(context) {
        init(context)
    }
    private fun init(context: Context) {
        val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.buildinglayout,this, true)
    }
}