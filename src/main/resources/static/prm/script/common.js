
/**
 * Created by Administrator on 2017/9/13.
 *///异步  加载公司下拉框
function getComList(idSelector) {
    $.ajax({
        type: 'get',
        async:false,
        url: 'http://'+oaPathUrl+'/oa/company/list.do',
        success: function (data) {
            if(data.code ==200) {
                var str ;
                $.each(data.data, function (index, value) {
                    var option =$('<option value="'+value.id+'" >'+value.name+'</option>');
                    $('#'+idSelector +'').append(option);
                });
            }else{
                alert("资源路径出错");
            }
        },
        error: function () {
            alert('获取数据失败');
        }
    });
}
// 根据公司加载部门
function getDempByCom(demp,companyId){
    $('#'+demp).empty().append('<option value="">请选择部门</option>');
    $.ajax({
        type: 'get',
        url: 'http://'+oaPathUrl+'/oa/demp/findDemps.do',
        data:{
            companyId:companyId
        },
        success: function (data) {
            $.each(data.data.demps, function (index, value) {
                var option =$('<option value="'+value.id+'" >'+value.name+'</option>');
                $("#"+demp).append(option);
            });
        },
        error: function () {
            alert('获取数据失败');
        }
    });
}