String.prototype.replaceAll = function (oldChar, newChar) {
    return this.replace(new RegExp(oldChar, "gm"), newChar);
}

function getCtxPath() {
    var path = window.location.pathname;
    return path.substring(0, path.indexOf("/", 1));
}

var ctxPath = "";
var ZENG = ZENG || null;
if (ZENG != null) {
    ZENG.msgbox.loadingAnimationPath = (ctxPath + "/static/js/msgbox/loading.gif");
}
//对Date的扩展，将 Date 转化为指定格式的String
//月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
//年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
//例子： 
//(new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
//(new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { // author: meizz
    var o = {
        "M+": this.getMonth() + 1, // 月份
        "d+": this.getDate(), // 日
        "h+": this.getHours(), // 小时
        "m+": this.getMinutes(), // 分
        "s+": this.getSeconds(), // 秒
        "q+": Math.floor((this.getMonth() + 3) / 3), // 季度
        "S": this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function createSelect($select, data) {
    $select.empty();
    for (var i = 0; i < data.length; i++) {
        var d = data[i];
        var option = $("<option>" + d.TEXT + "</option>").val(d.VALUE);
        $select.append(option);
    }
}

function getRootParent() {
    var _this = window;
    var _parent = window.parent;
    for (; _this != _parent;) {
        _this = _parent;
        _parent = _this.parent;
    }
    return _parent;
}

function redirectLogin() {
    var expect = ctxPath + "/login.html";
    var location = getRootParent().location;
    if (location.pathname != expect) {
        location.href = expect;
    }
}

function redirectIndex() {
    var expect = ctxPath + "/index.html";
    var location = getRootParent().location;
    if (location.pathname != expect) {
        location.href = expect;
    }
}

function tryLogin() {
    $geta("/isUserLogined.htm?r=" + Math.random(), function (d) {
        if (d == true) {
            redirectIndex();
        }
    });
}

function tryLogout() {
    $geta("/isUserLogined.htm?r=" + Math.random(), function (d) {
        if (d != true) {
            redirectLogin();
        }
    });
}

function validateLogin(d, fun) {
    var _d;
    if ("string" == typeof d) {
        _d = JSON.parse(d);
    } else {
        _d = d;
    }
    if (_d.code == -999) {
        redirectLogin();
        return;
    }
    fun(_d);
}

function $get0(url, param, fun, async) {
    if (fun == undefined) {
        fun = param;
    }
    $.ajax({
        cache: false,
        type: "post",
        url: ctxPath + url,
        data: param,
        async: async,
        success: function (d) {
            validateLogin(d, fun);
        },
        error: function (r) {
            errorMsgbox("请求超时");
        }
    });
}

//同步请求
function $get(url, param, fun) {
    $get0(url, param, fun, false);
}

//异步请求
function $geta(url, param, fun) {
    $get0(url, param, fun, true);
}

function createCombobox(pid, id, list, cl) {
    if (!cl) {
        cl = "sel w150";
    }
    var p = "<select id='" + id + "' name='" + id + "' class='" + cl + "'>";
    if (list.length > 0) {
        p += "<option></option>";
        for (var i in list) {
            var item = list[i];
            p += "<option value='" + item["VALUE"] + "'>" + item["NAME"] + "</option>"
        }
    } else {
        p += "<option></option>";
    }
    p += "</select>";
    $("#" + pid).html("");
    $("#" + pid).append(p);
}

function createTable(pid, id, headers, buttonsFn, callbacks, list, cid) {
    var buttons;
    var hasButtons;
    var isButtonsFn = false;
    if ((typeof buttonsFn) == "function") {
        hasButtons = true;
        isButtonsFn = true;
    } else if (buttonsFn.length > 0 && (typeof buttonsFn[0] == "string")) {
        hasButtons = true;
        buttons = buttonsFn;
    }
    if (!cid) {
        cid = "ID";
    }
    var headersMap = [];
    var p = "<table id=" + id + " class='showtablelist' style='width: 90%'>";
    p += "<thead>";
    p += "<tr>";
    for (var i in headers) {
        var header = headers[i];
        var arr = header.split(":");
        var width = "auto;";
        if (arr.length == 2) {
            headersMap.push(arr[1]);
        } else if (arr.length == 3) {
            headersMap.push(arr[1]);
            width = arr[2] + "px;"
        }
        p += "<th nowrap='nowrap' style='width:" + width + "'>" + arr[0] + "</th>";
    }
    p += "</tr>";
    p += "</thead>";
    p += "<tbody>";
    if (list.length > 0) {
        for (var i in list) {
            p += "<tr>";
            var item = list[i];
            if (hasButtons) {
                if (isButtonsFn) {
                    buttons = buttonsFn(item);
                }
                var buttonsMap = [];
                for (var i in buttons) {
                    buttonsMap.push(buttons[i].split(":"));
                }
                for (var j in headersMap) {
                    p += "<td nowrap='nowrap'>" + (item[headersMap[j]] || "") + "</td>";
                }
                p += "<td nowrap='nowrap'>";
                for (var j in buttonsMap) {
                    p += "<a href='javascript:void(0);' data-id='" + item[cid] + "' data-type='" + buttonsMap[j][1];
                    p += "' class='" + buttonsMap[j][2] + "'>" + buttonsMap[j][0] + "</a>  ";
                }
                p += "</td>";
            } else {
                for (var j in headersMap) {
                    p += "<td nowrap='nowrap'>" + (item[headersMap[j]] || "") + "</td>";
                }
            }
            p += "</tr>";
        }
        $("#paging").show();
    } else {
        var noDtataTip = ctxPath + "/static/images/noDataTip.jpg";
        p += "<tr>";
        p += "<td class='tc' colspan='10'><img class='vm' src='" + noDtataTip + "'/></td>";
        p += "</tr>";
        $("#paging").hide();
    }
    p += "</tbody>";
    p += "</table>";
    p += "<br>";
    $("#" + pid).html("");
    $("#" + pid).append(p);
    $("#" + pid).find("a").click(function () {
        callbacks[$(this).data("type")].call(this, $(this).data("id"));
    });
}

function createPage(url, param, callback) {
    if (param != null) {
        param = encodeURI(obj2httpParam(param));
    }
    $("#paging").myPagination({
        cssStyle: 'bspagination',
        currPage: 1,
        pageNumber: 10,
        ajax: {
            on: true,
            type: "POST",
            url: ctxPath + url,
            dataType: "json",
            param: param,
            ajaxStart: function () {
                ZENG.msgbox.show(" 正在加载中，请稍后...", 6, 10000);
            },
            onClick: function (page) {
                $.fn.debug(page);
            },
            ajaxStop: function () {
                setTimeout(function () {
                    ZENG.msgbox.hide();
                }, 1);
            },
            callback: function (d) {
                validateLogin(d, callback);
            }
        }
    });
}

function validateNotNull(param) {
    for (var i in param) {
        if (param[i] == '' || param[i] == null) {
            errorMsgbox(i);
            return false;
        }
    }
    return true;
}

function validateNotNullForm(form) {
    var v = true;
    $("#" + form).find("input,select").each(function () {
        if (this.type == "button") {
            return;
        }
        if (!$(this).attr("hint") == "none") {
            if (!$(this).val()) {
                v = false;
                errorMsgbox(this.name || this.id);
                return false;
            }
        }
    });
    return v;
}

function mapList(list, id) {
    if (!id) {
        id = "ID";
    }
    var map = {};
    for (var i in list) {
        var item = list[i];
        map[item[id]] = item;
    }
    return map;
}

function getInputValues(id) {
    var map = {};
    $("#" + id).find("input,select").each(function () {
        if (this.type == "button") {
            return;
        }
        map[this.name || this.id] = $(this).val();
    });
    return map;
}

function setInputValues(id, data, mapping) {
//	$("#"+id).find("input").each(function(){
//		if(this.type == "button"){
//			return;
//		}
//		var v = mapping == null ? data[this.name] : data[mapping[this.name]];
//		if(v == null || v == undefined){
//			this.value = "";
//			return;
//		}
//		this.value = v;
//	});
//	$("#"+id).find("select").each(function(){
//		var v = mapping == null ? data[this.name] : data[mapping[this.name]];
//		if(v == null || v == undefined){
//			$(this).val("");
//			return;
//		}
//		$(this).val(v);
//	});
    $("#" + id).find("input,select,textarea").each(function () {
        var n = this.name || this.id;
        var v = mapping == null ? data[n] : data[mapping[n]];
        if (v == null || v == undefined) {
            $(this).val("");
            return;
        }
        $(this).val(v);
    });

}

function errorMsgbox(msg) {
    sysMsgbox(msg, true);
}

function msgbox(msg) {
    sysMsgbox(msg, false);
}

function sysMsgbox(msg, error) {
    var el = $("#sys-msgbox");
    if (el.length == 0) {
        $("body").append("<div id='sys-msgbox' title='系统提示' style='display:none;'></div>");
        el = $("#sys-msgbox");
    }
    var color = error == true ? "red" : "black";
    el.css("color", color);
    el.html(msg);
    setTimeout(function () {
        el.dialog({
            resizable: false,
            height: "auto",
            width: 400,
            modal: true,
            buttons: {
                "关 闭": function () {
                    $(this).dialog("close");
                }
            }
        }).show();
    }, 100);
}

function dialog(id, width, buttons) {
    if (buttons == null) {
        buttons = width;
        width = 400;
    }
    buttons["关 闭"] = function () {
        $(this).dialog("close");
    }
    $("#" + id).dialog({
        resizable: false,
        height: "auto",
        width: width,
        modal: true,
        buttons: buttons
    }).show();
}

function obj2httpParam(obj) {
    if (typeof obj == "string") {
        return obj;
    }
    var str = "";
    for (var i in obj) {
        str += i + "=" + obj[i] + "&";
    }
    return str;
}

function addColumn(list, c, nc, map) {
    for (var i in list) {
        var it = list[i];
        it[nc] = map[it[c]];
    }
}

function fillDate(list, c, nc, f) {
    for (var i in list) {
        var it = list[i];
        if (it[c]) {
            it[nc] = new Date(it[c]).Format(f);
        }
    }
}



