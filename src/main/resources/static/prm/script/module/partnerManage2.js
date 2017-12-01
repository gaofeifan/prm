/**
 * Created by Administrator on 2017/9/12.
 */
/*frontcookie();*/
$(function(){
    var setting = {
        check: {
            enable: true,
            chkboxType: { "Y": "", "N": "" }
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid"
            },
            key:{
                name:'chineseAbbreviation',
                title: "chineseName"
            }
        },
        view: {
            showIcon: false,
            showLine: false,
            dbClickExpand: true,
            showTitle:true,
            /*addHoverDom: addHoverDom,
             removeHoverDom: removeHoverDom,*/
            addDiyDom: addDiyDom
        },
        treeNode: {
        },
        callback: {
            onClick: zTreeOnClick
            //beforeDrop: zTreeBeforeDrop
        }
    };
    function getDocList() {
        $.ajax({
            url: 'http://'+gPathUrl+'/partner/details/selectPartnerDetailsList',
            type: 'get',
            dataType:'json',
            data:{
                /*email:$.cookie('front_useremail'),
                 expiry:'',
                 isAdmin:''*/
            },
            success: function (data) {
                console.log(data)
                if(data.code == 200) {
                    var zNodes = data.data;
                    $.fn.zTree.init($("#tree"), setting, zNodes);
                }else{

                }
            },
            error: function (msg) {

            }
        });
    }
    getDocList();
    //关键字查询
    var selected = [];
    $('#query').click(function(){
        var checkedOrderNoList = [];
        $('.sortBox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).attr("data-orderNo") != "undefined") {
                    checkedOrderNoList.push($(n).attr('data-orderNo'));
                }
            }
        });
        $.ajax({
            url: 'http://'+gPathUrl+'/partner/details/selectListByQuery',
            type: 'get',
            dataType:'json',
            data:{
                name:$('#mainPoint').val(),
                offPartner:'',
                partnerCategory:checkedOrderNoList.join(','),
                blacklistPartner:''
            },
            success: function (data) {
                $('#screen').show();
                if(data.code == '200'){
                    if(data.data.length >0){
                        var arrText = doT.template($("#selectTemplate").text());
                        $('#selected').html(arrText(data));
                    }
                }
            },
            error: function (msg) {

            }
        });
    });
    //失效文件查询
    $('#lose').click(function(){
        $('#screen').show();
        $.ajax({
            url: 'http://'+gPathUrl+'/content/slelectListFileByQuery.do',
            type: 'get',
            xhrFields:{
                withCredentials: true
            },
            crossDomain: true,//支持跨域发送cookie
            jsonp: 'callback',
            dataType:'jsonp',
            data:{
                email:$.cookie('front_useremail'),
                keyword:$('#keyword').val(),
                expiry:'1',
                isAdmin:''
            },
            success: function (data) {
                var arrText = doT.template($("#selectTemplate").text());
                $('#selected').html(arrText(data));
            },
            error: function (msg) {

            }
        });
    });
    //显示隐藏
    $('#toggle').click(function(){
        $('#screen').toggle();
    });
    //全部收起
    $("#collapseAllBtn").on("click",function(){
        zTree = $.fn.zTree.getZTreeObj("tree");
        zTree.expandAll(false)
    });
    //到顶部
    $("#scrollTop").on("click",function(){
        $('#tree_box').scrollTop(0);
    });
    //到底部
    $("#scrollBottom").on("click",function(){
        $('#tree_box').scrollTop($('#tree').height());
    });
    /*点击删除*/
    $('#delete').click(function(){
        var zTree = $.fn.zTree.getZTreeObj("tree"),
            nodes = zTree.getChangeCheckedNodes();
        if(nodes.length< 1){
            alert('请选择一个节点！');
        }else if(nodes.length == 1){
            var idGroup = [];
            $.each(nodes,function(index,value){
                idGroup.push(value.id)
            });
            $.ajax({
                type: 'get',
                url: 'http://'+gPathUrl+'/partner/details/isDeletePartnerDetails',
                data:{
                    id:idGroup.join(',')
                },
                dataType:'json',
                success: function (data) {
                    if(data.code =='200'){
                        if(data.data){
                            var win = new Window().confirm({
                                 title:'删除确认',
                                 content:'<p style="text-align: center;">确实是否删除该目录或者文件！</p>',
                                 width:420,
                                 height:280,
                                 hasCloseBtn:false,
                                 text4ConfirmBtn:'确认',
                                 text4CancelBtn:'取消',
                                 dragHandle:'.window_header',
                                 skinClassName:'window_skin_a'
                             }).on('confirm',function(){
                                 $.ajax({
                                     type: 'get',
                                     url: 'http://'+gPathUrl+'/partner/details/deletePartnerDetailsById',
                                     data:{
                                         id:idGroup.join(',')
                                    },
                                     dataType:'json',
                                     success: function (data) {
                                         getDocList();
                                         $('.window_cancelBtn').trigger('click');
                                     },
                                     error:function(){
                                     }
                                 })
                             }).on('cancel',function(){
                             })
                        }else{
                            alert('该节点不允许删除！')
                        }
                    }
                },
                error:function(){
                }
            });
        }else if(nodes.length > 1){
            alert('只能选择一个节点！')
        }
    });

    /*点击转移*/
    $('#transferBtn').click(function(){
        var zTree = $.fn.zTree.getZTreeObj("tree"),
            nodes = zTree.getChangeCheckedNodes();
        if(nodes.length< 1){
            alert('请至少选择一个节点！');
        }else if(nodes.length >=1){
            var SelIdGroup = [];
            $.each(nodes,function(index,value){
                SelIdGroup.push(value.id)
            });
            $.ajax({
                url: 'http://'+gPathUrl+'/partner/details/selectShiftFile',
                type: 'get',
                dataType:'json',
                data:{
                    ids:SelIdGroup.join(',')
                },
                success: function (data) {
                    $('#transfer').show();
                    $('.transfer-box').empty();
                    $.each(data.data,function(index,value){
                        var str = '<p class="node node'+value.id+'" onclick="isDeleteSel('+value.id+')">'+value.chineseAbbreviation+'</p>'
                        $(str).appendTo('.transfer-box')
                    });
                },
                error: function (msg) {

                }
            });
        }
    });
    /*转移删除选中*/
    $('#tr-delete').click(function(){
        transferObj.remove();
    });
    /*转移取消*/
    $('#tr-cancel').click(function(){
        getDocList();
        $('#transfer-box').empty();
        $('input[type="text"]').val('');
        $('#transfer').hide();
    });
    /*点击转移至*/
    $('#transfer_btn').click(function(){
        /* $('#transfer').hide();*/
        //从新加载结构树，文件不能选择
        $.ajax({
            url: 'http://'+gPathUrl+'/partner/details/selectPartnerDetailsList',
            type: 'get',
            dataType:'json',
            data:{
            },
            success: function (data) {
                if(data.code == 200) {
                    var zNodes1 = data.data;
                    $.fn.zTree.init($("#tree"), setting, zNodes1);
                    $('#tree').on('click',clickTree)
                }else{

                }
            },
            error: function (msg) {
            }
        });
    });
    /*转移确定*/
    $('#tr-confirm').click(function(){
        if($('#toId').val().length <=0){
            alert('请选择转移至的节点！')
        }else{
            $.ajax({
                url: 'http://'+gPathUrl+'/partner/details/shiftPartnerDetailsFileByIds',
                type: 'get',
                dataType:'json',
                data:{
                    id:$('#toId').val()
                },
                success: function (data) {
                    if(data.code == 200){
                        $('#tr-cancel').trigger('click');
                    }else{
                        alert('您所选择的目录/文件中已有被转移项 请刷新页面获取最新的信息!')
                    }
                    getDocList();
                },
                error: function (msg) {
                }
            });
        }
    });

    /*点击新增*/
    $('.new').click(function(){
        /*一级目录新增*/
        var zTree = $.fn.zTree.getZTreeObj("tree"),
            nodes = zTree.getChangeCheckedNodes();
        if(nodes.length< 1){
            location.hash = vipspa.stringify('partnerNew',{id:null});
        }else if(nodes.length >1)
        {
            alert('只能选择一个节点！');
        }
        else if(nodes.length= 1){//选中一个节点
            location.hash = vipspa.stringify('partnerNew',{id:nodes[0].id});
        }
    });

    /*点击修改*/
    $('.alter').click(function(){
        var zTree = $.fn.zTree.getZTreeObj("tree"),
            nodes = zTree.getChangeCheckedNodes();
        if(nodes.length< 1){
            alert('请选择一个节点！');
        }else if(nodes.length >1){
            alert('只能选择一个节点！');
        } else if(nodes.length= 1){
            location.hash = vipspa.stringify('partnerEdit',{id:nodes[0].id});
        }
    });
});
function clickTree (){
    var zTree = $.fn.zTree.getZTreeObj("tree"),
        node = zTree.getChangeCheckedNodes();
    if(node.length > 0){
        /*$('#transfer').show();*/
        $('#toId').val(node[0].id);
        $('#goal').val(node[0].chineseAbbreviation);
        $("#tree").unbind("click",clickTree);
    }
}

/**
 *点击跳转
 * @param event
 * @param treeId
 * @param treeNode
 */
function zTreeOnClick(event, treeId, treeNode) {
    location.hash = vipspa.stringify('partnerNew',{id:treeNode.id});
}
/*
 转移选中的文件是都可以删除
 */
function isDeleteSel(id){
    $('.node').removeClass('active');
    $('.node'+id+'').addClass('active');
    $('#deleteSelId').val(id);
}
var transferObj = {
    remove:function(){
        $.ajax({
            url: 'http://'+gPathUrl+'/partner/details/deleteShiftFile',
            type: 'get',
            dataType:'json',
            data:{
                ids	:$('#deleteSelId').val()
            },
            success: function (data) {
                if(data.status){
                    $('.transfer-box').empty();
                    $.each(data.data,function(index,value){
                        var str = '<p class="node node'+value.id+'" onclick="isDeleteSel('+value.id+')">'+value.chineseAbbreviation+'</p>'
                        $(str).appendTo('.transfer-box')
                    });
                }else{
                    alert('不能删除选中！')
                }
            },
            error: function (msg) {

            }
        });
    }
};

function addHoverDom(treeId, treeNode) {
    if(treeNode.isFile ){
        var aObj = $("#" + treeNode.tId + "_a");
        aObj.css("position",'relative');
        if ($("#hoverBtn_"+treeNode.id).length>0) return;
        var hoverStr = "<button type='button' class='download hoverBtn' id='hoverBtn_" + treeNode.id
            + "' title='"+treeNode.name+"' onfocus='this.blur();'onclick='download("+treeNode.id+")'>下载</button>";
        aObj.append(hoverStr);
    }
}
function removeHoverDom(treeId, treeNode) {
    $("#hoverBtn_"+treeNode.id).unbind().remove();
    $("#hoverBtn__space_" +treeNode.id).unbind().remove();
}
function openShow(i){
    var  zTree_Menu = $.fn.zTree.getZTreeObj("tree");
    var node = zTree_Menu.getNodeByParam("id",i );
    zTree_Menu.selectNode(node,false);//指定选中ID的节点
    zTree_Menu.expandNode(node, true, false);//指定选中ID节点展开
}
function addDiyDom(treeId, treeNode) {
    var aIco = $("#" + treeNode.tId + "_ico");
    var aObj = $("#" + treeNode.tId + "_a");
    var aSpan = $("#" + treeNode.tId + "_span");
    aObj.css({'width':'100%','border-bottom':'1px solid #e7dfe6'});
        if(treeNode.isDisable){
            aSpan.css('color','red');
            aIco.css('background-position','-242px -168px');
            if ($("#diyBtn_"+treeNode.id).length>0) return;
            var editStr = "<div class='info info0' id='diyBtn_space_" +treeNode.id+ "'><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info1' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info2' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info3' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info4' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info5' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info6' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info7' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info8' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info9' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>";
            aObj.append(editStr);
        }else{
            aIco.css('background-position','-242px -168px');
            if ($("#diyBtn_"+treeNode.id).length>0) return;
            var editStr1 = "<div class='info info0' id='diyBtn_space_" +treeNode.id+ "'><i class='"+isCheck('0',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info1' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('1',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info2' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('2',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info3' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('3',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info4' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('4',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info5' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('5',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info6' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('6',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info7' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('7',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info8' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('8',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info9' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('9',treeNode.partnerCategorys) +"'></i></div>";
            aObj.append(editStr1);
        }
}
function download(id){
    $.ajax({
        url: 'http://'+dPathUrl+'/content/downloadFile.do',
        type: 'get',
        xhrFields:{
            withCredentials: true
        },
        crossDomain: true,//支持跨域发送cookie
        jsonp: 'callback',
        dataType:'jsonp',
        data:{
            email:$.cookie('front_useremail'),
            id:id
        },
        success: function (data) {
            if(data.code == 200){
                alert(data.data);
            }
        },
        error: function (msg) {
        }
    });
}

function isCheck(num,array){
    if(!!array){
        if(array.indexOf(num)>=0){
            return 'checkedIcon';
        }else{
            return '';
        }
    }
}
