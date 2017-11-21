/**
 * Created by Administrator on 2017/11/16.
 */
/*backCookie();*/
menuActive('log');
$(function(){
    $('.topBar ol li').click(function(){
        $(this).siblings('li').removeClass('active');
        $(this).addClass('active');
    })
});