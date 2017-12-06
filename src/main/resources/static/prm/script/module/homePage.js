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
                console.log(data);
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
                $('#passwordConfirm').hide();
                $('.passwordShow').show();
                $('.passwordEdit').val('').hide();
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
                $.cookie('back_useremail',alter_email);
                $('.emailShow').show();
                $('#emailVal').show().text(alter_email);
                $('.emailEdit').hide();
                $('#emailConfirm').hide()
            },
            error: function () {

            }
        });
    })
    /*修改邮箱 end*/

    /*加载日志*/
    operationLog();
});
function  operationLog(){
    $.ajax({
        type: 'get',
        url: 'http://'+gPathUrl+'/log/home',
        success: function (data) {
            $.each(data.data.permissionsOneWeekLsit, function (index, value) {
                var str =
            '<div>'+
                '<p>操作时间：<span>'+value.createDate+'</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作类型：<span>'+value.type+'</span> </p>'+
                '<p>涉及用户：<span>'+value.involvesUser+'</span></p>'+
                '<p>涉及权限：<span>'+value.involvesPermissions+'</span></p>'+
            '</div></br>';
                $(str).appendTo($('#accessLog'));
            });
            $.each(data.data.operationOneDayLsit, function (index, value) {
                var str =
                '<div class="o-l-list clearfix">'+
                '<div class="fl">'+
                '<p><em>'+value.userName+'</em> &nbsp;&nbsp;&nbsp;&nbsp;<span>'+value.createDate+'</span></p>'+
                '<p>ID：<span>'+value.userId+'</span>&nbsp;&nbsp;&nbsp;&nbsp; 公司：<span>'+value.company+'</span></p>'+
                '<p>部门：<span>'+value.department+'</span> &nbsp;&nbsp;&nbsp;&nbsp;岗位：<span>'+value.jobs+'</span></p>'+
                '</div>'+
                '<div class="fr"> <h4>操作日志：</h4>'+
                '<span>'+value.action+'</span>'+
                '</div>'+
                '</div>';
                $(str).appendTo($('#operationLog'));
            })
        },
        error: function () {

        }
    });
}
