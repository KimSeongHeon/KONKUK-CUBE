package com.project.reservation_kcube

fun login_script(user_id:String,user_pw:String):String{
    return "javascript:function afterLoad() {" +
            "document.getElementsByName('userId')[0].value = '"+ user_id + "';"+
            "document.getElementsByName('pw')[0].value = '"+user_pw+"';"+
            "document.getElementById('loginBtn').click();"+
            "window.android.onUrlChange(window.location.href);"+
            "};"+
            "afterLoad();";
}
fun parsing_building(): String {
    return "javascript:function building_parsing(){" +
            "var building = document.getElementsByClassName(\"lental-able\")[0];" +
            "var size = building.getElementsByTagName(\"li\").length;" +
            "var arr = [];" +
            "for(var i=0;i<size;i++){\n" +
            "arr[i] = building.getElementsByTagName(\"li\")[i].getElementsByTagName(\"label\")[0].textContent;" +
            ";}" + "window.android.get_building(arr);" + "return true;" +
            "}" +
            "building_parsing();"
}
fun parsing_date():String{
    return    "javascript:function date_parsing(){"+
            "var date = document.getElementById(\"ymdSelect\");"+
            "var option = date.getElementsByTagName(\"option\");"+
            "var arr = [];"+
    "for(var i=0;i<option.length;i++){"+
            "arr.push(option[i].text);"+
            "}"+"window.android.get_date(arr);" + "return true;"+
            "}"+"date_parsing();"
}