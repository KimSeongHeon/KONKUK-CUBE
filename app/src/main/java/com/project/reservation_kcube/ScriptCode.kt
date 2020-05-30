package com.project.reservation_kcube

import android.util.Log

fun login_script(user_id:String,user_pw:String):String{
    return "javascript:function afterLoad() {" +
            "document.getElementsByName('userId')[0].value = '"+ user_id + "';"+
            "document.getElementsByName('pw')[0].value = '"+user_pw+"';"+
            "document.getElementById('loginBtn').click();"+
            //"window.android.onUrlChange(window.location.href);"+
            "};"+
            "afterLoad();";
}
fun show_button_click():String{
    return "javascript:function click_search(){" +
            "document.getElementById('searchBtn').click()" +
            "};" +
            "click_search();"
}
fun access_script():String{
    return "javascript:function access() {" +
            "document.getElementsByClassName('kcube_box')[0].getElementsByTagName('a')[0].click()"+
            "};"+
            "access();";
}
fun go_reserve_tab_script():String{
    return "javascript:function go_reserve() {" +
        "document.getElementsByClassName('tab_style li4')[0].getElementsByTagName('li')[1].getElementsByTagName('a')[0].click()"+
                "};"+
                "go_reserve();";
}
fun select_all_script():String{
    return "javascript:function select_all() {" +
            "if(document.getElementsByClassName('sel-result-box__wrap show').length == 0){"+
            "document.getElementById('buildAll').click();" +
            "document.getElementById('searchBtn').click(); console.log(\"In if\")}" +
            ""+
            //"else {document.getElementById('todayBtn').click();}"+
            "};"+
            "select_all();"
}
/*fun temp():String{
    return "javascript:function a(){\n" +
            "    setTimeout(function(){\n" +
            "        document.getElementById('buildAll').click();},3000);\n" +
            "    setTimeout(function(){\n" +
            "        document.getElementById('searchBtn').click();},6000);\n" +
            "};a()"
}*/
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
fun parsing_table():String {
    return "javascript:" +
            "function table_parsing(){ " +
            "var arr = [];" +
            "var arr1 = [];" +
            "var ret = [];" +
            "arr = document.getElementsByClassName('enable range reservBtn first');" +
            "arr1 = document.getElementsByClassName('enable range reservBtn last');" +
            "for(var i=0;i<arr.length;i++){" +
            " ret[i] = JSON.stringify(arr[i].getAttribute(\"data-params\"));" +
            "console.log(ret[i])"+
            "   } " +
            "for(var i=0;i<arr1.length;i++){" +
            " ret.push(JSON.stringify(arr[i].getAttribute(\"data-params\")));" +
            "console.log(JSON.stringify(arr[i].getAttribute(\"data-params\")))"+
            "   } " +
            "window.android.get_table(ret);" + "return true"+
            "}"+ "table_parsing()"+ ";";
}
fun update_date_script(index:String):String{
    Log.v("index",index)
    return " javascript: function update_date(){\n" +
            "    const selectEl = document.querySelector(\"#ymdSelect\");\n" +
            "    const optionEls = selectEl.querySelectorAll(\"option\");\n" +
            "    \n" +
            "    selectEl.value = optionEls[${index}].value;\n" +
            "    if (\"createEvent\" in document) {\n" +
            "    \tconst evt = document.createEvent(\"HTMLEvents\");\n" +
            "    \tevt.initEvent(\"change\", false, true);\n" +
            "    \tselectEl.dispatchEvent(evt);\n" +
            "    } else {\n" +
            "    \tselectEl.fireEvent(\"onchange\");\n" +
            "    }\n"+
            "}update_date();"
}
