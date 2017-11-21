/**
 * Created by Administrator on 2017/11/16.
 */
/*backCookie();*/
menuActive('homePage');
$(function(){
    $('#nameEdit').click(function(){
        $('.nameShow').hide();
        $('.nameEdit').show();
    });
    $('#passwordEdit').click(function(){
        $('.passwordShow').hide();
        $('.passwordEdit').show();
    });
    $('#emailEdit').click(function(){
        $('.emailShow').hide();
        $('.emailEdit').show();
    })
    operationLog();
});



    function  operationLog  () {
        $.ajax({
            type: 'get',
            url: 'http://localhost:8083/log/home',
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
                    if(value.userName == null){
                        value.userName = ' ';
                    }
                    if(value.userId == null){
                        value.userId = ' ';
                    }
                    if(value.company == null){
                        value.company = ' ';
                    }
                    if(value.department == null){
                        value.department = ' ';
                    }
                    if(value.jobs == null){
                        value.jobs = ' ';
                    }
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
