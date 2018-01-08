/**
 * Created by Administrator on 2017/11/16.
 */
backCookie();
menuActive('power');
$(function(){
    /*加载公司*/
    getComList('#company');
    getComList('#companyTo');
    /*根据公司加载部门*/
    $('#company').change(function(){
        if(!!$(this).val()){
            getDempByCom('#demp',$('#company').val());
        }else{
            $('#demp').empty().append('<option value="">请选择公司</option>');
        }
        $('.list').empty();
    });
    $('#demp').change(function(){
        $('.list').empty();
    });
    /*公司改变时*/
    $('#companyTo').change(function(){
        if(!!$(this).val()){
            getDempByCom('#dempTo',$('#companyTo').val());
        }
        $('#dempTo').empty().append('<option value="">请选择部门</option>');
        $('#postTo').empty().append('<option value="">请选择岗位</option>');
        $('#staffTo').empty().append('<option value="">请选择员工</option>');
        $('.list-r').empty();
    });
    /*根据公司和部门加载岗位*/
    $('#dempTo').change(function(){
        if(!!$(this).val()){
            getPostByComAndDemp('#postTo',$('#companyTo').val(),$(this).val());
        }
        $('#postTo').empty().append('<option value="">请选择岗位</option>');
        $('#staffTo').empty().append('<option value="">请选择员工</option>');
        $('.list-r').empty();
    });
    /*根据岗位加载员工*/
    $('#postTo').change(function(){
        var postId = $(this).val();
        if(!!postId){
            power.queryStaffSelect(postId);//加载员工
        }
        $('#staffTo').empty().append('<option value="">请选择员工</option>');
        $('.list-r').empty();
    });
    /*根据员工加载权限*/
    $('#staffTo').change(function(){
        var staffId = $(this).val();
        var postId = $('#postTo').val();
        if(!!staffId){
            power.queryPowerByStaff($('#postId').val(),staffId);
        }else{
            $('.nemuList').empty();
            $('.btnList').empty();
        }
    });

    /*搜索*/
    $('#seek').click(function(){
        power.queryPost();
        postId = null;
    });
    /*选中岗位,加载员工*/
    $('.postList').on('click','.postIn',function(){
        postId = $(this).attr('post-id');
        power.queryStaff(postId);
        $('.nemuList').empty();
        $('.btnList').empty();
        $('#postTo').empty().append('<option value="">请选择岗位</option>');
        $('#staffTo').empty().append('<option value="">请选择员工</option>');
    });
    /*选中员工,加载菜单*/
    $('.staffList').on('click','.staffIn',function(){
        var staffId = $(this).attr('staff-id');
        power.queryPowerByStaff(postId,staffId);
        $('#postTo').empty().append('<option value="">请选择岗位</option>');
        $('#staffTo').empty().append('<option value="">请选择员工</option>');
    });
    $('#confirm').click(function(){
        power.confirm();
    });
    $('#menuList').on('click','.nemu',function(){
        power.queryBtnByMenu();
    });
    $('#cancel').click(function(){
        var checkedPost = $('input:radio[name="post"]:checked').attr('post-id');
        var checkedStaff = $('input:radio[name="staff"]:checked').attr('staff-id');
        if(!!checkedPost && !!checkedStaff){
            power.queryPowerByStaff(checkedPost,checkedStaff);
        }else{
            $('.nemuList').empty();
            $('.btnList').empty();
        }
        $('#companyTo').empty().append('<option value="">请选择公司</option>');
        $('#dempTo').empty().append('<option value="">请选择部门</option>');
        $('#postTo').empty().append('<option value="">请选择岗位</option>');
        $('#staffTo').empty().append('<option value="">请选择员工</option>');
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
    queryStaffSelect:function(id){//根据岗位查询用户
        $.ajax({
            type: "get",
            url: 'http://'+oaPathUrl+'/oa/user/selectUserByPostId.do', //请求的处理数据
            dataType:'json',
            data: {
                postIds:id
            },
            success:function(data){
                $.each(data.data, function (index, value) {
                    var option =$('<option value="'+value.id+'" >'+value.username+'</option>');
                    $('#staffTo').append(option);
                });
            },
            error:function(data){

            }
        })
    },
    queryPower:function(postId){ //根据postId ,userId查询权限
        $('.nemuList').empty();
        $('.btnList').empty();
        $.ajax({
            type: "get",
            url: 'http://'+gPathUrl+'/auth/menu/findMenuByPostId',
            dataType:'json',
            data: {
                postId:postId
            },
            success:function(data){
                var menuStr = '';
                var btnStr = '';
                $.each(data.data,function(index,value){
                    if(parseInt(value.isMenu) == 1){
                        menuStr = menuStr +'<p><input class="nemu nemuIn" data-id="'+value.id+'" type="checkbox" '+ (parseInt(value.checks)==1?'checked':'')+'><label for="">'+value.name+'</label></p>';
                    }else if(parseInt(value.isMenu) == 0 && value.checks == 1){
                        btnStr = btnStr + '<p><input class="nemuIn" data-id="'+value.id+'" type="checkbox" checked><label for="">'+value.name+'</label></p>';
                    }
                    else if(parseInt(value.isMenu) == 0 && value.checks == 0){
                        btnStr = btnStr + '<p><input class="nemuIn" data-id="'+value.id+'" type="checkbox"><label for="">'+value.name+'</label></p>';
                    }
                });
                $(''+menuStr).appendTo('.nemuList');
                $(''+btnStr).appendTo('.btnList')
            },
            error:function(data){

            }
        })
    },
    queryPowerByStaff:function(postId,userId){ //根据postId ,userId查询权限
        $('.nemuList').empty();
        $('.btnList').empty();
        $.ajax({
            type: "get",
            url: 'http://'+gPathUrl+'/auth/menu/findMenuByUserId',
            dataType:'json',
            data: {
                postId:postId,
                userId:userId
            },
            success:function(data){
                var menuStr = '';
                var btnStr = '';
                $.each(data.data,function(index,value){
                    if(parseInt(value.isMenu) == 1){
                        menuStr = menuStr +'<p><input class="nemu nemuIn" data-id="'+value.id+'" type="checkbox" '+ (parseInt(value.checks)==1?'checked':'')+'><label for="">'+value.name+'</label></p>';
                    }else if(parseInt(value.isMenu) == 0 && value.checks == 1){
                        btnStr = btnStr + '<p><input class="nemuIn" data-id="'+value.id+'" type="checkbox" checked><label for="">'+value.name+'</label></p>';
                    }
                    else if(parseInt(value.isMenu) == 0 && value.checks == 0){
                        btnStr = btnStr + '<p><input class="nemuIn" data-id="'+value.id+'" type="checkbox"><label for="">'+value.name+'</label></p>';
                    }
                });
                console.log(menuStr);
                $(''+menuStr).appendTo('.nemuList');
                $(''+btnStr).appendTo('.btnList')
            },
            error:function(data){

            }
        })
    },
    queryBtnByMenu:function(){ //根据菜单动态加载按钮
        var checkedPost = $('input:radio[name="post"]:checked').attr('post-id');
        var checkedMenu = [];
        $('.nemu').each(function(index,value){
            if($(this).prop('checked')){
                checkedMenu.push(parseInt($(this).attr('data-id')));
            }
        });
        $.ajax({
            type: "get",
            url: 'http://'+gPathUrl+'/auth/menu/findButtonByMenuIds', //请求的处理数据
            dataType:'json',
            data: {
                postId:checkedPost,
                menuIds:checkedMenu.join(',')
            },
            beforeSend:function(){
                if(!checkedPost){
                    alert('请选择岗位再确定！');
                    return false;
                }
            },
            success:function(data){
                var btnStr = '';
                if(!!data.data){
                    $.each(data.data,function(index,value){
                        if(parseInt(value.checks) == 1){
                            btnStr = btnStr + '<p><input class="nemuIn" data-id="'+value.id+'" type="checkbox" checked><label for="">'+value.name+'</label></p>';
                        }else if(parseInt(value.checks) == 0){
                            btnStr = btnStr + '<p><input class="nemuIn" data-id="'+value.id+'" type="checkbox"><label for="">'+value.name+'</label></p>';
                        }
                    });
                    $('.btnList').empty().append(btnStr);
                }else{
                    $('.btnList').empty();
                }
            },
            error:function(data){

            }
        })
    },
    confirm:function(){
        var checkedId = $('input:radio[name="post"]:checked').attr('post-id');
        var checkedStaff = $('input:radio[name="staff"]:checked').attr('staff-id');
        var checkedMenuId = [];
        $('.nemuIn').each(function(index,value){
            if($(this).prop('checked')){
                checkedMenuId.push(parseInt($(this).attr('data-id')));
            }
        });
        $.ajax({
            url : 'http://'+gPathUrl+'/auth/menu/editPostAuthority',
            type : "get",
            dataType:'json',
            data:{
             postId:checkedId,
             userId:checkedStaff,
             menuIds:checkedMenuId.join(',')
             },
            beforeSend:function(){
                if(!checkedId){
                    alert('请选择岗位再确定！');
                    return false;
                }
            },
            success:function(data){
                if(data.code == 200){
                    alert(data.msg);
                    window.location.reload();
                }
            },
            error:function(data){

            }
        });
    }
};