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
    var url = "/log/operation";
    var type = 1;
    /*追加正文title*/
    addTitleOptions();
    addPagedate(initData(1,null,null,type,url),type,url);

    /* 单击事件 清楚 表单数据 追加 操作日志  -- 分页展示   每页10条*/
        $("#operationLog").click(function(){
            url = "/log/operation";
            type = 1;
            addTitleOptions();
            /*获取 开始时间 与结束时间*/
            addPagedate(initData(1,$("#startData").val(),  $("#endData").val(),type,url),type,url);
        })

    /* 单击事件 清楚 表单数据 追加 权限日志 -- 分页展示 每页10条*/
    $("#permissionsLog").click(function(){
        url = "/log/permissions";
        type = 2;
        addTitlepermissionsLog();
        addPagedate(initData(1,$("#startData").val(),  $("#endData").val(),type,url),type,url);
    })

    /*根据时间查询*/
    $("#findBydate").click(function(){
        if((null!=$("#startData").val()&&""!=$("#startData").val())&&(null!=$("#endData").val()&&""!=$("#endData").val())){
            addPagedate(initData(1,$("#startData").val(),  $("#endData").val(),type,url),type,url);
        }else{
            alert("时间不能为空");
        }
    })
});




/*  加载分页插件 模块 */
function     addPagedate(totalPage,type,url){
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
            initData(api.getCurrent(),$("#startData").val(),$("#endData").val(),type,url);
        }
    });
}

/*列表获取*/
function   initData (pageIndex,startdate,enddate,type,url) {  // type 为 1 — 则代表操作界面  2 -- 代表权限界面

    var  totalPage ;
    $.ajax({
        url : "http://"+gPathUrl+""+url,
        type : "post",
        async : false ,
        contentType : "application/json; charset=utf-8",
        data : JSON.stringify({
            pageSize : 10,
            pageNo :pageIndex,
            startDate:startdate,
            endDate:enddate
        }),
        dataType : "json",
        success: function(data) {
            // 当前页 赋值
            /* pageNumber =data.data.pageNo ;//当前页   */
            totalPage = data.data.totalPage;// 总数
            // 总量赋值
            var  melist = data.data.list;
            // 清空 陈旧数据
           $("#log_base_body").empty();
            // 追加 数据
            if(type==1){
                addOption(melist);
            }else if(type==2){
                addPermissionsLog(melist);
            }
        }
    })
    return totalPage;
}

/*正文数据添加  操作日志正文*/
function   addOption ( melist) {

    /*循环数据添加信息*/
    for (var i = 0 ; i <melist.length;i++ ){

        $("#log_base_body").append(' <div class="bodyList clearfix" id = "log_base_data_'+i+'"></div>');

        /*追加内部信息*/
   /*     $("#log_base_data_"+i+"").append('  <div class="loginId">melist[i].id;</div>');*/
        $("#log_base_data_"+i+"").append('  <div class="loginId">'+melist[i].userId+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="name">'+melist[i].userName+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="company">'+melist[i].company+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="department">'+melist[i].department+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="post">'+melist[i].jobs+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="time">'+melist[i].createDate+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="log">'+melist[i].action+'</div>');
    }

}

/*正文数据添加  权限日志正文*/
/*正文数据添加  权限日志正文*/
function   addPermissionsLog ( melist) {
    /*循环数据添加信息*/
    for (var i = 0 ; i <melist.length;i++ ){
        $("#log_base_body").append(' <div class="bodyList clearfix" id = "log_base_data_'+i+'"></div>');
        /*追加内部信息*/
   /*     $("#log_base_data_"+i+"").append('  <div class="loginId">melist[i].id;</div>');*/
        $("#log_base_data_"+i+"").append('  <div class="loginId">'+melist[i].createDate+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="name">'+melist[i].type+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="company">'+melist[i].involvesUser+'</div>');
        $("#log_base_data_"+i+"").append('  <div class="department">'+melist[i].involvesPermissions+'</div>');
    }
}


/*正文title --操作*/
function   addTitleOptions ( ) {
    $("#log_basic_title").empty();
    $("#log_basic_title").append(' <div class="loginId">登录ID</div>');
    $("#log_basic_title").append('<div class="name">姓名</div>');
    $("#log_basic_title").append('<div class="company">公司</div>');
    $("#log_basic_title").append('<div class="department">部门</div>');
    $("#log_basic_title").append('<div class="post">岗位</div>');
    $("#log_basic_title").append('<div class="time">操作时间</div>');
    $("#log_basic_title").append('<div class="log">操作日志</div>');
}


/*正文title --权限*/
function   addTitlepermissionsLog ( ) {
    $("#log_basic_title").empty();
    $("#log_basic_title").append(' <div class="loginId">操作时间</div>');
    $("#log_basic_title").append('<div class="name">操作类型</div>');
    $("#log_basic_title").append('<div class="company">涉及用户</div>');
    $("#log_basic_title").append('<div class="department">涉及权限</div>');
}