/**
 * Created by Administrator on 2017/11/16.
 */
/**
 * Created by Administrator on 2017/9/12.
 */
frontcookie();
$(window).unbind("scroll");
$(function(){
    menuActive('internalCustomer');
    internalCustomerObj.queryCom();
});
var internalCustomerObj = {
    queryCom:function(){
        $.ajax({
            url: 'http://192.168.4.213:8082/base/frameWork/selectCompanyByQuery',
            type: 'get',
            success: function (resp) {
                var str = '';
                $.each(resp.data,function(index,value){
                    str =str +'<div class="bodyList clearfix">\
                            <div class="no">'+(index+1)+'</div>\
                            <div class="name">'+value.name+'</div>\
                        </div>'

                });
                $(str).appendTo('.body-box')
            }
        })
    }
};
