/**
 * Created by Administrator on 2017/9/12.
 */
/*frontcookie();*/
$(function(){
    var setting = {
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pid"
            },
            key:{
                name:'chineseAbbreviation'
            }
        },
        view: {
            showIcon: false,
            showLine: false,
            dbClickExpand: true,
            showTitle: false,
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            addDiyDom: addDiyDom
        },
        treeNode: {
        },
        callback: {
            /*onClick: zTreeOnClick*/
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
            url: 'http://'+dPathUrl+'/content/slelectListFileByQuery.do',
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
    /*点击下载*/
});
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
    if(!!array) {
        if (array.indexOf(num) >= 0) {
            return 'checkedIcon';
        } else {
            return '';
        }
    }
}
