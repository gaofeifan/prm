/**
 * Created by Administrator on 2017/11/16.
 */
frontcookie();
/*用户信用等级页面*/
menuActive('credit');
$(window).unbind("scroll");
$(function(){
    var flage = true;
    /*获取用户信用等级列表 */
    $.ajax({
        type:'get',
        url:'http://'+gPathUrl+'/user/level',
        crossDomain: false,//支持跨域发送cookie
        async:false,
        dataType:'json',
        success:function(data){
            var levels =  data.data;

            /* 循环追加信用等级信息到页面中*/
            for (var i = 0 ; i<levels.length;i++) {

                $("#credit_body_datas").append('<div class="bodyList clearfix" id ="base_' + i + '"></div>');
                $("#base_" + i + "").append('<div class="no"> <input type="text" name = "id" readonly = "true" style="border: none"  value="' + levels[i].id + '"  ></div>');
                $("#base_" + i + "").append('<div class="class"><input type="text" name="level" readonly = "true"  style="border: none"  value="' + levels[i].level + '"  ></div>');
                $("#base_" + i + "").append('<div class="protocolType" id="base_type_' + i + '"></div>');

                /*追加单选框 判断是否 选中*/
                $("#base_type_" + i + "").append('<input onclick="checkedType(this)" type="radio" id = "txt1'+i+'" name="type1' + i + '" value="协议/保函"   style="width:16px;height:16px;" ><label for="">协议/保函</label>');
                $("#base_type_" + i + "").append('<input onclick="checkedType(this)"  type="radio" id = "txt2'+i+'"  name="type1' + i + '" value="付款买单" style="width:16px;height:16px;" ><label for="">付款买单</label>');
                $("#base_type_" + i + "").append('<input  onclick="checkedType(this)" type="radio" id = "txt3'+i+'"  name="type1' + i + '" value="签约在途" style="width:16px;height:16px;" ><label for="">签约在途</label>');

                if (levels[i].protocolType.toString() == "协议/保函") {
                    $("#txt1" + i + "").attr("checked", true);
                }
                if (levels[i].protocolType == "付款买单") {
                    $("#txt2" + i + "").attr("checked", true);
                }
                if (levels[i].protocolType.toString() == "签约在途") {
                    $("#txt3" + i + "").attr("checked", true);
                }
                // 默认额度 默认期限添加
                $("#base_" + i + "").append('<div class="class"><input class="defaultLine must" style="width: 80%;" type="text" name="df_num"  id = "df_num"     value="' + levels[i].defaultQuota + '" onkeyup="value=value.replace(/[^\\-?\\d.]/g,\'\')" ></div>');
                $("#base_" + i + "").append('<div class="class"><input class="defaultDeadline must" style="width: 80%;" type="text" name="df_day"  id = "df_day"    value="' + levels[i].defaultTtime + '"  onkeyup="value=value.replace(/[^\\-?\\d.]/g,\'\')"></div>');

                /*z追加是否有效单选框*/
                $("#base_" + i + "").append('<div class="valid" id="base_boolean_' + i + '"></div>');
                $("#base_boolean_" + i + "").append('<input type="checkbox" name="type_boolean' + i + '" value="1"  style="width:16px;height:16px;"><label for="">有效</label>');
                $("input[name=type_boolean"+i+"][value="+levels[i].effectiveness+"]").attr("checked",true) ;


                /*z追加备注框*/
                $("#base_" + i + "").append('<div class="remark" id="base_mark_' + i + '"></div>');
                if(levels[i].mark.toString() =="null"){
                    levels[i].mark = '';
                }

                $("#base_mark_" + i + "").append('<textarea   style="height: 20px;" role="3" cols="30">'+ levels[i].mark +'</textarea>');

            }
        },
        error:function(){

        }
    });

    /*控制编辑按钮*/
    $.ajax({
        url: 'http://'+gPathUrl+'/auth/menu/findMenuOrButtonByPostId',
        type: 'get',
        data:{
            isMenu:false,
            menuId:3,

            email:$.cookie('front_useremail')
        },
        success: function (resp) {
            if(resp.data.length >0){
                $.each(resp.data,function(index,value){
                    if(value.name=="信用等级管理 - 修改"){
                        $('#editBtn').show();
                    }
                });
            }else{
                var inputRadio = $('input[type=radio]');
                var inputCheckbox = $('input[type=checkbox]');
                var remarkText = $('textarea');
                inputRadio.attr('disabled',true);
                inputCheckbox.attr('disabled',true);
                remarkText.attr('disabled',true);
            }
        }
    });

    /*确认按钮*/
    $("#confirmClick").click(function(){
        /*定义数组*/
        var commitDate = "{ "+"//userLevelList//"+":[";
        var flage = true;
        /* 循环 div中的所有 input 标签 */
        $("#credit_body_datas input[type='text'],input[type='radio'], input[type='checkbox'] ,textarea").each(function(vi,obj){

            /*判断标签类分别获取数据*/
            if( $(this)[0].getAttribute("type")=='text'){
                /*判断 text */
                if($(this).attr("name") =="id"){
                    commitDate+=",{"+"//id//"+":"+$(this).val()+"";
                }else   if($(this).attr("name") =="level"){
                    commitDate+=","+"//level//"+":"+"//"+$(this).val()+"//";
                } else   if($(this).attr("name") =="df_num"){
                    commitDate+=","+"//defaultQuota//"+":"+"//"+$(this).val()+"//";
                } else   if($(this).attr("name") =="df_day"){

                    commitDate+=","+"//defaultTtime//"+":"+"//"+$(this).val()+"//";
                }
            }else   if( $(this)[0].getAttribute("type")=='radio'){

                /*判断 单选按钮  */
                if($(this).is(":checked")){
                    commitDate+=","+"//protocolType//"+":"+"//"+$(this).val()+"//";
                }

            }else   if( $(this)[0].getAttribute("type")=='checkbox'){
                /*判断 复选框  */
                if($(this).is(":checked")){
                    commitDate+=","+""+"//effectiveness//"+":"+$(this).val();
                }else{
                    commitDate+=","+""+"//effectiveness//"+""+":0";
                }
            }else if($(this).is("textarea")){
                commitDate+=","+"//mark//://"+$(this).val()+"//}";
            }
        });
        commitDate+="],//email//://"+$.cookie('front_useremail')+"//}";
        var commitDate2 = commitDate.replace(",", "");
        var commitDate3 = commitDate2.replace(new RegExp("//","g"), '"');
        if(flage){
            $.ajax({
                type:'post',
                url:'http://'+gPathUrl+'/user/levelUpdate',
                crossDomain: false,//支持跨域发送cookie
                contentType: "application/json; charset=utf-8",
                data: commitDate3,
                dataType:'json',
                success:function(data){
                    location.reload();
                },
                error:function(){

                }
            });
        } else {
            alert("数据格式错误");
        }
    })

    /*取消按钮*/
    $("#cancelClick").click(function(){
        location.reload();
    })


    $("input.must").blur(function(){
        var isChecked = $(this).parents('.bodyList').find('input[type="checkbox"]').attr('checked');
        if(!!isChecked){
            if($(this).val().length <=0){
                $(this).css("background-color","#D6D6FF");
                flage = false;
                $(this).focus();
            }
        }
    });

});
/*  校验 */
function checkNUm(obj ) {

    if(!(/^[0-9]+\.?[0-9]*$/).test(obj.value)){
        alert("只能输入数字或小数");
        $(this).html("");
        $(this).focus();
    }
}


function   checkedType(obj){

    var names =  obj.name;
    $("input[name="+names+"]").each(function(){
        $(this).attr("checked",false);
    })

    $(this).attr("checked",true);

}