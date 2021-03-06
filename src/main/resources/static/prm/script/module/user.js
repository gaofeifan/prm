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
        type: 'get',
        // url: 'http://'+oaPathUrl+'/oa/user/list.do',
        url: 'http://'+eamsPathUrl+'/base/person/selectPersonPageByQuery',
        async:false,
        dataType: 'json',
        data:{
            name:$('#username').val(),
            number:$('#filenumber').val(),
            email:$('#email').val()
        },
        success: function (data) {
            console.log(data)
            $('.body-box').empty();
           if(data.code == 200) {
                $('.M-box4').pagination({
                    pageCount: data.data.totalPage,
                    jump: true,
                    callback:function(api){
                        InitData(api.getCurrent())
                    }
                });
                $.each(data.data.list, function (index, value) {
                    console.log(data)
                    var tr = ' <div class="bodyList clearfix">\
                        <div class="loginId">'+value.ssoId+'</div>\
                        <div class="name">'+value.name+'</div>\
                        <div class="number">'+(value.number||'')+'</div>\
                        <div class="company">'+(value.companyName||'')+'</div>\
                        <div class="department">'+(value.deptName||'')+'</div>\
                        <div class="post">'+(value.positionName||'')+'</div>\
                        <div class="phone">'+(value.phone||'')+'</div>\
                        <div class="email">'+(value.email||'')+'</div>\
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
        url: 'http://'+eamsPathUrl+'/base/person/selectPersonPageByQuery',
        data: {
            pageNo:parseInt(pageIndex),
            username:$('#username').val(),
            filenumber:$('#filenumber').val(),
            email:$('#email').val()
        },
        success: function(data) {
            $.each(data.data.list, function (index, value) {
                var tr = ' <div class="bodyList clearfix">\
                        <div class="loginId">'+value.ssoId+'</div>\
                        <div class="name">'+value.name+'</div>\
                        <div class="number">'+(value.number||'')+'</div>\
                        <div class="company">'+(value.companyName||'')+'</div>\
                        <div class="department">'+(value.deptName||'')+'</div>\
                        <div class="post">'+(value.positionName||'')+'</div>\
                        <div class="phone">'+(value.phone||'')+'</div>\
                        <div class="email">'+(value.email||'')+'</div>\
                    </div>';
                $(tr).appendTo('.body-box');
            });
        }
    })
}

