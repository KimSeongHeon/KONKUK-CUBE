package com.project.reservation_kcube

fun convertToname(num:Int):String{
    var ret = "";
    when(num){
        8 -> ret =  "생명과학관 Startup Ground";
        9 -> ret =  " 생명과학관 BIO Factory"
    }
    return ret;
}