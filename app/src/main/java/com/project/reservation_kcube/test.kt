package com.project.reservation_kcube

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_test.*

class test : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        var paint = Paint()
        paint.color = Color.BLACK
        paint.alpha = 0;
        abcd.setBackgroundColor(paint.color)
    }

    override fun onResume() {
        super.onResume()
    }
    class person(function: () -> Boolean) {
        var name = ""
        var age = ""
        var address = ""
    }
}
