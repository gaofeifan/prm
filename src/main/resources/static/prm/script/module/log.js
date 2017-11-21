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

    addPagedate(initData(1,null,null));

    /* 单击事件 清楚 表单数据 追加 操作日志  -- 分页展示   每页10条*/
        $("#operationLog").click(function(){

        })

    /* 单击事件 清楚 表单数据 追加 权限日志 -- 分页展示 每页10条*/
    $("#permissionsLog").click(function(){

    })
});
/*  加载分页插件 模块 */
function     addPagedate(totalPage){
    $('.M-box4').pagination({
        pageCount:totalPage,
        jump:true,
        coping:true,
        homePage:'首页',
        endPage:'末页',
        prevContent:'上页',
        nextContent:'下页',
        callback:function(api){
            api.setPageCount( totalPage);
            initData(api.getCurrent(),null,null);
        }
    });
}

function   initData (pageIndex,startdate,enddate) {

    
    var  totalPage ;
    $.ajax({
        url : "http://localhost:8083/log/operation",
        type : "post",
        async : false ,
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify({
            pageSize : 10,
            pageNo :pageIndex
        }),
        dataType : "json",
        success: function(data) {
            // 当前页 赋值
            /* pageNumber =data.data.pageNo ;//当前页   */
            totalPage = data.data.totalPage;// 总数
            // 总量赋值
            var  melist = data.data.list;
            // 清空 陈旧数据
          //  $("#basicDate").empty();
            // 追加 数据
        //   addFunction(melist);
        }
    })
    return totalPage;
}