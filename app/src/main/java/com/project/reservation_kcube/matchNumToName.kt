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
fun convertRoomNumToName(num:Int):String{
    var ret = "";
    when(num){
        7,15,22,44,55-> ret= "1호실"
        8,16,23,45,56 -> ret= "2호실"
        9,17,24,46-> ret= "3호실"
        10,18,25,47-> ret= "4호실"
        11,19,26,48-> ret= "5호실"
        12,20,27,54-> ret= "6호실"
        13,21,28-> ret = "7호실"
        14,29 -> ret = "8호실"
        50->ret = "B104"
    }
    return ret
}
fun converNametoNum(name:String):Int{
    var ret = 0;
    if(name == "공학관 K-Cube") ret = 1;
    else if(name == "생명과학관 K-Cube") ret = 2;
    else if(name == "상허연구관 K-Cube") ret = 3;
    else if(name == "동물생명과학관 K-Cube") ret = 7;
    else if(name == "생명과학관 Startup Ground(LINC+사업단)") ret = 8;
    else if(name == "생명과학관 BIO Factory(LINC+사업단)") ret = 9;
    return ret
}