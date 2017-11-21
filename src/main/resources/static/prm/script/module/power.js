/**
 * Created by Administrator on 2017/11/16.
 */
/*backCookie();*/
menuActive('power');
$(function(){
    //加载公司
    getComList('company');
    //根据公司加载部门
    $('#company').change(function(){
        $('#companyId').val($(this).val());
        $('#dempId').val('');
        getDempByCom('demp',$('#company').val());
        /*$('.listBox').empty();*/
    });
    $('#demp').change(function(){
        $('#dempId').val($(this).val());
        /*$('.listBox').empty();*/
    });
    //搜索
    $('#seek').click(function(){
        power.queryPost();
    });
    //选中岗位,加载员工和菜单
    $('.postList').on('click','.postIn',function(){
        var postId = $(this).attr('post-id');
        power.queryStaff(postId);
        power.queryPower(postId);
    })

    $('#confirm').click(function(){
        power.confirm();
    })
});
var power = {
    queryPost:function(){ //根据公司和部门查询岗位
        $.ajax({
            type: "get",
            url: 'http://'+oaPathUrl+'/oa/post/list.do', //请求的处理数据
            dataType:'json',
            data: {
                companyId:$('#company').val(),
                dempId:$('#demp').val()
            },
            success:function(data){
                var arrText = doT.template($("#postTemplate").text());
                $('.postList').html(arrText(data.data.posts));
            },
            error:function(data){

            }
        })
    },
    queryStaff:function(id){//根据岗位查询用户
        $.ajax({
            type: "get",
            url: 'http://'+oaPathUrl+'/oa/user/selectUserByPostId.do', //请求的处理数据
            dataType:'json',
            data: {
                postIds:id
            },
            success:function(data){
                var arrText = doT.template($("#staffTemplate").text());
                $('.staffList').html(arrText(data.data));
            },
            error:function(data){

            }
        })
    },
    queryPower:function(){ //根据postId 查询权限
        $('.nemuList').empty();
        $('.btnList').empty();
        $.ajax({
            type: "get",
            url: 'http://'+gPathUrl+'/auth/menu/findMenuByPostId',
            dataType:'json',
            data: {
                postId:''
            },
            success:function(data){
                console.log(data)
                var menuStr = '';
                var btnStr = '';
                $.each(data.data,function(index,value){
                    if(parseInt(value.isMenu) == 1){
                        menuStr = menuStr +'<p><input data-id="'+value.id+'" type="checkbox" '+ (parseInt(value.checks)==1?'checked':'')+'><label for="">'+value.name+'</label></p>';
                    }else if(parseInt(value.isMenu) == 0){
                        btnStr = btnStr + '<p><input data-id="'+value.id+'" type="checkbox"'+ (parseInt(value.checks)==1?'checked':'')+'><label for="">'+value.name+'</label></p>';
                    }
                });
                $(''+menuStr).appendTo('.nemuList');
                $(''+btnStr).appendTo('.btnList')
            },
            error:function(data){

            }
        })
    },
    queryPostAfterUser:function(){
        $.ajax({
            type: "get",
            url: 'http://'+gPathUrl+'/auth/selectPostByQury.do', //请求的处理数据
            xhrFields:{
                withCredentials: true
            },
            crossDomain: true,//支持跨域发送cookie
            jsonp: 'callback',
            dataType:'jsonp',
            data: {
                companyId:$('#company').val(),
                dempId:$('#demp').val()
            },
            success:function(data){
                var arrText = doT.template($("#postTemplate").text());
                $('#post_body').html(arrText(data));
                isCheck();
            },
            error:function(data){

            }
        })
    },
    confirm:function(){
        var checkedId = $('input:radio[name="post"]:checked').attr('post-id');
        console.log(checkedId);
        var checkedMenuId = [];
        var options = {
            url : 'http://'+gPathUrl+'/auth/menu/editPostAuthority',
            type : "get",
            dataType:'json',
            /*data:{
                postId:,
                menuIds:,
            },*/
            success : function(data) {
                if(data.code =='200'){
                    alert(data.data)
                }else{
                    alert(data.data)
                }
            },error:function(data){
                alert('保存失败请重试！')
            }
        };
        $("#jvForm").ajaxSubmit(options); // jquery.form.js提价
    }
};