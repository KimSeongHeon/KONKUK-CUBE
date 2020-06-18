package com.project.reservation_kcube

fun convertToname(num:Int):String{
    var ret = "";
    when(num){
        1-> ret = "공학관 K-Cube";
        2-> ret = "생명과학관 K-Cube"
        3->ret = "상허연구관 K-Cube";
        7 -> ret = "동물생명과학관 K-Cube"
        8 -> ret =  "생명과학관 Startup Ground";
        9 -> ret =  " 생명과학관 BIO Factory"
    }
    return ret;
}