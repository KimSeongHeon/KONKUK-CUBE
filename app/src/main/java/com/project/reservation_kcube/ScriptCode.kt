package com.project.reservation_kcube

import android.util.Log

fun get_name():String{
    return "javascript:function temp(){" +
            "var name = document.getElementsByClassName('name1 first')[0].textContent;" +
            "name = name.split('\\n')[0];" +
            "var dept = document.getElementsByClassName('name2')[0].textContent;" +
            "dept = dept.split('\\n')[1];" +
            "var id = document.getElementsByClassName('name3 last')[0].textContent;" +
            "id = id.split('\\n')[1];" +
            "window.android.get_my_name(name,dept,id); return true;" +
            "}; temp()"
}
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
    Log.v("parsing_date", "parsing_date");
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
            " ret.push(JSON.stringify(arr1[i].getAttribute(\"data-params\")));" +
            "console.log(JSON.stringify(arr1[i].getAttribute(\"data-params\")))"+
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
fun click_reserve(time:String,info:Pair<Int,Int>):String{
    var building = info.first
    var room = info.second
    var time  = time
    return "javascript:(function reserve(){" +
            "var b = `.enable.range.reservBtn[data-params*='\"buildSeq\":\"${building}\"'][data-params*='\"roomSeq\":\"${room}\"'][data-params*='\"rsvStartHm\":\"${time}\"']`;" +
            "var a = document.querySelector(b); console.log(b, a);" +
            "a.click();})()"
}
fun parsing_reserve_data():String{
    return "javascript:function temp(){" +
            "document.getElementById('srupSeq').value = 1;" +
            "var date = document.getElementById('dateNm').innerText;" +
            "var location = document.getElementById('cubeNm').innerText;" +
            "var location_info = document.getElementById('cubeInfo').innerText;" +
            "var name = document.getElementsByTagName('tbody')[0].getElementsByClassName('last')[0].innerText;" +
            "var possible_time = document.getElementById('remainderTm').innerText;" +
            "var userInfo = document.getElementById('userInfo').innerText;" +
            "var arr = [];" +
            "var purpose = document.getElementById('srupSeq').childNodes;" +
            "for(var i=1;i<purpose.length-1;i++){" +
            "arr.push(purpose[i].innerText);}"+
            "console.log(arr[0]);" +
            "window.android.show_reserve_data(date,location,location_info,name,possible_time,arr,userInfo); return true" +
            "}temp()"
}
fun select_time_script(num:Int):String{
    return "javascript:function temp(){" +
            "document.getElementById('resetTm').click();" +
            "var a = document.getElementsByClassName('day-list tmList')[0].childNodes;" +
            "for(var i=0;i<${num+1};i++){ a[i].click(); } } temp();"
}
fun select_purpose_script(num:Int):String{
    return "javascript:function temp(){" +
            "var purpose = document.getElementById('srupSeq');" +
            "purpose.value = ${num+1};" +
            "} temp();"
}
fun open_friend_script():String{
    return "javascript:function temp(){" +
            "document.getElementById('userAddBtn').click();} temp()"
}
fun Search_friend_script(str:String):String{
    Log.v("search_friend_script",",")
    return "javascript:function temp(){" +
            "console.log(\"${str}\");" +
            "document.getElementsByName('userId')[1].value = \"${str}\";" +
            "document.getElementById('addBtn').click();" +
            "var target = document.getElementsByClassName('last')[19];" +
            "var config = { attributes: true, childList: true, characterData: true };" +
            "var observer = new MutationObserver(function(mutations){\n" +
            "    mutations.forEach(function(mutation){\n" +
            "document.getElementsByClassName('di_btn_conf ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only')[0].click();})\n" +
            "});" +
            "var target2 = document.getElementsByClassName('del-box-item')[0].getElementsByClassName('last')[0];" +
            "var observer2 = new MutationObserver(function(mutations){\n" +
            "mutations.forEach(function(mutation){\n" +
            "var a = document.getElementsByClassName('del-box-item')[0].getElementsByTagName('i');" +
            "var id = [];" +
            "var name = [];" +
            "for(var i=0;i<a.length;i++){" +
            "if(i%2 == 0) id.push(a[i].innerText);" +
            "else name.push(a[i].innerText);}" +
            "window.android.select_friends_script(id,name);return true;});" +
            "});" +
            "observer.observe(target, config);" +
            "observer2.observe(target2,config);" +
            "window.alert = function(msg){" +
            "console.log(\"HI\");" +
            "window.android.print_error(msg); return true;" +
            "};" +
            "}temp()"
}
fun final_submit_script():String{
    return "javascript:function temp(){" +
            "window.confirm = function(){" +
            "window.android.print_success(\"신청되었습니다.\");" +
            "return true; return true;};" +
            "window.alert = function(msg){" +
            "window.android.print_error(msg); return true;" +
            "};" +
            "var arr=  document.querySelectorAll(\"input[name^='reservUserList'][name\$='userId']\");" +
            "var ret = [...arr].map(v=>v.value);" +
            "window.android.save_friend(ret);" +
            "document.getElementById('saveBtn').click();" +
            "}temp()"
}
fun delete_friend_script(num:Int):String{
    return "javascript:function temp(){" +
            "var a = document.getElementsByClassName('del-box')[${num}].getElementsByClassName('userDel')[0].click();" +
            "window.alert = function(msg){" +
            "window.android.print_error(msg); return true;" +
            "};" +
            "}temp()"
}
fun show_reserve_data():String{
    return "javascript:function temp(){" +
            "var a = document.getElementsByTagName('tbody')[0].getElementsByTagName('tr');" +
            "var c = [];" +
            "for(var i=0;i<a.length;i++){" +
            "var b = a[i].getElementsByTagName('td');" +
            "var info = new Object;" +
            "for(var j=0;j<b.length;j++){" +
            "if(j==0) info.click_day = b[j].innerText;" +
            "if(j==1) info.day = b[j].innerText;" +
            "if(j==2) info.time = b[j].innerText;" +
            "if(j==3) info.location = b[j].innerText;" +
            "if(j==4 || j==5){" +
            "if(b[j].innerText != \"-\") info.accept = b[j].innerText;" +
            "}" +
            "}" +
            "c.push(JSON.stringify(info));" +
            "}" +
            "window.android.print_reserve(c); return true;" +
            "} temp();"
}
fun move_reserve_tab():String{
    return "javascript:function temp(){" +
            "document.getElementsByClassName('tab_style li4')[0].getElementsByTagName('a')[2].click();" +
            "}; temp()"
}
fun one_click_add_script(query:String):String{
    return "javascript:function temp(){" +
            "var target = document.getElementsByTagName('body')[0];" +
            "var observer = new MutationObserver(function(mutations) {" +
            "mutations.forEach(function(mutation){" +
            "if(mutation.previousSibling.getAttribute(\'class\') == \"ui-dialog ui-widget ui-widget-content ui-corner-all syworksDialog ui-draggable ui-dialog-buttons\")" +
            "{window.android.say_load(\"${query}\"); " +
            "observer.disconnect(); return true;}})"+
            "});" +
            "console.log(\"one click add script\");" +
            "var config = { attributes: false, childList: true, characterData: false };" +
            "observer.observe(target,config);" +
            "document.getElementById('userAddBtn').click();" +
            "}; temp()"
}