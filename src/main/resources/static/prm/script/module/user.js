/**
 * Created by Administrator on 2017/11/16.
 */
backCookie();
menuActive('user');
$(function(){
    gettotalCount();
    $('#search').click(function(){
        gettotalCount();
    });
});
function gettotalCount() {
    $.ajax({
        type: 'post',
        url: 'http://'+oaPathUrl+'/oa/user/list.do',
        async:false,
        dataType: 'json',
        data:{
            username:$('#username').val(),
            filenumber:$('#filenumber').val(),
            email:$('#email').val()
        },
        success: function (data) {
            $('.body-box').empty();
           if(data.code == 200) {
                $('.M-box4').pagination({
                    pageCount: data.data.pagination.totalPage,
                    jump: true,
                    callback:function(api){
                        InitData(api.getCurrent())
                    }
                });
                $.each(data.data.pagination.list, function (index, value) {
                   var tr = ' <div class="bodyList clearfix">\
                        <div class="loginId">'+value.ssoId+'</div>\
                        <div class="name">'+value.username+'</div>\
                        <div class="number">'+(value.filenumber||'')+'</div>\
                        <div class="company">'+(value.companyname||'')+'</div>\
                        <div class="department">'+(value.dempname||'')+'</div>\
                        <div class="post">'+(value.postname||'')+'</div>\
                        <div class="phone">'+(value.phone||'')+'</div>\
                        <div class="email">'+(value.companyEmail||'')+'</div>\
                    </div>';
                   $(tr).appendTo('.body-box');
               });
            }
        },
        error: function () {
            alert('数组加载失败')
        }
    });
}
function InitData(pageIndex) {
    $('.body-box').empty();
    $.ajax({
        type: "get",
        url: 'http://'+oaPathUrl+'/oa/user/list.do',
        data: {
            pageNo:parseInt(pageIndex),
            username:$('#username').val(),
            filenumber:$('#filenumber').val(),
            email:$('#email').val()
        },
        success: function(data) {
            $.each(data.data.pagination.list, function (index, value) {
                var tr = ' <div class="bodyList clearfix">\
                        <div class="loginId">'+value.ssoId+'</div>\
                        <div class="name">'+value.username+'</div>\
                        <div class="number">'+(value.filenumber||'')+'</div>\
                        <div class="company">'+(value.companyname||'')+'</div>\
                        <div class="department">'+(value.dempname||'')+'</div>\
                        <div class="post">'+(value.postname||'')+'</div>\
                        <div class="phone">'+(value.phone||'')+'</div>\
                        <div class="email">'+(value.companyEmail||'')+'</div>\
                    </div>';
                $(tr).appendTo('.body-box');
            });
        }
    })
}

