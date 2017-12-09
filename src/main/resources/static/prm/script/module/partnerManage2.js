/**
 * Created by Administrator on 2017/9/12.
 */
frontcookie();
$(function(){
    menuActive('partnerManage');
    /*控制 新增 编辑 修改 删除 按钮*/
    $.ajax({
        url: 'http://'+gPathUrl+'/auth/menu/findMenuOrButtonByPostId',
        type: 'get',
        async:false,
        data:{
            isMenu:false,
            menuId:6,
            email:$.cookie('front_useremail')
        },
        success: function (resp) {
            $.each(resp.data,function(index,value){
                if(value.name == "合作伙伴管理 - 新增"){
                    $('.new').show();
                }else if(value.name == "合作伙伴管理 - 修改"){
                    $('.alter').show();
                }else if(value.name == "合作伙伴管理 - 转移"){
                    $('#transferBtn').show();
                }else if(value.name == "合作伙伴管理 - 删除"){
                    $('#delete').show();
                }else if(value.name == "合作伙伴管理 - 查询"){
                    $('#query').show();
                    getDocList();
                }
            });
        }
    });

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

    var partnerObj = {
        query:function(isStop,isBlack){
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
                    offPartner:isStop,
                    partnerCategory:checkedOrderNoList.join(','),
                    blacklistPartner:isBlack
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
        }
    };
    //关键字和分类查询
    $('#query').click(function(){
        $('#selected').empty();
        partnerObj.query();
    });
    //查询停用
    $('#queryStop').click(function(){
        $('#selected').empty();
        partnerObj.query(1,0);
    });
    //黑名单
    $('#queryBlack').click(function(){
        $('#selected').empty();
        partnerObj.query(0,1);
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
                                 content:'<p style="text-align: center;">确实是否删除该合作伙伴！</p>',
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
                                         id:idGroup.join(','),
                                         email:$.cookie('front_useremail')
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
                    id:$('#toId').val(),
                    email:$.cookie('front_useremail')
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

    /*点击关闭*/
    $('#close').click(function(){
        $('#infoWall').hide();
    })
});

var addressList = [];
var addressObj = {
    getAddressList:function(){
        $('.addressList').empty();
        $.each(addressList,function(index,value){
            var str= '<div data-listId="'+value.id+'" class="list clearfix">\
                <div style="width:6%;" class="no"><span>'+(index+1)+'</span></div>\
                <div class="addressType"><span>'+value.addressType+'</span></div>\
                <div style="width:16%;" class="short"><span>'+value.abbreviation+'</span></div>\
                <div style="width:40%;" class="address"><span>'+value.address+'</span></div>\
                <div class="postcode"><span>'+value.zipCode+'</span></div>\
                </div>';
            $('.addressList').append(str);
        });
    }
};

/*联系人*/
var contactsList = [];
var contactsObj = {
    getContactsList:function(){
        $('.contactList').empty();
        $.each(contactsList,function(index,value){
            var str= '<div data-listId="'+value.id+'" class="list clearfix">\
                <div class="no"><span>'+(index+1)+'</span></div>\
                <div class="name"><span>'+value.name+'</span></div>\
                <div style="width:8%;" class="obligation"><span>'+value.obligation+'</span></div>\
                <div class="demp"><span>'+value.demp+'</span></div>\
                <div style="width: 8%;" class="duty"><span>'+value.duty+'</span></div>\
                <div class="tel"><span>'+value.fixPhone+'</span></div>\
                <div class="phone"><span>'+value.phone+'</span></div>\
                <div style="width:16%;" class="email"><span>'+value.email+'</span></div>\
                <div style="width:28%;" class="address2"><span>'+value.address+'</span></div>\
                </div>';
            $('.contactList').append(str);
        });
    }
};
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
    //清空表单
    $(':input','#checkForm')
        .not(':button, :submit, :reset, :hidden')
        .val('')
        .removeAttr('checked')
        .removeAttr('selected');
    $('#infoWall').show();
    $('body').scrollTop(0);
    /*回显各个字段的值*/
    seePartner.show(treeNode.id);
}
var seePartner = {
    show:function(treeNodeId){
        $.ajax({
            url: 'http://'+gPathUrl+'/partner/details/selectPartnerDetailsById',
            type: 'get',
            async:false,
            data:{
                id:treeNodeId
            },
            success: function (data) {
                $.ajax({
                    url: 'http://' + gPathUrl + '/partner/details/getParentCodeList',
                    type: 'get',
                    data: {
                        id:treeNodeId
                    },
                    success: function (data) {
                        $('input[name="code"]').val(data.data.join(''));//代码
                    }
                });
                $('#mnemonicCode').val(data.data.mnemonicCode);//助记码
                $('#chineseName').val(data.data.chineseName);//中文全称
                $('#chineseAbbreviation').val(data.data.chineseAbbreviation);//中文简称
                $('#englishName').val(data.data.englishName);//英文全称
                $('#englishAbbreviation').val(data.data.englishAbbreviation);//英文简称
                $('#financingCode').val(data.data.financingCode);//财务代码
                $('#dutyInput').val(data.data.receiverName);//提醒接受者
                $('#receiverId').val(data.data.receiverId);//提醒接受者ID
                $('#receiverName').val(data.data.receiverName);//提醒接受者
                //黑名单
                $('#blackList').val(data.data.isBlacklist);
                if(data.data.isBlacklist == 1){
                    $('#isBlacklist').attr('checked',true);
                }else{
                    $('#isBlacklist').attr('checked',false)
                }
                //停用
                $('#disable').val(data.data.isBlacklist);
                if(data.data.isDisable == 1){
                    $('#isDisable').attr('checked',true);
                }else{
                    $('#isDisable').attr('checked',false)
                }
                $('#disableRemark').val(data.data.disableRemark);//停用备注
                addressList = data.data.addressList;//联系地址
                contactsList = data.data.linkmansList;//联系人
                $('#scopeBusiness').val(data.data.scopeBusiness);//业务范畴
                $.each(data.data.scopeBusinesss,function(index,value){
                    $('.businessCheckbox').find("input:checkbox").each(function(i,n) {
                        if(value == $(n).val()){
                            $(n).attr('checked',true);
                        }
                    });
                });
                $('#partnerCategory').val(data.data.partnerCategory);//合作伙伴分类
                $.each(data.data.partnerCategorys,function(index,value){
                    $('.partnersCheckbox').find("input:checkbox").each(function(i,n) {
                        if(value == $(n).val()){
                            $(n).attr('checked',true);
                        }
                    });
                });
                $('#wbkhCustomerClass').val(data.data.wbkhCustomerClass);//客户分类
                $.each(data.data.wbkhCustomerClasss,function(index,value){
                    $('#customerClass').find("input:checkbox").each(function(i,n) {
                        if(value == $(n).val()){
                            $(n).attr('checked',true);
                        }
                    });
                });
                //代垫
                $('#wbkhIsPayForAnother').val(data.data.wbkhIsPayForAnother);
                if(data.data.wbkhIsPayForAnother == 1){
                    $('.wbkhIsPayForAnother').attr('checked',true);
                    $('.daiDian').attr('disabled',false);
                }else{
                    $('.wbkhIsPayForAnother').attr('checked',false);
                }
                $('.wbkhPaymentTerm').val(data.data.wbkhPaymentTerm);//代垫期限（天）
                $('.wbkhPaidAmount').val(data.data.wbkhPaidAmount);//代垫额度（万元）
                $('.wbkhCreditRating').val(data.data.wbkhCreditRating);//信用等级
                $('.wbkhTypeCreditPeriod').val(data.data.wbkhTypeCreditPeriod);//信用期限类型
                $('.wbkhCreditPeriod').val(data.data.wbkhCreditPeriod);//信用期限（天）
                $('.wbkhLineCredit').val(data.data.wbkhLineCredit);//信用额度(万元)
                $('.wbkhInvoiceType').val(data.data.wbkhInvoiceType);//开票类型
                $('.invoiceTypeTitle').text(data.data.wbkhInvoiceType);//开票类型Title
                $('.wbkhDepositBank ').val(data.data.wbkhDepositBank);//开户银行
                $('.headingCode ').val(data.data.headingCode);//纳税人识别码
                $('.wbkhBankAccount ').val(data.data.wbkhBankAccount);//银行账号
                $('.wbkhCompanyTel ').val(data.data.wbkhCompanyTel);//公司电话
                $('.wbkhCompanyAddress ').val(data.data.wbkhCompanyAddress);//公司地址
                $('.hwdlTaxReceipt ').val(data.data.hwdlTaxReceipt);//进项税票
                $('.hwdlTaxRate ').val(data.data.hwdlTaxRate);//进项税率%
                $('#gxcyrClassOfService').val(data.data.gxcyrClassOfService);//服务类别
                $.each(data.data.gxcyrClassOfServices,function(index,value){
                    $('.gxcyrClassOfServiceBox').find("input:checkbox").each(function(i,n) {
                        if(value == $(n).val()){
                            $(n).attr('checked',true);
                        }
                    });
                });
                //收货人
                $('input[name="sfhrIsConsignee"]').val(data.data.sfhrIsConsignee);
                if(data.data.sfhrIsConsignee == 1){
                    $('#sfhrIsConsignee').attr('checked',true);
                    $('.consigneeInput').find('input').attr('disabled',false);
                }else{
                    $('#sfhrIsConsignee').attr('checked',false);
                }
                $('#sfhrConsigneeNation').val(data.data.sfhrConsigneeNation)//收货人国家
                $('input[name="sfhrConsigneeContinent"]').val(data.data.sfhrConsigneeContinent)//收货人州
                $('input[name="sfhrConsigneeCity"]').val(data.data.sfhrConsigneeCity)//收货人城市
                $('input[name="sfhrConsigneeZipCode"]').val(data.data.sfhrConsigneeZipCode)//收货人邮编
                $('input[name="sfhrConsigneePhone"]').val(data.data.sfhrConsigneePhone)//收货人电话
                $('input[name="sfhrConsigneeAddress"]').val(data.data.sfhrConsigneeAddress)//收货人地址

                //与收货人地址相同
                $('input[name="sfhrIsConsigneesAddress"]').val(data.data.sfhrIsConsigneesAddress);
                if(data.data.sfhrIsConsignee == 1){
                    $('#sfhrIsConsigneesAddress').attr('checked',true);
                }else{
                    $('#sfhrIsConsigneesAddress').attr('checked',false);
                }
                //发货人
                $('input[name="sfhrIsShipper"]').val(data.data.sfhrIsConsignee);
                if(data.data.sfhrIsShipper == 1){
                    $('#sfhrIsShipper').attr('checked',true);
                    $('.shipperInput').find('input').attr('disabled',false);
                }else{
                    $('#sfhrIsShipper').attr('checked',false);
                }
                $('input[name="sfhrShipperNation"]').val(data.data.sfhrShipperNation)//发货人国家
                $('input[name="sfhrShipperContinent"]').val(data.data.sfhrShipperContinent)//发货人州
                $('input[name="sfhrShipperCity"]').val(data.data.sfhrShipperCity)//发货人城市
                $('input[name="sfhrShipperZipCode"]').val(data.data.sfhrShipperZipCode)//发货人邮编
                $('input[name="sfhrShipperPhone"]').val(data.data.sfhrShipperPhone)//发货人电话
                $('input[name="sfhrShipperAddress"]').val(data.data.sfhrShipperAddress)//发货人地址
                addressObj.getAddressList();
                contactsObj.getContactsList();
            }
        });
    }
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
            var editStr = "<div style='margin-right: 26px;' class='info info0' id='diyBtn_space_" +treeNode.id+ "'><i class='"+isCheck('结算对象',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info1' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('收发货人',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info2' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('延伸服务供应商',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info3' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('不可控供应商',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info4' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('干线承运人',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info5' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('海外代理',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info6' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('互为代理',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info7' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('外部客户',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info8' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isflag(treeNode.isBlacklist) +"'></i></div>" +
                "<div class='info info9' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isflag(treeNode.isDisable) +"'></i></div>";
            aObj.append(editStr);
        }else if(treeNode.isBlacklist){
            aSpan.css('color','#d29351');
            aIco.css('background-position','-242px -168px');
            if ($("#diyBtn_"+treeNode.id).length>0) return;
            var editStr1 = "<div style='margin-right: 26px;' class='info info0' id='diyBtn_space_" +treeNode.id+ "'><i class='"+isCheck('结算对象',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info1' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('收发货人',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info2' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('延伸服务供应商',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info3' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('不可控供应商',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info4' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('干线承运人',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info5' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('海外代理',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info6' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('互为代理',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info7' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('外部客户',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info8' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isflag(treeNode.isBlacklist) +"'></i></div>" +
                "<div class='info info9' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isflag(treeNode.isDisable) +"'></i></div>";
            aObj.append(editStr1);
        }else{
            aIco.css('background-position','-242px -168px');
            if ($("#diyBtn_"+treeNode.id).length>0) return;
            var editStr2 = "<div style='margin-right: 26px;' class='info info0' id='diyBtn_space_" +treeNode.id+ "'><i class='"+isCheck('结算对象',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info1' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('收发货人',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info2' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('延伸服务供应商',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info3' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('不可控供应商',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info4' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('干线承运人',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info5' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('海外代理',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info6' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('互为代理',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info7' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isCheck('外部客户',treeNode.partnerCategorys) +"'></i></div>" +
                "<div class='info info8' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isflag(treeNode.isBlacklist) +"'></i></div>" +
                "<div class='info info9' id='diyBtn_space_" +treeNode.id+ "' ><i class='"+isflag(treeNode.isDisable) +"'></i></div>";
            aObj.append(editStr2);
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

function isCheck(name,array){
    if(!!array){
        if(array.indexOf(name)>=0){
            return 'checkedIcon';
        }else{
            return '';
        }
    }
}
function isflag(flagName){
        if(flagName == 1){
            return 'checkedIcon';
        }else if(flagName == 0){
            return '';
        }
}

