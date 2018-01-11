
/**
 * Created by Administrator on 2017/9/13.
 */
//同步  加载公司下拉框
function getComList(dom) {
    $.ajax({
        type: 'get',
        async:false,
        url: 'http://'+eamsPathUrl+'/base/frameWork/selectCompanyByQuery',
        success: function (data) {
            if(data.code ==200) {
                var str ;
                $.each(data.data, function (index, value) {
                    var option =$('<option value="'+value.id+'" >'+value.name+'</option>');
                    $(dom).append(option);
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
//同步  加载利润中心
function selectLastCompanyList(dom) {
    $.ajax({
        type: 'get',
        async:false,
        url: 'http://'+eamsPathUrl+'/base/frameWork/selectLastCompanyList',
        success: function (data) {
            if(data.code ==200) {
                var str ;
                $.each(data.data, function (index, value) {
                    var option =$('<option value="'+value.id+'" >'+value.name+'</option>');
                    $(dom).append(option);
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
function getDempByCom(dempDom,companyId){
    $(dempDom).empty().append('<option value="">请选择部门</option>');
    $.ajax({
        type: 'get',
        url: 'http://'+eamsPathUrl+'/base/frameWork/selectDeptByQuery',
        data:{
            companyId:companyId
        },
        success: function (data) {
            $.each(data.data, function (index, value) {
                var option =$('<option value="'+value.id+'" >'+value.name+'</option>');
                $(dempDom).append(option);
            });
        },
        error: function () {
            alert('获取数据失败');
        }
    });
}
/**
 *根据公司和部门查询岗位
 * @param dempDom
 * @param companyId
 * @param dempId
 */
function getPostByComAndDemp(dempDom,companyId,dempId){
    $(dempDom).empty().append('<option value="">请选择岗位</option>');
    $.ajax({
        type: 'get',
        url: 'http://'+eamsPathUrl+'/base/position/selectPositionByQuery',
        data: {
            companyId:companyId,
            deptId:dempId
        },
        success: function (data) {
            $.each(data.data, function (index, value) {
                var option =$('<option value="'+value.id+'" >'+value.name+'</option>');
                $(dempDom).append(option);
            });
        },
        error: function () {
            alert('获取数据失败');
        }
    });
}

var mm = {
    getIndexWithArr:function(_arr,_obj){
        var len = _arr.length;
        for(var i = 0; i < len; i++)
        {
            if(_arr[i] == _obj)
            {
                return parseInt(i);
            }
        }
        return -1;
    },
    removeObjWithArr:function (_arr,_objId) {
        var length = _arr.length;
        for(var i = 0; i < length; i++)
        {
            if(_arr[i].id == _objId)
            {
                if(i == 0)
                {
                    _arr.shift(); //删除并返回数组的第一个元素
                    return;
                }
                else if(i == length-1)
                {
                    _arr.pop();  //删除并返回数组的最后一个元素
                    return;
                }
                else
                {
                    _arr.splice(i,1); //删除下标为i的元素
                    return;
                }
            }
        }
    }
};
/**
 *加载信用等级
 */
function qualityRating(){
    $.ajax({
        type: 'get',
        url: 'http://' + gPathUrl + '/user/level',
        dataType: 'json',
        async:false,
        success: function (data) {
            var optionStr = '';
            $.each(data.data,function(index,value){
                if(parseInt(value.effectiveness)== 1){
                    optionStr = optionStr + '<option value="'+value.level+'-'+value.protocolType+'">'+value.level+'-'+value.protocolType+'</option>';
                }
            });
           $('.wbkhCreditRating').append(optionStr);
            /*
            if(data.data[0].protocolType =='协议/保函'){
                $('.wbkhCreditPeriod').attr('disabled',false);
                $('.wbkhLineCredit').attr('disabled',false);
                $('.wbkhIsPayForAnother').attr('disabled',false);
            }else if(data.data[0].protocolType =='签约在途 '){
                $('.wbkhIsPayForAnother').attr('disabled',false);
            }else{
                $('.wbkhLineCredit').attr('disabled',true);
                $('.aiCreditPeriod ').attr('disabled',true);
                $('.businessItem input').attr('disabled',true);
            }*/
        }
    });
}

/**
 * 根据信用等级 加载默认额度和默认期限
 * @param val
 */
function getDefault(val){
    $.ajax({
        type: 'get',
        async:false,
        url: 'http://' + gPathUrl + '/user/selectLevelByName',
        dataType: 'json',
        data:{
            name:val
        },
        success: function (data) {
            if(data.code == 200){
                $('.wbkhLineCredit ').val(data.data.defaultQuota);
                $('.businessBox input').val(data.data.defaultTtime);
            }
        }
    });
}
/**
 * 数组中删除某一项
 * @param arr
 * @param item
 * @returns {Array.<T>|*}
 */
function remove(arr,item){
    if(arr.length){
        var index = arr.indexOf(item);
        if(index>-1){
            return arr.splice(index,1)
        }
    }
}

function getInternalCustomer(dom) {
    $.ajax({
        type: 'get',
        async:false,
        url: 'http://192.168.4.213:8082/base/frameWork/selectCompanyByQuery',
        success: function (data) {
            if(data.code ==200) {
                var str ;
                $.each(data.data, function (index, value) {
                    var option =$('<option value="'+value.id+'" >'+value.name+'</option>');
                    $(dom).append(option);
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
