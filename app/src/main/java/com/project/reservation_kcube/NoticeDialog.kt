package com.project.reservation_kcube

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.TextView
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader


class NoticeDialog(var context: Context?) {
    fun callFunction() {
        val dlg = Dialog(context)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.notice_dialog)
        dlg.show()
        var notice = dlg.findViewById<TextView>(R.id.notice)
        notice.text = loadText()
    }
    fun loadText():String{
        val strBuffer = StringBuffer()
        try {
            val inputstream = FileInputStream("notice.txt")
            val reader = BufferedReader(InputStreamReader(inputstream))
            var line = ""
            while (reader.readLine() != null) {
                line = reader.readLine()
                strBuffer.append(line + "\n")
            }
            reader.close()
            inputstream.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return ""
        }

        return strBuffer.toString()
    }
}
