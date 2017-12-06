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