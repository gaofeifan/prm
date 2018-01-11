/**
 * Created by Administrator on 2017/9/13.
 */
$(function(){
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth()+1;
    var getDate = date.getDate();
    var data = year+"-"+month+"-"+getDate;
    $('#day').text(data);

    function ss(){
        var od = new Date();
        var oH=od.getHours(); //获取小时
        var oM=od.getMinutes(); //获取分钟
        $('#time').text(oH+":"+oM);
    }
    ss();
    setInterval(ss,1000);

    $('#left ul').on('click','li',function(){
        $(this).find('a').addClass('active');
        $(this).siblings('li').find('a').removeClass('active');
    });

    /*点击退出登录*/
    $('#beforeLogout').click(function(){
        allObj.beforeLogout()
    });
    $('#afterLogout').click(function(){
        allObj.afterLogout()
    });
    /*关闭alert报错提示*/
    $('#alertClose').click(function(){
        $('.alertShow').stop().hide();
    })
});
var allObj = {
    beforeLogout:function(){
        var win = new Window().confirm({
            title:'退出确认',
            content:'<p style="text-align: center;">您确定要退出系统吗？</p>',
            width:420,
            height:280,
            hasCloseBtn:false,
            text4ConfirmBtn:'确认',
            text4CancelBtn:'取消',
            dragHandle:'.window_header',
            skinClassName:'window_skin_a'
        }).on('confirm',function(){
            $.ajax({
                type:'get',
                url:'http://'+ssoPathUrl+'/sso/user/loginout',
                crossDomain: true,//支持跨域发送cookie
                dataType:'jsonp',
                success:function(data){
                    if(data.status==200){
                        window.location.href="./login.html";
                        $.cookie('front_id', null);
                        $.cookie('front_useremail', null);
                        $.cookie('front_username', null);
                        $.cookie('power',null)
                    }
                },
                error:function(){

                }
            })
        }).on('cancel',function(){
        })
    },
    afterLogout:function(){
        var win = new Window().confirm({
            title:'退出确认',
            content:'<p style="text-align: center;">您确定要退出系统吗？</p>',
            width:420,
            height:280,
            hasCloseBtn:false,
            text4ConfirmBtn:'确认',
            text4CancelBtn:'取消',
            dragHandle:'.window_header',
            skinClassName:'window_skin_a'
        }).on('confirm',function(){
            $.ajax({
                type:'get',
                url:'http://'+ssoPathUrl+'/sso/user/loginout',
                crossDomain: true,//支持跨域发送cookie
                dataType:'jsonp',
                success:function(data){
                    if(data.status==200){
                        window.location.href="./login.html";
                        $.cookie('back_id', null);
                        $.cookie('back_useremail', null);
                        $.cookie('back_username', null);
                        $.cookie('power',null)
                    }
                },
                error:function(){

                }
            })
        }).on('cancel',function(){
        })
    }
}
function menuActive(one){
    $('#left ul li').find('a').removeClass('active');
    $('#left ul li a.'+one).addClass('active');
}
function operationStyle(i){
    if(parseInt(i) == 1){
        return '删除权限'
    }else if(parseInt(i) == 2){
        return '新增权限'
    }else{
        return '';
    }
}
/*前台清空缓存自动退出*/
function frontcookie(){
    var frontEmail = $.cookie('front_useremail');
    if(!frontEmail){
        window.location.href="./login.html";
    }
}
/*后台清空缓存自动退出*/
function backCookie(){
    var backEmail = $.cookie('back_useremail');
    if(!backEmail){
        window.location.href="./login.html";
    }
}
/**
 *
 * @param i
 * @param num
 * @returns {boolean}
 * @constructor
 */
function StatusOn(i,num){
    if( isNaN(i) || i > num || i <= 0){
        return false;
    }else{
        var statusDivs = document.getElementsByClassName("status-div");
        for(var j = 0;j < statusDivs.length;j++){
            statusDivs[j].classList.remove("active");
            statusDivs[j].children[1].classList.remove("active");
            statusDivs[j].classList.remove("on");
            statusDivs[j].children[1].classList.remove("on");
            /* statusDivs[j].classList.remove("next");*/
        }
        for(var j = 0;j < statusDivs.length;j++){
            if(j < i){
                statusDivs[j].classList.add("active");
                statusDivs[j].children[1].classList.add("active");
            }
        }
        if(!!statusDivs[i-1]){
            var nowStatus = statusDivs[i-1].children;
            nowStatus[1].classList.add("on");
        }
        statusDivs[i-1].classList.add("on");
        /*if(i<num){
         statusDivs[i].classList.add("next");
         }*/
    }
}
/**
 * 邮箱的正则
 * @param str
 * @returns {boolean}
 */
function isEmail(str){
    var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
    return reg.test(str);
}

function getTop(){
    _top0 = parseInt($('#newForm .lump').eq(0).offset().top)-288;
    _top1 = parseInt($('#newForm .lump').eq(1).offset().top)-288;
    _top2 = parseInt($('#newForm .lump').eq(2).offset().top)-288;
    _top3 = parseInt($('#newForm .lump').eq(3).offset().top)-288;
    _top4 = parseInt($('#newForm .lump').eq(4).offset().top)-288;
    _top5 = parseInt($('#newForm .lump').eq(5).offset().top)-288;
    _top6 = parseInt($('#newForm .lump').eq(6).offset().top)-288;
    _top7 = parseInt($('#newForm .lump').eq(7).offset().top)-288;
    _top8 = parseInt($('#newForm .lump').eq(8).offset().top)-288;
    _top9 = parseInt($('#newForm .lump').eq(9).offset().top)-288;
    _top10 = parseInt($('#newForm .lump').eq(10).offset().top)-288;
    _top11 = parseInt($('#newForm .lump').eq(11).offset().top)-288;
}