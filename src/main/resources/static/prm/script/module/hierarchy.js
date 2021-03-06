/**
 * Created by Administrator on 2017/11/16.
 */
frontcookie();
menuActive('hierarchy');
$(window).unbind("scroll");
var isEditFlag = true;
$(function(){
    /*控制编辑按钮*/
    $.ajax({
        url: 'http://'+gPathUrl+'/auth/menu/findMenuOrButtonByPostId',
        type: 'get',
        async:false,
        data:{
            isMenu:false,
            menuId:4,
            email:$.cookie('front_useremail')
        },
        success: function (resp) {
            if(resp.code == 200){
                if(resp.data.length > 0){
                    $.each(resp.data,function(index,value){
                        if(value.name=="层级位数管理 - 修改"){
                            $('#editBtn').show();
                        }
                    });
                }else{
                    isEditFlag = false;
                }
            }
        }
    });
    var booleans;
    /*调用接口判断本层是否已经被使用是否可以修改*/
    $.ajax({
        type:'get',
        url:'http://'+gPathUrl+'/user/checkIsEditHierarchy',
        crossDomain: false,//支持跨域发送cookie
        async:false,
        dataType:'json',
        success:function(data){
            booleans = data.data;
        }})

    /*获取层数信息*/
    $.ajax({
        type:'get',
        url:'http://'+gPathUrl+'/user/hierarchy',
        crossDomain: false,//支持跨域发送cookie
        async:false,
        dataType:'json',
        success:function(data){
            var baseData =  data.data;

            /* 循环追加信用等级信息到页面中*/
            for (var i = 0 ; i<baseData.length;i++) {

                /*追加第几层*/
                $("#hi_base_layer").append('<div class="item"><span>'+baseData[i].layerName+'</span></div>');

                /*追加 序列号*/
                $("#hi_base_layer_body").append('<div class="item"   id="base_body_'+i+'"> </div>');

                /*追加隐藏附属数据  序号 层级  */
                $("#base_body_"+i+"").append('<input type="text" hidden = "true" name = "id" value='+baseData[i].id+'> ');
                $("#base_body_"+i+"").append('<input type="text"  hidden = "true" name = "layerName" value='+baseData[i].layerName+'> ');

                /*追加 下拉数字*/
                $("#base_body_"+i+"").append(' <select  id = "select_'+i+'" name=""> <option value="1">1</option> <option value="2">2</option> <option value="3">3</option> <option value="4">4</option><option value="5">5</option><option value="6">6</option></select> ');

                /*遍历默认选中数字*/
                $("#base_body_"+i+" option[value="+baseData[i].layerNumber+"]").attr("selected", true);
                /*置灰*/
                if(!isEditFlag){
                    $("#select_"+i+"").attr("disabled",true);
                    $("#select_"+i+"").attr("style","background-color: #bdbdbd");
                }else{
                    if(!booleans[i]){
                        $("#select_"+i+"").attr("disabled",true);
                        $("#select_"+i+"").attr("style","background-color: #bdbdbd");
                    }
                }
            }
        },
        error:function(){

        }
    });


    /*确认按钮 更新数据*/
    $("#confirmClick").click(function(){
        /*定义数组*/
        var commitDate = "{ "+"//hierarchyList//"+":[";
        var i= 0;
        /* 循环 option中的所有 selected 标签 */
        $("   option:selected ").each(function(vi,obj){
            /*判断标签类分别获取数据*/
            commitDate+=",{//id//:"+$(this).parent().prev().prev().val()+", //layerName//://"+$(this).parent().prev().val()+"//, //layerNumber//:"+$(this).val()+"}";
            i+=parseInt($(this).val());
        });
        commitDate+="],//email//://"+$.cookie('front_useremail')+"//}";
        var commitDate2 = commitDate.replace(",", "");
        var commitDate3 = commitDate2.replace(new RegExp("//","g"), '"');
        if(i<=20){
            $.ajax({
                type:'post',
                url:'http://'+gPathUrl+'/user/hierarchyUpdate',
                crossDomain: false,//支持跨域发送cookie
                contentType: "application/json; charset=utf-8",
                data: commitDate3,
                dataType:'json',
                success:function(data){
                    location.reload();
                },
                error:function(){
                    alert("信息修改异常！")
                }
            }) ;
        } else {
            alert("层数总和不能超过20！");
        }
    });

    /*取消按钮*/
    $("#cancelClick").click(function(){
        location.reload();
    })



});

function   checkedType(obj){

    var names =  obj.name;
    $("input[name="+names+"]").each(function(){
        $(this).attr("checked",false);
    })

    $(this).attr("checked",true);

}