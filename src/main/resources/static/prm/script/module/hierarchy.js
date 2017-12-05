/**
 * Created by Administrator on 2017/11/16.
 */
/**
 * Created by Administrator on 2017/9/12.
 */
/*backCookie();*/
menuActive('hierarchy');
$(function(){

        /*获取层数信息*/
        $.ajax({
            type:'get',
            url:'http://localhost:8083/user/hierarchy',
            crossDomain: false,//支持跨域发送cookie
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
                    $("#base_body_"+i+"").append(' <select name=""> <option value="1">1</option> <option value="2">2</option> <option value="3">3</option> <option value="4">4</option><option value="5">5</option><option value="6">6</option></select> ');

                    /*遍历默认选中数字*/
                    $("#base_body_"+i+" option[value="+baseData[i].layerNumber+"]").attr("selected", true);

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
            i+=$(this).val();
        });

        commitDate+="]}";
        var commitDate2 = commitDate.replace(",", "");
        var commitDate3 = commitDate2.replace(new RegExp("//","g"), '"');
        if(i<=20){

     $.ajax({
            type:'post',
            url:'http://localhost:8083/user/hierarchyUpdate',
            crossDomain: false,//支持跨域发送cookie
            contentType: "application/json; charset=utf-8",
            data: commitDate3,
            dataType:'json',
            success:function(data){
                location.reload();
            },
            error:function(){

            }
        }) ;
        } else {
            alert("层数总和不能超过20！");
        }
    })

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