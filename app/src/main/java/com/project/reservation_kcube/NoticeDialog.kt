package com.project.reservation_kcube

import android.app.Dialog
import android.content.Context
import android.os.Environment
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class NoticeDialog(var context: Context?) {
    val filePath = Environment.getExternalStorageDirectory().getAbsolutePath()
    fun callFunction() {
        val dlg = Dialog(context)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.notice_dialog)
        dlg.show()
        var notice = dlg.findViewById<TextView>(R.id.notice)
        var okbutton = dlg.findViewById<Button>(R.id.notice_okButton)
        notice.text = loadText()
        okbutton.setOnClickListener {
            dlg.dismiss()
        }
    }
    fun loadText():String{
        val strBuffer = StringBuffer()
        try {
            val inputstream = context!!.resources.openRawResource(R.raw.notice)
            val reader = BufferedReader(InputStreamReader(inputstream))
            for (item in reader.readLines()){
                strBuffer.append(item)
                strBuffer.append("\n\n")
            }
            reader.close()
            inputstream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }
        Log.v("str",strBuffer.toString())
        return strBuffer.toString()
    }
}
