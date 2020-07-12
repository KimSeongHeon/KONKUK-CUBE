package com.project.reservation_kcube

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.EditText


class MangeFriendDialog(var context: Context?) {
    fun callFunction() {
        val dlg = Dialog(context)
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.manage_friend_dialog)
        dlg.show()
        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        val message = dlg.findViewById(R.id.mesgase) as EditText
        val okButton = dlg.findViewById(R.id.okButton) as Button
        val cancelButton = dlg.findViewById(R.id.cancelButton) as Button
    }
}
