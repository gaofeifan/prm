/**
 * Created by Administrator on 2017/11/16.
 */
backCookie();
menuActive('homePage');
$(function(){
    $('#name').text($.cookie('back_username'));
    $('#emailVal').text($.cookie('back_useremail'));
    $('#nameEdit').click(function(){
        $('.nameShow').hide();
        $('.nameEdit').show();
    });
    $('#nameCancel').click(function(){
        $('.nameShow').show();
        $('.nameEdit').val('').hide();
        $('#nameConfirm').hide();
    });
    /*有值时显示确定按钮*/
    $('#nameInput').keyup(function(){
        if($(this).val()){
            $('#nameConfirm').show();
        }else{
            $('#nameConfirm').hide();
        }
    });
    //修改用户名
    $('#nameConfirm').click(function(){
        var alter_username =$('#nameInput').val();
        $.ajax({
            type: 'get',
            url: 'http://'+ssoPathUrl+'/sso/accountSet/admin/resetUsername',
            async:false,
            xhrFields:{
                withCredentials: true
            },
            crossDomain: true,//支持跨域发送cookie
            jsonp: 'callback',
            dataType:'jsonp',
            data:{
                id:$.cookie('back_id'),
                newUserName:alter_username
            },
            success: function (data) {
                if(data.status == '200'){
                    $.cookie('back_username',alter_username);
                    $('.nameEdit').hide();
                    $('#nameConfirm').hide();
                    $('.nameShow').show();
                    $('#name').show().text(alter_username);
                    $('.user_name').text(alter_username);
                }
            },
            error: function () {

            }
        });
    });

    /*修改密码 start*/
    $('#passwordEdit').click(function(){
        $('.passwordShow').hide();
        $('.passwordEdit').show();
    });
    $('#passwordCancel').click(function(){
        $('.passwordShow').show();
        $('.passwordEdit').val('').hide();
        $('#passwordConfirm').hide();
    });
    /*有值时显示确定按钮*/
    $('#passwordInput').keyup(function(){
        if($(this).val()){
            $('#passwordConfirm').show();
        }else{
            $('#passwordConfirm').hide();
        }
    });
    $('#passwordConfirm').click(function(){
        $.ajax({
            type: 'get',
            url: 'http://'+ssoPathUrl+'/sso/accountSet/admin/resetPassword',
            async:false,
            xhrFields:{
                withCredentials: true
            },
            crossDomain: true,//支持跨域发送cookie
            jsonp: 'callback',
            dataType:'jsonp',
            data:{
                id:$.cookie('back_id'),
                newPassword:$('#passwordInput').val()
            },
            success: function (data) {
                if(data.status == '200'){
                    $('#passwordConfirm').hide();
                    $('.passwordShow').show();
                    $('.passwordEdit').val('').hide();
                }else {
                    alert(data.error);
                }
            },
            error: function () {

            }
        });
    });
    /*修改密码 end*/


    /*修改邮箱 start*/
    $('#emailEdit').click(function(){
        $('.emailShow').hide();
        $('.emailEdit').show();
    });
    $('#emailCancel').click(function(){
        $('.emailShow').show();
        $('.emailEdit').val('').hide();
        $('#emailConfirm').hide();
    });
    /*有值时显示确定按钮*/
    $('#emailInput').keyup(function(){
        if($(this).val()){
            $('#emailConfirm').show();
        }else{
            $('#emailConfirm').hide();
        }
    });
    $('#emailConfirm').click(function(){
        var alter_email =$('#emailInput').val();
        $.ajax({
            type: 'get',
            url: 'http://'+ssoPathUrl+'/sso/accountSet/admin/resetEmail',
            xhrFields:{
                withCredentials: true
            },
            crossDomain: true,//支持跨域发送cookie
            jsonp: 'callback',
            dataType:'jsonp',
            data:{
                id:$.cookie('back_id'),
                newEmail:alter_email
            },
            success: function (data) {
                if(data.status == 200){
                    $.cookie('back_useremail',alter_email);
                    $('.emailShow').show();
                    $('#emailVal').show().text(alter_email);
                    $('.emailEdit').hide();
                    $('#emailConfirm').hide()
                }else{
                    alert(data.msgs);
                }
            },
            error: function () {

            }
        });
    })
    /*修改邮箱 end*/

    /*加载日志*/
    operationLog();

    /* createBY sevenboyLiu  2017年12月19日18:03:48  自动报表 邮件发送功能 */
    $("#seek1").click(function(){
        var statu = confirm("您确认要发送上月新增Partner清单邮件给管理者吗？");
        if(statu){
            sendEmail("email/lastNew");
        }
    })

    $("#seek2").click(function(){
        var statu = confirm("您确认要发送签约在途Partner清单邮件给接收者吗？");
        if(statu){
            sendEmail("email/signing");
        }
    })

    $("#seek3").click(function(){
        var statu = confirm("您确认要发即将到期Partner清单邮件给接收者吗？");
        if(statu){
            sendEmail("email/warning");
        }
    })

});
/*邮件请求ajax*/
function sendEmail(url) {
    $.ajax({
        type: 'get',
        url: 'http://' + gPathUrl + '/' + url,
        crossDomain: true,//支持跨域发送cookie
        dataType: 'json',
        success: function (data) {
            alert(data.data);
        }
    })
}
function  operationLog(){
    $.ajax({
        type: 'get',
        url: 'http://'+gPathUrl+'/log/home',
        success: function (data) {
            $.each(data.data.permissionsOneWeekLsit, function (index, value) {
                var str =
            '<div class="a-l-list clearfix"><div style="width: 49%;" class="fl clearfix">'+
                '<p>操作时间：<span>'+(value.createDate||"")+'</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作类型：<span>'+(value.type||'')+'</span> </p>'+
                '<p class="aboutUser">涉及用户：<span>'+(value.involvesUser||'')+'</span></p>'+
            '</div><div style="width: 49%;" class="fr clearfix"><h4 class="h4Title">涉及权限：</h4><span class="aboutPower">'+(value.involvesPermissions||'')+'</span></div></div>';
                $(str).appendTo($('#accessLog'));
            });
            $.each(data.data.operationOneDayLsit, function (index, value) {
                var str =
                '<div class="o-l-list clearfix">'+
                '<div class="fl">'+
                '<p><em>'+(value.userName||'')+'</em> &nbsp;&nbsp;&nbsp;&nbsp;<span>'+(value.createDate||'')+'</span></p>'+
                '<p style="height: auto;line-height: 28px;">ID：<span>'+(value.userId||'')+'</span>&nbsp;&nbsp;&nbsp;&nbsp; 公司：<span>'+(value.company||'')+'</span></p>'+
                '<p>部门：<span>'+(value.department||'')+'</span> &nbsp;&nbsp;&nbsp;&nbsp;岗位：<span>'+(value.jobs||'')+'</span></p>'+
                '</div>'+
                '<div class="fr"> <h4>操作日志：</h4>'+
                '<span style="line-height: 28px;">'+(value.action||'')+'</span>'+
                '</div>'+
                '</div>';
                $(str).appendTo($('#operationLog'));
            })
        },
        error: function () {

        }
    });
}
