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
            url: 'http://' + eamsPathUrl + '/base/frameWork/selectLastCompanyList',
            type: 'get',
            success: function (resp) {
                var str = '';
                $.each(resp.data,function(index,value){
                    str =str +'<div class="bodyList clearfix">\
                            <div class="no">'+(index+1)+'</div>\
                            <div style="height:19px;" class="shortName">'+(value.shortName||"")+'</div>\
                            <div class="name">'+value.name+'</div>\
                        </div>'

                });
                $(str).appendTo('#customerList')
            }
        })
    }
};
