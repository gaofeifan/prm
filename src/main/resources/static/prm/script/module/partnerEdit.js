/**
 * Created by Administrator on 2017/11/22.
 */
frontcookie();
var id = null;
$(function(){
    $('body').scrollTop(0);
    var codes = $('.code');
    var nn = 0;//控制编辑的是哪个code
    var urlParameter = vipspa.parse();
    id = urlParameter.param.id;
    $('#partnerId').val(id);

    /*加载公司*/
    selectLastCompanyList('#profitCenter');
    /*加载信用等级*/
    qualityRating();

    var partnerCategorys = [];
    /*回显各个字段的值*/
    var gxcyrClassOfServiceArr = [];
    var wbkhInvoiceType ='';
    $.ajax({
        url: 'http://'+gPathUrl+'/partner/details/selectPartnerDetailsById',
        type: 'get',
        async:false,
        data:{
            id:id
        },
        success: function (data) {
            $('#pId').val(data.data.pid);
            $('#mnemonicCode').val(data.data.mnemonicCode);//助记码
            $('#chineseName').val(data.data.chineseName);//中文全称
            $('#chineseAbbreviation').val(data.data.chineseAbbreviation);//中文简称
            $('#englishName').val(data.data.englishName);//英文全称
            $('#englishAbbreviation').val(data.data.englishAbbreviation);//英文简称
            $('#financingCode').val(data.data.financingCode);//财务代码
            $('#currency').val(data.data.defaultCurrency);//默认币种
            $('#dutyInput').val(data.data.receiverName);//提醒接受者
            $('#receiverId').val(data.data.receiverId);//提醒接受者ID
            $('#receiverName').val(data.data.receiverName);//提醒接受者
            $('#profitCenter').val(data.data.profitsCenterId);//利润中心
            $('#startDate').val(data.data.maturityDateBegan);//开始时间
            $('#endDate').val(data.data.maturityDateEnd);//结束时间
            $('#filePath').val(data.data.filePath);//文件
            $('#fileName').val(data.data.fileName);//文件
            $('#download').attr('href','http://'+gPathUrl+'/upload/download?filePath='+data.data.filePath);//文件下载
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
            $('.disableRemark').val(data.data.disableRemark);//停用备注
            if(data.data.isBlacklistStatus){
                $('#isBlacklist').attr('disabled',true);
            }
            if(data.data.isDisableStatus){
                $('#isDisable').attr('disabled',true);
            }
            addressList = data.data.addressList;//联系地址
            contactsList = data.data.linkmansList;//联系人
            $('#scopeBusiness').val(data.data.scopeBusiness);//业务范畴
            $.each(data.data.scopeBusinesss,function(index,value){
                $('.businessCheckbox').find("input:checkbox").each(function(i,n) {
                   if(value == $(n).val()){
                       $(n).attr({'checked':true,'disabled':true});
                       $('.'+value).fadeIn();
                   }
                });
            });
            partnerCategorys = data.data.partnerCategory;
            if(partnerCategorys.indexOf('结算对象') > 0){
                $('.group1').attr('disabled',true);
            }else{
                $('.group2').attr('disabled',true);
            }
            $('#partnerCategory').val(data.data.partnerCategory);//合作伙伴分类
            $.each(data.data.partnerCategorys,function(index,value){
                $('.partnersCheckbox').find("input:checkbox").each(function(i,n) {
                    if(value == $(n).val()){
                        $(n).attr('checked',true);
                    }
                });
            });
            gxcyrClassOfServiceArr = data.data.gxcyrClassOfServices;
            if(data.data.partnerCategorys.indexOf('干线承运人') > -1){
                $('.classOfSer1').attr('checked',true);
                if(gxcyrClassOfServiceArr.indexOf('干线运输') <=-1){
                    gxcyrClassOfServiceArr.push('干线运输');
                }
            }
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
            if(!!data.data.wbkhCreditRating){
                if(data.data.wbkhCreditRating.slice(2,7) == '协议/保函'){
                    $('.wbkhCreditPeriod').attr('disabled',false);
                    $('.wbkhLineCredit').attr('disabled',false);
                    $('.wbkhIsPayForAnother').attr('disabled',false);
                    $('.businessBox').find('select,input').attr('disabled',false);
                }else if(data.data.wbkhCreditRating.slice(2,7) == '签约在途'){
                    $('.wbkhIsPayForAnother').attr('disabled',false);
                    $('.businessBox').find('select,input').attr('disabled',true);
                }else{
                    $('.businessBox').find('select,input').attr('disabled',true);
                }
            }
            $('.wbkhTypeCreditPeriod').val(data.data.wbkhTypeCreditPeriod);//信用期限类型
            $('.wbkhCreditPeriod').val(data.data.wbkhCreditPeriod);//信用期限（天）
            $('.wbkhLineCredit').val(data.data.wbkhLineCredit);//信用额度(万元)
            $('.useQuota').val(data.data.useQuota||0);//已用额度(万元)
            $('.wbkhInvoiceType').val(data.data.wbkhInvoiceType);//开票类型
            $('.invoiceTypeTitle').text(data.data.wbkhInvoiceType);//开票类型Title
            wbkhInvoiceType = data.data.wbkhInvoiceType;//开票类型变量
            $('.wbkhDepositBank ').val(data.data.wbkhDepositBank);//开户银行
            $('.headingCode ').val(data.data.headingCode);//纳税人识别码
            $('.wbkhBankAccount ').val(data.data.wbkhBankAccount);//银行账号
            $('.wbkhCompanyTel ').val(data.data.wbkhCompanyTel);//公司电话
            $('.wbkhCompanyAddress ').val(data.data.wbkhCompanyAddress);//公司地址
            $('.hwdlTaxReceipt ').val(data.data.hwdlTaxReceipt);//进项税票
            $('.outputRate').val(data.data.hwdlOutputRate);//销项税票
            $('.hwdlTaxRate ').val(data.data.hwdlTaxRate);//进项税率%
            $('.aiTypeCreditPeriod').val(data.data.aiTypeCreditPeriod);//AI
            $('.aiCreditPeriod').val(data.data.aiCreditPeriod);
            $('.aeTypeCreditPeriod').val(data.data.aeTypeCreditPeriod);//AE
            $('.aeCreditPeriod').val(data.data.aeCreditPeriod);
            $('.siTypeCreditPeriod').val(data.data.siTypeCreditPeriod);//SI
            $('.siCreditPeriod').val(data.data.siCreditPeriod);
            $('.seTypeCreditPeriod').val(data.data.seTypeCreditPeriod);//SE
            $('.seCreditPeriod').val(data.data.seCreditPeriod);
            $('.tiTypeCreditPeriod').val(data.data.tiTypeCreditPeriod);//TI
            $('.tiCreditPeriod').val(data.data.tiCreditPeriod);
            $('.teTypeCreditPeriod').val(data.data.teTypeCreditPeriod);//TE
            $('.teCreditPeriod').val(data.data.teCreditPeriod);
            $('.oiTypeCreditPeriod').val(data.data.oiTypeCreditPeriod);//OI
            $('.oiCreditPeriod').val(data.data.oiCreditPeriod);
            $('.oeTypeCreditPeriod').val(data.data.oeTypeCreditPeriod);//OE
            $('.oeCreditPeriod').val(data.data.oeCreditPeriod);
            $('.itTypeCreditPeriod').val(data.data.itTypeCreditPeriod);//IT
            $('.itCreditPeriod').val(data.data.itCreditPeriod);
            $('.ddnTypeCreditPeriod').val(data.data.ddnTypeCreditPeriod);//DDN
            $('.ddnCreditPeriod').val(data.data.ddnCreditPeriod);
            $('.yyoxTypeCreditPeriod').val(data.data.yyoxTypeCreditPeriod);//YYOX
            $('.yyoxCreditPeriod').val(data.data.yyoxCreditPeriod);
            $('.IndustrialTypeCreditPeriod').val(data.data.industrialTypeCreditPeriod);//Industrial
            $('.IndustrialCreditPeriod').val(data.data.industrialCreditPeriod);
            $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr);//服务类别
            $('.service1').val(gxcyrClassOfServiceArr);//服务类别
            remove(gxcyrClassOfServiceArr,'干线运输');
            $('.service2').val(gxcyrClassOfServiceArr);//服务类别
            $('.service3').val(gxcyrClassOfServiceArr);//服务类别
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
            if(data.data.sfhrIsConsigneesAddress == 1){
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
            $('input[name="sfhrShipperNation"]').val(data.data.sfhrShipperNation);//发货人国家
            $('input[name="sfhrShipperContinent"]').val(data.data.sfhrShipperContinent);//发货人州
            $('input[name="sfhrShipperCity"]').val(data.data.sfhrShipperCity);//发货人城市
            $('input[name="sfhrShipperZipCode"]').val(data.data.sfhrShipperZipCode);//发货人邮编
            $('input[name="sfhrShipperPhone"]').val(data.data.sfhrShipperPhone);//发货人电话
            $('input[name="sfhrShipperAddress"]').val(data.data.sfhrShipperAddress);//发货人地址
            $('input[name="createDate"]').val(data.data.createDate);//createDate
            $('input[name="isDelete"]').val(data.data.isDelete);//isDelete
        }
    });
    /*助记码是否可以修改*/
    $.ajax({
        url: 'http://' + gPathUrl + '/partner/details/selectIsChild',
        type: 'get',
        data: {
            id:id
        },
        success: function (data) {
            if(data.code == '200'){
                if(data.data){
                    $('#mnemonicCode').attr('disabled',true);
                }
            }
        }
    });
    /*flow*/
    StatusOn(1,12);
    /*滚动*/
    $('#navigationBar').on('click','.status-div',function(){
        var thisIndex = parseInt($(this).index());
        /* StatusOn(thisIndex+1,13);*/
        var _top = parseInt($('#newForm .lump').eq(thisIndex).offset().top);
        $("body,html").animate({scrollTop:(_top-200)},500);
    });
    var _top0 = 0;
    var _top1 = 0;
    var _top2 = 0;
    var _top3 = 0;
    var _top4 = 0;
    var _top5 = 0;
    var _top6 = 0;
    var _top7 = 0;
    var _top8 = 0;
    var _top9 = 0;
    var _top10 = 0;
    var _top11 = 0;
    var lumpLength = 0;
    $(window).scroll(function() {
        lumpLength = $('#newForm .lump').length;
        if(lumpLength > 0) {
            _top0 = parseInt($('#newForm .lump').eq(0).offset().top) - 200;
            _top1 = parseInt($('#newForm .lump').eq(1).offset().top) - 200;
            _top2 = parseInt($('#newForm .lump').eq(2).offset().top) - 200;
            _top3 = parseInt($('#newForm .lump').eq(3).offset().top) - 200;
            _top4 = parseInt($('#newForm .lump').eq(4).offset().top) - 200;
            _top5 = parseInt($('#newForm .lump').eq(5).offset().top) - 200;
            _top6 = parseInt($('#newForm .lump').eq(6).offset().top) - 200;
            _top7 = parseInt($('#newForm .lump').eq(7).offset().top) - 200;
            _top8 = parseInt($('#newForm .lump').eq(8).offset().top) - 200;
            _top9 = parseInt($('#newForm .lump').eq(9).offset().top) - 200;
            _top10 = parseInt($('#newForm .lump').eq(10).offset().top) - 200;
            _top11 = parseInt($('#newForm .lump').eq(11).offset().top) - 200;
            var htmlScrollTop = parseInt($(document).scrollTop());
            if (htmlScrollTop > 0) {
                $('#specialNav').css('top', '0');
            } else {
                $('#specialNav').css('top', '60px');
            }
            if (0 < htmlScrollTop && htmlScrollTop < _top1) { //基础表单
                StatusOn(1, 12);
            } else if (_top1 <= htmlScrollTop && htmlScrollTop < _top2) {//联系人资料
                StatusOn(2, 12);
            } else if (_top2 <= htmlScrollTop && htmlScrollTop < _top3) {//联系人资料
                StatusOn(3, 12);
            } else if (_top3 <= htmlScrollTop && htmlScrollTop < _top4) {//业务范畴
                StatusOn(4, 12);
            } else if (_top4 <= htmlScrollTop && htmlScrollTop < _top5) {//合作伙伴分类
                StatusOn(5, 12);
            } else if (_top5 <= htmlScrollTop && htmlScrollTop < _top6) {//外部客户
                StatusOn(6, 12);
            } else if (_top6 <= htmlScrollTop && htmlScrollTop < _top7) {//互为代理
                StatusOn(7, 12);
            } else if (_top7 <= htmlScrollTop && htmlScrollTop < _top8) {//海外代理
                StatusOn(8, 12);
            } else if (_top8 <= htmlScrollTop && htmlScrollTop < _top9) {//干线承运人
                StatusOn(9, 12);
            } else if (_top9 <= htmlScrollTop && htmlScrollTop < _top10) {//不可控供应商
                StatusOn(10, 12);
            } else if (_top10 <= htmlScrollTop && htmlScrollTop < _top11) {//延伸服务供应商
                StatusOn(11, 12);
            } else if (_top11 <= htmlScrollTop) {//收发货人
                StatusOn(12, 12);
            }
        }
    });

    /*控制代码填写区域*/
    if(!!id){
        $.ajax({
            url: 'http://' + gPathUrl + '/partner/details/getParentCodeList',
            type: 'get',
            data: {
                id:id
            },
            success: function (data) {
                if(data.code == '200'){
                    $.each(data.data,function (index,value) {
                        codes.eq(index).val(value);
                        if(index>0){
                            codes.eq(index-1).attr('disabled',true);
                        }
                        nn = ++index;
                    });
                    codes.each(function (i,val) {
                        if(nn <=i){
                            $('.code').eq(i).attr('disabled','disabled');
                        }
                    })
                }
            }
        })
    }
    /*code是否允许修改*/
    $.ajax({
        url: 'http://' + gPathUrl + '/partner/details/isEditCode',
        type: 'get',
        data: {
            id:id
        },
        success: function (data) {
            if(data.code == 200){
                if(!data.data){
                    $('.code').attr('readonly',true).css('background-color',' rgb(235, 235, 228)');
                }
            }
        }
    });
    //光标移开，校验代码字段的填写
    codes.blur(function(){
        var that = this;
        if($(this).val().length > 0){
            if(parseInt($(that).val().length) != 3){
                $('#alertWords').text('代码必须3位');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('')
                });
                $(that).focus();
                return false;
            }
        }
    });
    //转换大写
    $('.code,#mnemonicCode,#currency').blur(function(){
        $(this).val($(this).val().toUpperCase());
    });
    /*默认币种*/
    $('#currency').blur(function(){
        if(parseInt($(this).val().length) != 3){
            $('#alertWords').text('默认币种为三位大写字母！');
            $('.alertShow').show().delay(3000).hide(300,function(){
                $('#alertWords').text('');
            });
            $(this).focus();
        }
    });
    /*提醒接受者逻辑*/
    $('#dutyInput').keyup(function(){
        var that = this;
        var inputVal = $('#dutyInput').val();
        if ( inputVal.length >0) {
            $('.userNameLi').empty();
            $.ajax({
                url: 'http://' + eamsPathUrl + '/base/person/selectPersonByQuery',
                type: 'get',
                data: {
                    name: inputVal
                },
                success: function (data) {
                    if(data.data.length >=1){
                        $('.userNameLi').show();
                        $('.warning').text('');
                        $.each(data.data,function(index,value){
                            var str = ' <li data-Id = '+value.id+' class="nameList" comId-data="'+value.companyId+'"><span class="staffName">'+value.name+'</span><span>'+(value.companyName||"")+'</span><span>'+(value.deptName||'')+'</span><span>'+(value.positionName||'')+'</span></li>'
                            $(str).appendTo('.userNameLi');
                        });
                    }else if(data.data <1){
                        $('.userNameLi').hide();
                        $('.warning').text('');
                        $('.dutyOfficer-waring').text('该责任人不存在');
                        $('#receiverName').val('');
                        $('#receiverId').val('');
                    }
                },
                error: function (msg) {
                }
            });
        }else{
            $('.userNameLi').hide();
        }
    });
    $('.userNameLi').on('click','.nameList',function(){
        $('#dutyInput').val( $(this).find('.staffName').text());
        $('#receiverName').val( $(this).find('.staffName').text());
        $('#receiverId').val( $(this).attr('data-id'));
        $('#profitCenter').val( $(this).attr('comId-data'));//利润中心自动带出
        $('.userNameLi').hide();
    });
    /*黑名单逻辑*/
    $('#isBlacklist').change(function(){
        var isBlacklist = $(this).prop('checked');
        if(isBlacklist){
            $('input[name="isBlacklist"]').val('1');
        }else{
            $('input[name="isBlacklist"]').val('0');
        }
    });
    /*停用逻辑*/
    $('#isDisable').change(function(){
        var isDisable = $(this).prop('checked');
        if(isDisable){
            $('input[name="isDisable"]').val('1');
            $('input[name="disableRemark"]').prop('required',true);
            $('.disableMark').css('color','#ed6e56');
        }else{
            $('input[name="isDisable"]').val('0');
            $('input[name="disableRemark"]').prop('required',false);
            $('.disableMark').css('color','#fff');
        }
    });

    /*选中合作伙伴分类显示必填项校验*/
    /*外部客户*/
    var externalClientMark = $('#externalClient .mark');
    var externalClientMust = $('#externalClient .must');
    if(partnerCategorys.indexOf('外部客户')>=0){
        $('#externalClientBox').slideDown();
        externalClientMark.css('color','#ed6e56');
        externalClientMust.prop('required',true);
        if(wbkhInvoiceType =="增值税普票"){
            $('.bankInfo1 .mark').css('color','#fff');
            $('.bankInfo1 input').attr('required',false);
            $('.bankInfo1 .hCMark1,.xxsl1').css('color','#ed6e56');
            $('.Taxpayers1,.outputRate1').attr('required',true);
        }else if(wbkhInvoiceType =="增值税专票"){
            $('.bankInfo1 .mark').show().css('color','#ed6e56');
            $('.bankInfo1 input').attr('required',true);
        }else if(wbkhInvoiceType =="DebitNote"){
            $('.bankInfo1 .mark').css('color','#fff');
            $('.bankInfo1 input').attr('required',false);
            $('.bankInfo1 .mark.xxsl1').css('color','#ed6e56');
            $('.outputRate1').attr('required',true);
        }else if(wbkhInvoiceType =="其他"){
            $('.bankInfo1 .mark').css('color','#fff');
            $('.bankInfo1 input').attr('required',false);
            $('.bankInfo1 .mark.xxsl1').css('color','#ed6e56');
            $('.outputRate1').attr('required',true);
        }
    }else{
        $('#externalClientBox').slideUp();
        externalClientMark.css('color','#fff');
        externalClientMust.prop('required',false);
    }
    $('.partnersCheckbox').on('change','.externalClient',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            $('#externalClientBox').slideDown();
            externalClientMark.css('color','#ed6e56');
            externalClientMust.prop('required',true);
        }else{//取消勾选
            $('#externalClientBox').slideUp();
            externalClientMark.css('color','#fff');
            externalClientMust.prop('required',false);
            $('.Taxpayers1 ').val('');
        }
    });
    /*客户分类*/
    $('.customerClassBox input').change(function(){
        var customerClass = [];
        $('#wbkhCustomerClass').val(customerClass.join(','));
        $('.customerClassBox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    customerClass.push($(n).val());
                }
            }
        });
        $('#wbkhCustomerClass').val(customerClass.join(','));
    });
    /*开票类型 1*/
    $('.wbkhInvoiceType1').change(function(){
        var selectInvoiceType = $(this).val();
        $('.wbkhInvoiceType').val(selectInvoiceType);
        $('.invoiceTypeTitle').text(selectInvoiceType);
        if(selectInvoiceType =="增值税普票"){
            $('.bankInfo1 .mark').css('color','#fff');
            $('.bankInfo1 input').attr('required',false);
            $('.bankInfo1 .hCMark1,.xxsl1').css('color','#ed6e56');
            $('.Taxpayers1,.outputRate1').attr('required',true);
        }else if(selectInvoiceType =="增值税专票"){
            $('.bankInfo1 .mark').show().css('color','#ed6e56');
            $('.bankInfo1 input').attr('required',true);
        }else if(selectInvoiceType =="DebitNote"){
            $('.bankInfo1 .mark').css('color','#fff');
            $('.bankInfo1 input').attr('required',false);
            $('.bankInfo1 .mark.xxsl1').css('color','#ed6e56');
            $('.outputRate1').attr('required',true);
        }else if(selectInvoiceType =="其他"){
            $('.bankInfo1 .mark').css('color','#fff');
            $('.bankInfo1 input').attr('required',false);
            $('.bankInfo1 .mark.xxsl1').css('color','#ed6e56');
            $('.outputRate1').attr('required',true);
        }
    });


    /*互为代理*/
    var eachAgentMark = $('#eachAgent .mark');
    var eachAgentMust = $('#eachAgent .must');
    if(partnerCategorys.indexOf('互为代理')>=0){
        $('#eachAgentBox').slideDown();
        eachAgentMark.css('color','#ed6e56');
        eachAgentMust.prop('required',true);
        if(wbkhInvoiceType =="增值税普票"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 input').attr('required',false);
            $('.bankInfo2 .hCMark2,.xxsl2').css('color','#ed6e56');
            $('.Taxpayers2,.outputRate2').attr('required',true);
        }else if(wbkhInvoiceType =="增值税专票"){
            $('.bankInfo2 .mark').show().css('color','#ed6e56');
            $('.bankInfo2 input').attr('required',true);
        }else if(wbkhInvoiceType =="DebitNote"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 input').attr('required',false);
            $('.bankInfo2 .mark.xxsl2').css('color','#ed6e56');
            $('.outputRate2').attr('required',true);
        }else if(wbkhInvoiceType =="其他"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 input').attr('required',false);
            $('.bankInfo2 .mark.xxsl2').css('color','#ed6e56');
            $('.outputRate2').attr('required',true);
        }
    }else{
        $('#eachAgentBox').slideUp();
        eachAgentMark.css('color','#fff');
        eachAgentMust.prop('required',false);
    }
    $('.partnersCheckbox').on('change','.eachAgent',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            $('#eachAgentBox').slideDown();
            eachAgentMark.css('color','#ed6e56');
            eachAgentMust.prop('required',true);
        }else{//取消勾选干线承运人
            eachAgentMark.css('color','#fff');
            eachAgentMust.prop('required',false);
            $('.Taxpayers2').val('');
        }
    });
    /*开票类型 2*/
    $('.wbkhInvoiceType2').change(function(){
        var selectInvoiceType2 = $(this).val();
        $('.wbkhInvoiceType').val(selectInvoiceType2);
        $('.invoiceTypeTitle').text(selectInvoiceType2);
        if(selectInvoiceType2 =="增值税普票"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 input').attr('required',false);
            $('.bankInfo2 .hCMark2,.xxsl2').css('color','#ed6e56');
            $('.Taxpayers2,.outputRate2').attr('required',true);
        }else if(selectInvoiceType2 =="增值税专票"){
            $('.bankInfo2 .mark').show().css('color','#ed6e56');
            $('.bankInfo2 input').attr('required',true);
        }else if(selectInvoiceType2 =="DebitNote"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 input').attr('required',false);
            $('.bankInfo2 .mark.xxsl2').css('color','#ed6e56');
            $('.outputRate2').attr('required',true);
        }else if(selectInvoiceType2 =="其他"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 input').attr('required',false);
            $('.bankInfo2 .mark.xxsl2').css('color','#ed6e56');
            $('.outputRate2').attr('required',true);
        }
    });
    /*海外代理*/
    var overseasAgencyMark = $('#overseasAgency .mark');
    var overseasAgencyMust = $('#overseasAgency .must');
    if(partnerCategorys.indexOf('海外代理')>=0){
        $('#overseasAgencyBox').slideDown();
        overseasAgencyMark.css('color','#ed6e56');
        overseasAgencyMust.prop('required',true);
    }else{
        $('#overseasAgencyBox').slideUp();
        overseasAgencyMark.css('color','#fff');
        overseasAgencyMust.prop('required',false);
    }
    $('.partnersCheckbox').on('change','.overseasAgency',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            $('#overseasAgencyBox').slideDown();
            overseasAgencyMark.css('color','#ed6e56');
            overseasAgencyMust.prop('required',true);
        }else{//取消勾选
            $('#overseasAgencyBox').slideUp();
            overseasAgencyMark.css('color','#fff');
            overseasAgencyMust.prop('required',false);
        }
    });
    /*干线承运人*/
    var trunkCarrierMark = $('#trunkCarrier .mark');
    var trunkCarrierMust = $('#trunkCarrier .must');
    if(partnerCategorys.indexOf('干线承运人')>=0){
        $('#trunkCarrierBox').slideDown();
        trunkCarrierMark.css('color','#ed6e56');
        trunkCarrierMust.prop('required',true);
        $('.classOfSer1').attr('checked',true);
    }else{
        $('#trunkCarrierBox').slideUp();
        trunkCarrierMark.css('color','#fff');
        trunkCarrierMust.prop('required',false);
        $('.classOfSer1').attr('checked',false);
    }
    $('.partnersCheckbox').on('change','.trunkCarrier',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            $('#trunkCarrierBox').slideDown();
            trunkCarrierMark.css('color','#ed6e56');
            trunkCarrierMust.prop('required',true);
            $('.classOfSer1').attr('checked',true);
            $('.service2').val(gxcyrClassOfServiceArr.join(','));
            $('.service3').val(gxcyrClassOfServiceArr.join(','));
            gxcyrClassOfServiceArr.push('干线运输');
            $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
            $('.service1').val(gxcyrClassOfServiceArr.join(','));
        }else{//取消勾选
            $('#trunkCarrierBox').slideUp();
            trunkCarrierMark.css('color','#fff');
            trunkCarrierMust.prop('required',false);
            $('.classOfSer1').attr('checked',false);
            remove(gxcyrClassOfServiceArr,'干线运输');
            $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
            $('.service1').val(gxcyrClassOfServiceArr.join(','));
            $('.service2').val(gxcyrClassOfServiceArr.join(','));
            $('.service3').val(gxcyrClassOfServiceArr.join(','));
        }
    });
    /*控制三个服务类别同时变动*/
    $('.gxcyrClassOfServiceBox input[type=checkbox]').change(function () {
        var className = $(this).prop('className');
        var isChecked = $(this).prop('checked');
        $('.'+className).prop('checked',isChecked)
    });
    //服务类别
    var gxcyrClassOfServiceArr = [];
    $('.classOfServiceBox input').change(function(){
        gxcyrClassOfServiceArr = [];
        $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
        $('.service1').val(gxcyrClassOfServiceArr.join(','));
        $('.service2').val(gxcyrClassOfServiceArr.join(','));
        $('.service3').val(gxcyrClassOfServiceArr.join(','));
        $('#classOfServiceBox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    gxcyrClassOfServiceArr.push($(n).val());
                }
            }
        });
        $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
        $('.service1').val(gxcyrClassOfServiceArr.join(','));
        remove(gxcyrClassOfServiceArr,'干线运输');
        $('.service2').val(gxcyrClassOfServiceArr.join(','));
        $('.service3').val(gxcyrClassOfServiceArr.join(','));
    });

    /*不可控供应商*/
    var uncontrollableSupplierMark = $('#uncontrollableSupplier .mark');
    var uncontrollableSupplierMust = $('#uncontrollableSupplier .must');
    if(partnerCategorys.indexOf('不可控供应商')>=0){
        $('#uncontrollableSupplierBox').slideDown();
        uncontrollableSupplierMark.css('color','#ed6e56');
        uncontrollableSupplierMust.prop('required',true);
    }else{
        $('#uncontrollableSupplierBox').slideUp();
        uncontrollableSupplierMark.css('color','#fff');
        uncontrollableSupplierMust.prop('required',false);
    }
    $('.partnersCheckbox').on('change','.uncontrollableSupplier',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            $('#uncontrollableSupplierBox').slideDown();
            uncontrollableSupplierMark.css('color','#ed6e56');
            uncontrollableSupplierMust.prop('required',true);
        }else{//取消勾选
            $('#uncontrollableSupplierBox').slideUp();
            uncontrollableSupplierMark.css('color','#fff');
            uncontrollableSupplierMust.prop('required',false);
        }
    });

    /*延伸服务供应商*/
    var extendServiceProvidersMark = $('#extendServiceProviders .mark');
    var extendServiceProvidersMust = $('#extendServiceProviders .must');
    if(partnerCategorys.indexOf('延伸服务供应商')>=0){
        $('#extendServiceProvidersBox').slideDown();
        extendServiceProvidersMark.css('color','#ed6e56');
        extendServiceProvidersMust.prop('required',true);
    }else{
        $('#extendServiceProvidersBox').slideUp();
        extendServiceProvidersMark.css('color','#fff');
        extendServiceProvidersMust.prop('required',false);
    }
    $('.partnersCheckbox').on('change','.extendServiceProviders',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            $('#extendServiceProvidersBox').slideDown();
            extendServiceProvidersMark.css('color','#ed6e56');
            extendServiceProvidersMust.prop('required',true);
        }else{//取消勾选
            $('#extendServiceProvidersBox').slideUp();
            extendServiceProvidersMark.css('color','#fff');
            extendServiceProvidersMust.prop('required',false);
        }
    });

    /*收发货人*/
    /*var consigneeAndConsignerMark = $('#consigneeAndConsigner .mark');
    var consigneeAndConsignerMust = $('#consigneeAndConsigner .must');
    if(partnerCategorys.indexOf('收发货人')>=0){
        consigneeAndConsignerMark.css('color','#ed6e56');
    }else{
        consigneeAndConsignerMark.css('color','#fff');
    }
    $('.partnersCheckbox').on('change','.consigneeAndConsigner',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            consigneeAndConsignerMark.css('color','#ed6e56');
            consigneeAndConsignerMust.prop('required',true)
        }else{//取消勾选
            consigneeAndConsignerMark.css('color','#fff');
            consigneeAndConsignerMust.prop('required',false)
        }
    });*/
    /*结算对象*/
    var settlementObjectMark = $('#settlementObject .mark');
    var settlementObjectMust = $('#settlementObject .must');
    if(partnerCategorys.indexOf('结算对象')>=0){
        $('#settlementObjectBox').slideDown();
        settlementObjectMark.css('color','#ed6e56');
        settlementObjectMust.prop('required',true);
        if(wbkhInvoiceType =="增值税普票"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 input').attr('required',false);
            $('.bankInfo3 .hCMark3,.xxsl3').css('color','#ed6e56');
            $('.Taxpayers3,.outputRate3').attr('required',true);
        }else if(wbkhInvoiceType =="增值税专票"){
            $('.bankInfo3 .mark').show().css('color','#ed6e56');
            $('.bankInfo3 input').attr('required',true);
        }else if(wbkhInvoiceType =="DebitNote"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 input').attr('required',false);
            $('.bankInfo3 .mark.xxsl3').css('color','#ed6e56');
            $('.outputRate3').attr('required',true);
        }else if(wbkhInvoiceType =="其他"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 input').attr('required',false);
            $('.bankInfo3 .mark.xxsl3').css('color','#ed6e56');
            $('.outputRate3').attr('required',true);
        }
    }else{
        $('#settlementObjectBox').slideUp();
        settlementObjectMark.css('color','#fff');
        settlementObjectMust.prop('required',false);
    }
    $('.partnersCheckbox').on('change','.settlementObject',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            $('#settlementObjectBox').slideDown();
            settlementObjectMark.css('color','#ed6e56');
            settlementObjectMust.prop('required',true);
        }else{//取消勾选
            $('#settlementObjectBox').slideUp();
            settlementObjectMark.css('color','#fff');
            settlementObjectMust.prop('required',false);
            $('.Taxpayers3').val('');
        }
    });
    /*开票类型 3*/
    $('.wbkhInvoiceType3').change(function(){
        var selectInvoiceType3 = $(this).val();
        $('.wbkhInvoiceType').val(selectInvoiceType3);
        $('.invoiceTypeTitle').text(selectInvoiceType3);
        if(selectInvoiceType3 =="增值税普票"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 input').attr('required',false);
            $('.bankInfo3 .hCMark3,.xxsl3').css('color','#ed6e56');
            $('.Taxpayers3,.outputRate3').attr('required',true);
        }else if(selectInvoiceType3 =="增值税专票"){
            $('.bankInfo3 .mark').show().css('color','#ed6e56');
            $('.bankInfo3 input').attr('required',true);
        }else if(selectInvoiceType3 =="DebitNote"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 input').attr('required',false);
            $('.bankInfo3 .mark.xxsl3').css('color','#ed6e56');
            $('.outputRate3').attr('required',true);
        }else if(selectInvoiceType3 =="其他"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 input').attr('required',false);
            $('.bankInfo3 .mark.xxsl3').css('color','#ed6e56');
            $('.outputRate3').attr('required',true);
        }
    });

    /*信用等级同时改变*/
    $('.wbkhCreditRating').change(function(){
        $('.wbkhCreditRating').val($(this).val());
        var selectVal = $(this).val().slice(2,7);
        if(selectVal=='协议/保函'){
            $('.wbkhCreditPeriod').attr('disabled',false);
            $('.wbkhLineCredit').attr('disabled',false);
            $('.wbkhIsPayForAnother').attr('disabled',false);
            $('.businessBox select').attr('disabled',false);
            $('.businessBox input').attr('disabled',false);
            $('.businessBox input.must').attr('required',true)
            getDefault($(this).val());
        }else if(selectVal=='签约在途'){
            $('.wbkhCreditPeriod').val('').attr('disabled',true);
            $('.wbkhLineCredit').val('').attr('disabled',true);
            $('.wbkhIsPayForAnother').attr('disabled',false);
            $('.businessBox select').val('').attr('disabled',true);
            $('.businessBox input').val('').attr('disabled',true)
            $('.businessBox input.must').attr('required',false)
        }else{
            $('.wbkhCreditPeriod').val('').attr('disabled',true);
            $('.wbkhLineCredit').val('').attr('disabled',true);
            $('.wbkhIsPayForAnother').prop('checked',false);
            $('.wbkhIsPayForAnother').attr('disabled',true);
            $('input[name="wbkhIsPayForAnother"]').val('0');
            $('.daiDian ').val('').attr('disabled',true);
            $('.daiDian ').val('').attr('disabled',true);
            $('.businessBox input.must').attr('required',false)
        }
    });
    /*信用期限类型*/
    $('.wbkhTypeCreditPeriod').change(function(){
        $('.wbkhTypeCreditPeriod').val($(this).val());
    });
    /*信用期限*/
    $('.wbkhCreditPeriod').keyup(function(){
        $('.wbkhCreditPeriod').val($(this).val());
    });
    /*信用额度*/
    $('.wbkhLineCredit').keyup(function(){
        $('.wbkhLineCredit').val($(this).val());
    });
    /*代垫逻辑*/
    $('.wbkhIsPayForAnother').change(function(){
        var isPay = $(this).prop('checked');
        $('.wbkhIsPayForAnother').prop('checked',isPay);
        if(isPay){
            $('input[name="wbkhIsPayForAnother"]').val('1');
            $('.daiDian').attr('disabled',false);
        }else{
            $('input[name="wbkhIsPayForAnother"]').val('0');
            $('.daiDian').val('').attr('disabled',true);
        }
    });
    /*停用备注*/
    $('#partnerNew').on('keyup','.disableRemark',function(){
        $('.disableRemark').val($(this).val());
    });
    /*代垫天数*/
    $('.wbkhPaymentTerm').keyup(function(){
        $('.wbkhPaymentTerm').val($(this).val());
    });
    /*代垫额度*/
    $('.wbkhPaidAmount').keyup(function(){
        $('.wbkhPaidAmount').val($(this).val());
    });
    /*开户银行*/
    $('.wbkhDepositBank').keyup(function(){
        $('.wbkhDepositBank').val($(this).val());
    });
    /* 纳税人识别码*/
    $('.headingCode').keyup(function(){
        $('.headingCode').val($(this).val());
    });
    /*银行账号*/
    $('.wbkhBankAccount').keyup(function(){
        $('.wbkhBankAccount').val($(this).val());
    });
    /*公司电话*/
    $('.wbkhCompanyTel').keyup(function(){
        $('.wbkhCompanyTel').val($(this).val());
    });
    /*销项税率*/
    $('.outputRate').keyup(function(){
        $('.outputRate').val($(this).val());
    });
    /*公司地址*/
    $('.wbkhCompanyAddress').keyup(function(){
        $('.wbkhCompanyAddress').val($(this).val());
    });
    /*进项税票*/
    $('.hwdlTaxReceipt').change(function(){
        $('.hwdlTaxReceipt').val($(this).val());
    });
    /*进项税率*/
    $('.hwdlTaxRate').keyup(function(){
        $('.hwdlTaxRate').val($(this).val());
    });
    /*收货人*/
    $('#sfhrIsConsignee').change(function(){
        var isConsignee = $(this).prop('checked');
        if(isConsignee){
            $('input[name="sfhrIsConsignee"]').val('1');
            $('.consigneeInput input').attr('disabled',false);
            $('.consigneeInput input').attr('required',true);
        }else{
            $('input[name="sfhrIsConsignee"]').val('0');
            $('.consigneeInput input').attr('disabled',true);
            $('.consigneeInput input').attr('required',false);
        }
    });
    /*发货人*/
    $('#sfhrIsShipper').change(function(){
        var isShipper = $(this).prop('checked');
        if(isShipper){
            $('input[name="sfhrIsShipper"]').val('1');
            $('.shipperInput input').attr('disabled',false);
            $('.shipperInput input').attr('required',true);
        }else{
            $('input[name="sfhrIsShipper"]').val('0');
            $('.shipperInput input').val('').attr('disabled',true);
            $('input[name="sfhrIsConsigneesAddress"]').val('0');
            $('#sfhrIsConsigneesAddress').prop('checked',false);
            $('.shipperInput input').attr('required',false);
        }
    });
    /*与收货人地址相同*/
    $('#sfhrIsConsigneesAddress').change(function(){
        var isShipper = $(this).prop('checked');
        if(isShipper){
            $('input[name="sfhrIsConsigneesAddress"]').val('1');
            $('.consigneeInput input').each(function(i,val){
                $('.shipperInput input').eq(i).val($(val).val());
            });
            //发货人逻辑
            $('input[name="sfhrIsShipper"]').val('1');
            $('#sfhrIsShipper').prop('checked',true);
            $('.shipperInput input').attr('disabled',false);
            $('.shipperInput input').attr('required',true);
        }else{
            $('input[name="sfhrIsShipper"]').val('0');
            $('input[name="sfhrIsConsigneesAddress"]').val('0');
            $('#sfhrIsShipper').prop('checked',false);
            $('.shipperInput input').val('').attr('disabled',true);
            $('.shipperInput input').attr('required',false);
        }
    });
    /*合作伙伴分类*/
    $('.partnersCheckbox input').change(function(){
        var partnersCheck = $('.partnersCheckbox input');
        var flag1 = true;
        $.each(partnersCheck,function(index,value){
            if($(value).prop('checked')){
                flag1 = false;
            }
        });
        if(flag1){
            $('#partnerCategory').prop('required','required');
        }else{
            $('#partnerCategory').prop('required',false);
        }

    });

    /*加载地址列表*/
    addressObj.getAddressList();
    /*点击新增地址逻辑*/
    $('#addAddress').click(function(){
        var addressListBox = $('.addressList');
        if(addressListBox.children('.addingAdd').length >= 1){
            return false;
        }
        var listNum = parseInt(addressListBox.find('.list').length)+1;
        addressListBox.append('' +
            '<div class="addingAdd clearfix">\
            <div class="no"><span>'+ listNum +'</span></div>\
            <div class="addressType"><select style="width:80%;"><option value="注册地址">注册地址</option><option value="办公地址">办公地址</option><option value="仓库地址">仓库地址</option></select></div>\
            <div class="short"><input style="width: 80%;" type="text"></div>\
            <div class="address"><input style="width: 88%;" type="text"></div>\
            <div class="postcode"><input  style="width: 80%;" type="text"></div>\
            <div class="operation"><a class="confirmAdd" href="javascript:void(0);">确定</a> <a class="cancelAdd redColor" href="javascript:void(0);">取消</a></div>\
            </div>');
    });
    /*新增地址-取消*/
    $('.addressList ').on('click','.cancelAdd',function(){
        $('.addingAdd').remove();
    });
    /*新增地址--确定*/
    $('.addressList ').on('click','.confirmAdd',function(){
        var newAddObj = {};
        var addingAdd = $('.addingAdd');
        var addressTypeVal = addingAdd.find('.addressType select').val();
        var addressShortName = addingAdd.find('.short input').val();
        var addressAddVal = addingAdd.find('.address input').val();
        if(addressShortName.length <=0){
            $('.short input').focus();
            return false;
        }
        if(addressAddVal.length <=0){
            $('.address input').focus();
            return false;
        }
        newAddObj.id = addingAdd.find('.no').find('span').text();
        newAddObj.addressType = addressTypeVal;
        newAddObj.abbreviation = addingAdd.find('.short input').val();
        newAddObj.address = addressAddVal;
        newAddObj.zipCode = addingAdd.find('.postcode input').val();
        addressList.push(newAddObj);
        addressObj.getAddressList();
    });
    /*删除地址*/
    $('.addressList ').on('click','.delAdd',function(){
        var delListId = $(this).parents('.list').attr('data-listId');
        mm.removeObjWithArr(addressList,delListId);
        addressObj.getAddressList();
    });
    /*修改地址*/
    $('.addressList ').on('click','.editAdd',function(){
        var thisList = $(this).parents('.list');
        var editId = thisList.attr('data-ListId');
        var editIndex = thisList.find('.no').find('span').text();
        var editAddressType = thisList.find('.addressType span').text();
        var editAbbreviation = thisList.find('.short span').text();
        var editAddress = thisList.find('.address span').text();
        var editZipCode = thisList.find('.postcode span').text();
        thisList.after('' +
            '<div data-ListId="'+editId+'" class="editingAdd clearfix">\
            <div class="no"><span>'+ editIndex +'</span></div>\
            <div class="addressType"><select id="addTypeSelect" style="width:80%;"><option value="注册地址">注册地址</option><option value="办公地址">办公地址</option><option value="仓库地址">仓库地址</option></select></div>\
            <div class="short"><input style="width: 80%;" type="text" value="'+editAbbreviation+'"></div>\
            <div class="address"><input style="width: 88%;" type="text" value="'+editAddress+'"></div>\
            <div class="postcode"><input  style="width: 80%;" type="text" value="'+editZipCode+'"></div>\
            <div class="operation"><a class="confirmEdit" href="javascript:void(0);">确定</a> <a class="cancelEdit redColor" href="javascript:void(0);">取消</a></div>\
            </div>');
        $('.addressType select').val(editAddressType);
        thisList.remove();
    });
    /*修改地址-取消*/
    $('.addressList ').on('click','.cancelEdit',function(){
        addressObj.getAddressList();
    });
    /*修改地址-确定*/
    $('.addressList ').on('click','.confirmEdit',function(){
        var EditObj = {};
        var Editing = $(this).parents('.editingAdd');
        var typeVal = Editing.find('.addressType select').val();
        var shortName = Editing.find('.short input').val();
        var addressVal = Editing.find('.address input').val();
        if(shortName.length <=0){
            $('.short input').focus();
            return false;
        }
        if(addressVal.length <=0){
            $('.address input').focus();
            return false;
        }
        EditObj.id = Editing.attr('data-ListId');
        EditObj.addressType = typeVal;
        EditObj.abbreviation = shortName;
        EditObj.address = addressVal;
        EditObj.zipCode = Editing.find('.postcode input').val();
        mm.removeObjWithArr(addressList,EditObj.id);
        addressList.push(EditObj);
        addressObj.getAddressList();
    });

    /*加载联系人列表*/
    contactsObj.getContactsList();
    /*点击新增联系人逻辑*/
    $('#addContact').click(function(){
        var contactListBox = $('.contactList');
        if(contactListBox.children('.addingCon').length >= 1){
            return false;
        }
        var listNum = parseInt(contactListBox.find('.list').length)+1;
        contactListBox.append('' +
            '<div class="addingCon clearfix ">\
            <div class="no2"><span>'+ listNum +'</span></div>\
            <div class="name"><input  style="width: 80%;" type="text"></div>\
            <div class="obligation"><select style="width: 90%;padding-left:0;"><option value="订舱">订舱</option><option value="操作">操作</option><option value="对账">对账</option><option value="财务">财务</option></select></div>\
            <div class="demp"><input style="width: 88%;" type="text"></div>\
            <div class="duty"><input  style="width: 80%;" type="text"></div>\
            <div class="tel"><input  style="width: 80%;" type="text"></div>\
            <div class="phone"><input  style="width: 88%;" type="text"></div>\
            <div class="email"><input  style="width: 88%;" type="text"></div>\
            <div class="qq"><input  style="width: 84%;" type="text"></div>\
            <div class="weChat"><input  style="width: 84%;" type="text"></div>\
            <div class="address2"><input  style="width: 88%;" type="text"></div>\
            <div class="operation"><a class="confirmAdd" href="javascript:void(0);">确定</a> <a class="cancelAdd redColor" href="javascript:void(0);">取消</a></div>\
            </div>');
    });
    /*新增-取消*/
    $('.contactList').on('click','.cancelAdd',function(){
        $('.addingCon').remove();
    });
    /*新增-确定*/
    $('.contactList').on('click','.confirmAdd',function(){
        var newConObj = {};
        var addingCon = $('.addingCon');
        var addLinkmanName = addingCon.find('.name input').val();
        var addLinkmanObl= addingCon.find('.obligation select').val();
        var addLinkmanPhone = addingCon.find('.phone input').val();
        var addLinkmanEmail = addingCon.find('.email input').val();
        if(addLinkmanName.length <=0){
            $('.name input').focus();
            return false;
        }
        if(addLinkmanPhone.length <=0){
            $('.phone input').focus();
            return false;
        }
        if(addLinkmanEmail.length <=0){
            $('.email input').focus();
            return false;
        }
        if(!isEmail(addLinkmanEmail)){
            $('#alertWords').text('邮箱格式不正确!');
            $('.alertShow').show().delay(3000).hide(300,function(){
                $('#alertWords').text('');
                $('.email input').focus();
            });
            return false;
        }
        /*校验联系人是否存在*/
        $.ajax({
            url: 'http://' + gPathUrl + '/partner/details/checkPhone',
            type:'post',
            dataType:'json',
            contentType:'application/json;charset=utf-8',
            data:JSON.stringify({
                phone:addLinkmanPhone
            }),
            success:function(data){
                if(data.code == 200){
                    if(data.data){
                        if (confirm("联系人电话已存在，是否确认保存？")) {
                            newConObj.id = addingCon.find('.no2').find('span').text();
                            newConObj.name = addLinkmanName;
                            newConObj.obligation = addLinkmanObl;
                            newConObj.demp = addingCon.find('.demp input').val();
                            newConObj.duty = addingCon.find('.duty input').val();
                            newConObj.fixPhone = addingCon.find('.tel input').val();
                            newConObj.phone = addLinkmanPhone;
                            newConObj.email = addLinkmanEmail;
                            newConObj.qq = addingCon.find('.qq input').val();
                            newConObj.weChat = addingCon.find('.weChat input').val();
                            newConObj.address = addingCon.find('.address2 input').val();
                            contactsList.push(newConObj);
                            contactsObj.getContactsList();
                        } else {
                            $('.addingCon .phone input').focus();
                            return false;
                        }
                    }else{
						// 无重复联系人时 校验 新是否与增联系人集合中的其他联系人重复并提醒
                        var RepeatedPhoneCount = 0;
                        $(contactsList).each(function(){
                            if($(this).attr("phone")==addLinkmanPhone ){
                                RepeatedPhoneCount++;
                            }
                        });
                        if(RepeatedPhoneCount>0){
                            var statu = confirm("联系人电话已存在，是否确认保存？");
                            if(statu) {
                                newConObj.id = addingCon.find('.no2').find('span').text();
                                newConObj.name = addLinkmanName;
                                newConObj.obligation = addLinkmanObl;
                                newConObj.demp = addingCon.find('.demp input').val();
                                newConObj.duty = addingCon.find('.duty input').val();
                                newConObj.fixPhone = addingCon.find('.tel input').val();
                                newConObj.phone = addLinkmanPhone;
                                newConObj.email = addLinkmanEmail;
                                newConObj.qq = addingCon.find('.qq input').val();
                                newConObj.weChat = addingCon.find('.weChat input').val();
                                newConObj.address = addingCon.find('.address2 input').val();
                                contactsList.push(newConObj);
                                contactsObj.getContactsList();
                            } else {
                                $('.addingCon .phone input').focus();
                                return false;
                            }
                        } else {
                            newConObj.id = addingCon.find('.no2').find('span').text();
                            newConObj.name = addLinkmanName;
                            newConObj.obligation = addLinkmanObl;
                            newConObj.demp = addingCon.find('.demp input').val();
                            newConObj.duty = addingCon.find('.duty input').val();
                            newConObj.fixPhone = addingCon.find('.tel input').val();
                            newConObj.phone = addLinkmanPhone;
                            newConObj.email = addLinkmanEmail;
                            newConObj.qq = addingCon.find('.qq input').val();
                            newConObj.weChat = addingCon.find('.weChat input').val();
                            newConObj.address = addingCon.find('.address2 input').val();
                            contactsList.push(newConObj);
                            contactsObj.getContactsList();
                        }
                    }
                }
            },
            error:function () {

            }
        });
    });
    /*删除联系人*/
    $('.contactList ').on('click','.delAdd',function(){
        var delListId = $(this).parents('.list').attr('data-listId');
        mm.removeObjWithArr(contactsList,delListId);
        contactsObj.getContactsList();
    });
    /*修改联系人*/
    $('.contactList ').on('click','.editAdd',function(){
        var thisList = $(this).parents('.list');
        var editId = thisList.attr('data-ListId');
        var editIndex = thisList.find('.no2').find('span').text();
        var editContactName = thisList.find('.name span').text();
        var editContactObligation = thisList.find('.obligation span').text();
        var editContactDemp = thisList.find('.demp span').text();
        var editContactDuty = thisList.find('.duty span').text();
        var editContactTel = thisList.find('.tel span').text();
        var editContactPhone = thisList.find('.phone span').text();
        var editContactEmail = thisList.find('.email span').text();
        var editContactQq = thisList.find('.qq span').text();
        var editContactweChat = thisList.find('.weChat span').text();
        var editContactAddress = thisList.find('.address2 span').text();
        thisList.after('' +
            '<div data-ListId="'+editId+'" class="editingCon clearfix">\
            <div class="no2"><span>'+ editIndex +'</span></div>\
            <div class="name"><input  style="width: 80%;" type="text" value="'+editContactName+'"></div>\
            <div class="obligation"><select style="width: 90%;padding-left:0;"><option value="订舱">订舱</option><option value="操作">操作</option><option value="对账">对账</option><option value="财务">财务</option></select></div>\
            <div class="demp"><input style="width: 88%;" type="text" value="'+editContactDemp+'"></div>\
            <div class="duty"><input  style="width: 80%;" type="text" value="'+editContactDuty+'"></div>\
            <div class="tel"><input  style="width: 80%;" type="text" value="'+editContactTel+'"></div>\
            <div class="phone"><input  style="width: 88%;" type="text" value="'+editContactPhone+'"></div>\
            <div class="email"><input  style="width: 88%;" type="text" value="'+editContactEmail+'"></div>\
            <div class="qq"><input  style="width: 84%;" type="text" value="'+editContactQq+'"></div>\
            <div class="weChat"><input  style="width: 84%;" type="text" value="'+editContactweChat+'"></div>\
            <div class="address2"><input  style="width: 88%;" type="text" value="'+editContactAddress+'"></div>\
            <div class="operation"><a class="confirmEdit" href="javascript:void(0);">确定</a> <a class="cancelEdit redColor" href="javascript:void(0);">取消</a></div>\
            </div>');
        $('.obligation select').val(editContactObligation);
        thisList.remove();
    });

    /*修改联系人-取消*/
    $('.contactList ').on('click','.cancelEdit',function(){
        contactsObj.getContactsList();
    });
    /*修改联系人-确定*/
    $('.contactList ').on('click','.confirmEdit',function(){
        var EditConObj = {};
        var EditingCon = $(this).parents('.editingCon');
        var editLinkmanName = EditingCon.find('.name input').val();
        var editLinkmanObl= EditingCon.find('.obligation select').val();
        var editLinkmanPhone = EditingCon.find('.phone input').val();
        var editLinkmanEmail = EditingCon.find('.email input').val();
        if(editLinkmanName.length <=0){
            $('.name input').focus();
            return false;
        }
        if(editLinkmanPhone.length <=0){
            $('.phone input').focus();
            return false;
        }
        if(editLinkmanEmail.length <=0){
            $('.email input').focus();
            return false;
        }
        if(!isEmail(editLinkmanEmail)){
            $('#alertWords').text('邮箱格式不正确!');
            $('.alertShow').show().delay(3000).hide(300,function(){
                $('#alertWords').text('');
                $('.email input').focus();
            });
            return false;
        }
        EditConObj.id = EditingCon.attr('data-listId');
        EditConObj.name = editLinkmanName;
        EditConObj.obligation = editLinkmanObl;
        EditConObj.demp = EditingCon.find('.demp input').val();
        EditConObj.duty = EditingCon.find('.duty input').val();
        EditConObj.fixPhone = EditingCon.find('.tel input').val();
        EditConObj.phone = editLinkmanPhone;
        EditConObj.email = editLinkmanEmail;
        EditConObj.qq = EditingCon.find('.qq input').val();
        EditConObj.weChat = EditingCon.find('.weChat input').val();
        EditConObj.address = EditingCon.find('.address2 input').val();
        mm.removeObjWithArr(contactsList,EditConObj.id);
        contactsList.push(EditConObj);
        contactsObj.getContactsList();
		/*校验联系人是否存在*/
        $.ajax({
            url: 'http://' + gPathUrl + '/partner/details/checkPhone',
            type:'post',
            dataType:'json',
            contentType:'application/json;charset=utf-8',
            data:JSON.stringify({
                phone:editLinkmanPhone
            }),
            success:function(data){
                if(data.code == 200){
                    if(data.data){
                        if (confirm("联系人电话已存在，是否确认保存？")) {
                            EditConObj.id = EditingCon.attr('data-listId');
                            EditConObj.name = editLinkmanName;
                            EditConObj.obligation = editLinkmanObl;
                            EditConObj.demp = EditingCon.find('.demp input').val();
                            EditConObj.duty = EditingCon.find('.duty input').val();
                            EditConObj.fixPhone = EditingCon.find('.tel input').val();
                            EditConObj.phone = editLinkmanPhone;
                            EditConObj.email = editLinkmanEmail;
                            EditConObj.qq = EditingCon.find('.qq input').val();
                            EditConObj.weChat = EditingCon.find('.weChat input').val();
                            EditConObj.address = EditingCon.find('.address2 input').val();
                            mm.removeObjWithArr(contactsList,EditConObj.id);
                            contactsList.push(EditConObj);
                            contactsObj.getContactsList();
                        } else {
                            $('.addingCon .phone input').focus();
                            return false;
                        }
                    }else{
                        // 无重复联系人时 校验 新是否与增联系人集合中的其他联系人重复并提醒
                     var RepeatedPhoneCount = 0;

                     $(contactsList).each(function(){
                         if($(this).attr("phone")==editLinkmanPhone ){
                             RepeatedPhoneCount++;
                         }
                     })
                        if(RepeatedPhoneCount>1){

                            var statu = confirm("联系人电话已存在，是否确认保存？");
                            if(statu){
                                EditConObj.id = EditingCon.attr('data-listId');
                                EditConObj.name = editLinkmanName;
                                EditConObj.obligation = editLinkmanObl;
                                EditConObj.demp = EditingCon.find('.demp input').val();
                                EditConObj.duty = EditingCon.find('.duty input').val();
                                EditConObj.fixPhone = EditingCon.find('.tel input').val();
                                EditConObj.phone = editLinkmanPhone;
                                EditConObj.email = editLinkmanEmail;
                                EditConObj.qq = EditingCon.find('.qq input').val();
                                EditConObj.weChat = EditingCon.find('.weChat input').val();
                                EditConObj.address = EditingCon.find('.address2 input').val();
                                mm.removeObjWithArr(contactsList,EditConObj.id);
                                contactsList.push(EditConObj);
                                contactsObj.getContactsList();
                            }else {
                                $('.addingCon .phone input').focus();
                                return false;
                            }
                        } else {
                            EditConObj.id = EditingCon.attr('data-listId');
                            EditConObj.name = editLinkmanName;
                            EditConObj.obligation = editLinkmanObl;
                            EditConObj.demp = EditingCon.find('.demp input').val();
                            EditConObj.duty = EditingCon.find('.duty input').val();
                            EditConObj.fixPhone = EditingCon.find('.tel input').val();
                            EditConObj.phone = editLinkmanPhone;
                            EditConObj.email = editLinkmanEmail;
                            EditConObj.qq = EditingCon.find('.qq input').val();
                            EditConObj.weChat = EditingCon.find('.weChat input').val();
                            EditConObj.address = EditingCon.find('.address2 input').val();
                            mm.removeObjWithArr(contactsList,EditConObj.id);
                            contactsList.push(EditConObj);
                            contactsObj.getContactsList();
                        }

                    }
                }
            },
            error:function () {

            }
        });
    });
    /*联系人展开*/
    $('.spreadAdd').click(function(){
        $('.addressList').slideDown();
        $(this).hide();
        $('.packUpAdd').show();
    });
    /*联系人收起*/
    $('.packUpAdd').click(function(){
        $('.addressList').slideUp();
        $(this).hide();
        $('.spreadAdd').show();
    });
    /*联系人展开*/
    $('.spreadCon').click(function(){
        $('.contactList').slideDown();
        $(this).hide();
        $('.packUpCon').show();
    });
    /*联系人收起*/
    $('.packUpCon').click(function(){
        $('.contactList').slideUp();
        $(this).hide();
        $('.spreadCon').show();
    });

    /*业务范畴 复选框逻辑*/
    $('.businessCheckbox input').change(function(){
        businessCheckbox = [];
        $('#scopeBusiness').val(businessCheckbox.join(','));
        $('.businessCheckbox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    businessCheckbox.push($(n).val());
                }
            }
        });
        $('#scopeBusiness').val(businessCheckbox.join(','));
    });
    //AI
    $('.businessCheckbox').on('change','#AI',function(){
        var AIVal = $(this).prop('checked');
        if(AIVal){
            //勾选
            $('.AI').fadeIn();
        }else{//取消勾选
            $('.AI').fadeOut();
        }
    });
    $('.businessBox').on('change','.aiTypeCreditPeriod',function(){
        $('.aiTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.aiCreditPeriod',function(){
        $('.aiCreditPeriod').val($(this).val());
    });
    //AE
    $('.businessCheckbox').on('change','#AE',function(){
        var AEVal = $(this).prop('checked');
        if(AEVal){
            //勾选
            $('.AE').fadeIn();
        }else{//取消勾选
            $('.AE').fadeOut();
        }
    });
    $('.businessBox').on('change','.aeTypeCreditPeriod',function(){
        $('.aeTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.aeCreditPeriod',function(){
        $('.aeCreditPeriod').val($(this).val());
    });
    //SI
    $('.businessCheckbox').on('change','#SI',function(){
        var SIVal = $(this).prop('checked');
        if(SIVal){
            //勾选
            $('.SI').fadeIn();
        }else{//取消勾选
            $('.SI').fadeOut();
        }
    });
    $('.businessBox').on('change','.siTypeCreditPeriod',function(){
        $('.siTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.siCreditPeriod',function(){
        $('.siCreditPeriod').val($(this).val());
    });
    //SE
    $('.businessCheckbox').on('change','#SE',function(){
        var SEVal = $(this).prop('checked');
        if(SEVal){
            //勾选
            $('.SE').fadeIn();
        }else{//取消勾选
            $('.SE').fadeOut();
        }
    });
    $('.businessBox').on('change','.seTypeCreditPeriod',function(){
        $('.seTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.seCreditPeriod',function(){
        $('.seCreditPeriod').val($(this).val());
    });
    //TI
    $('.businessCheckbox').on('change','#TI',function(){
        var TIVal = $(this).prop('checked');
        if(TIVal){
            //勾选
            $('.TI').fadeIn();
        }else{//取消勾选
            $('.TI').fadeOut();
        }
    });
    $('.businessBox').on('change','.tiTypeCreditPeriod',function(){
        $('.tiTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.tiCreditPeriod',function(){
        $('.tiCreditPeriod').val($(this).val());
    });
    //TE
    $('.businessCheckbox').on('change','#TE',function(){
        var TEVal = $(this).prop('checked');
        if(TEVal){
            //勾选
            $('.TE').fadeIn();
        }else{//取消勾选
            $('.TE').fadeOut();
        }
    });
    $('.businessBox').on('change','.teTypeCreditPeriod',function(){
        $('.teTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.teCreditPeriod',function(){
        $('.teCreditPeriod').val($(this).val());
    });
    //OI
    $('.businessCheckbox').on('change','#OI',function(){
        var OIVal = $(this).prop('checked');
        if(OIVal){
            //勾选
            $('.OI').fadeIn();
        }else{//取消勾选
            $('.OI').fadeOut();
        }
    });
    $('.businessBox').on('change','.oiTypeCreditPeriod',function(){
        $('.oiTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.oiCreditPeriod',function(){
        $('.oiCreditPeriod').val($(this).val());
    });
    //OE
    $('.businessCheckbox').on('change','#OE',function(){
        var OEVal = $(this).prop('checked');
        if(OEVal){
            //勾选
            $('.OE').fadeIn();
        }else{//取消勾选
            $('.OE').fadeOut();
        }
    });
    $('.businessBox').on('change','.oeTypeCreditPeriod',function(){
        $('.oeTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.oeCreditPeriod',function(){
        $('.oeCreditPeriod').val($(this).val());
    });
    //IT
    $('.businessCheckbox').on('change','#IT',function(){
        var ITVal = $(this).prop('checked');
        if(ITVal){
            //勾选
            $('.IT').fadeIn();
        }else{//取消勾选
            $('.IT').fadeOut();
        }
    });
    $('.businessBox').on('change','.itTypeCreditPeriod',function(){
        $('.itTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.itCreditPeriod',function(){
        $('.itCreditPeriod').val($(this).val());
    });
    //DDN
    $('.businessCheckbox').on('change','#DDN',function(){
        var DDNVal = $(this).prop('checked');
        if(DDNVal){
            //勾选
            $('.DDN').fadeIn();
        }else{//取消勾选
            $('.DDN').fadeOut();
        }
    });
    $('.businessBox').on('change','.ddnTypeCreditPeriod',function(){
        $('.ddnTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.ddnCreditPeriod',function(){
        $('.ddnCreditPeriod').val($(this).val());
    });
    //YYOX
    $('.businessCheckbox').on('change','#YYOX',function(){
        var YYOXVal = $(this).prop('checked');
        if(YYOXVal){
            //勾选
            $('.YYOX').fadeIn();
        }else{//取消勾选
            $('.YYOX').fadeOut();
        }
    });
    $('.businessBox').on('change','.yyoxTypeCreditPeriod',function(){
        $('.yyoxTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.yyoxCreditPeriod',function(){
        $('.yyoxCreditPeriod').val($(this).val());
    });
    //Industrial
    $('.businessCheckbox').on('change','#Industrial',function(){
        var IndustrialVal = $(this).prop('checked');
        if(IndustrialVal){
            //勾选
            $('.Industrial').fadeIn();
        }else{//取消勾选
            $('.Industrial').fadeOut();
        }
    });
    $('.businessBox').on('change','.IndustrialTypeCreditPeriod',function(){
        $('.IndustrialTypeCreditPeriod').val($(this).val());
    });
    $('.businessBox').on('blur','.IndustrialCreditPeriod',function(){
        $('.IndustrialCreditPeriod').val($(this).val());
    });
    /*合作伙伴*/
    var PartnersCheckbox = [];
    $('.partnersCheckbox').on('change','.group1',function(){
        PartnersCheckbox = [];
        $('#partnerCategory').val(PartnersCheckbox.join(','));
        $('.partnersCheckbox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    PartnersCheckbox.push($(n).val());
                }
            }
        });
        if(PartnersCheckbox.length >0){
            $('.group2').attr({'checked':false,'disabled':true});
        }else{
            $('.group2').attr({'disabled':false});
        }
        $('#partnerCategory').val(PartnersCheckbox.join(','));
    });
    $('.partnersCheckbox').on('change','.group2',function(){
        PartnersCheckbox = [];
        $('#partnerCategory').val(PartnersCheckbox.join(','));
        $('.partnersCheckbox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    PartnersCheckbox.push($(n).val());
                }
            }
        });
        if(PartnersCheckbox.length >0){
            $('.group1').attr({'checked':false,'disabled':true});
        }else{
            $('.group1').attr({'disabled':false});
        }
        $('#partnerCategory').val(PartnersCheckbox.join(','));
    });

    /*客户分类*/
    $('#customerClass input').change(function(){
        var customerClass = [];
        $('#wbkhCustomerClass').val(customerClass.join(','));
        $('#customerClass').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    customerClass.push($(n).val());
                }
            }
        });
        $('#wbkhCustomerClass').val(customerClass.join(','));
    });
    /*/!*服务类别*!/
    $('.classOfServiceBox input').change(function(){
        var gxcyrClassOfServiceArr = [];
        $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
        $('.classOfServiceBox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    gxcyrClassOfServiceArr.push($(n).val());
                }
            }
        });
        $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
    });*/

    /*取消*/
    $('#callOff').click(function(){
        $(window).unbind("scroll");
        location.hash = vipspa.stringify('partnerManage');
    });
    /*表单提交*/
    $('#newForm').submit(function(){
        if ($('#savePartner').hasClass('disabled')){
            return false;
        }
        $('#linkmans').val(JSON.stringify(contactsList));
        $('#address').val(JSON.stringify(addressList));
        //判断地址和联系人必须维护一个
        if(addressList.length <=0){
            $('#alertWords').text('必须维护一个注册地址');
            $('.alertShow').show().delay(3000).hide(300,function(){
                $('#alertWords').text('');
                $('html,body').animate({
                    scrollTop: parseInt($('#contactAddress').offset().top)-200
                },300);
            });
            return false;
        }else{
            var hasFlag = false;
            $.each(addressList,function(i,n) {
                if(n.addressType == "注册地址"){
                    hasFlag = true;
                }
            });
            if(!hasFlag){
                $('#alertWords').text('必须维护一个注册地址');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('#contactAddress').offset().top)-200
                    },300);
                });
                return false;
            }
        }
        if(contactsList.length <=0){
            $('#alertWords').text('必须维护一个联系人!');
            $('.alertShow').show().delay(3000).hide(300,function(){
                $('#alertWords').text('');
                $('html,body').animate({
                    scrollTop: parseInt($('#contactInformation').offset().top)-200
                },300);
            });
            return false;
        }
        $(this).ajaxSubmit(options);
        return false;//阻止表单提交
    });
    /*文件上传*/
    $('#upload').on('click',function(){
        $('#nemo').trigger('click')
    });
});

var addressList = [
    /*{
     id:1,
     addressType:'注册地址',
     abbreviation:'来广营',
     address:'北京市朝阳区来广营地铁站望京城诚盈中心A座',
     zipCode:'10000'
     }*/
];
var addressObj = {
    getAddressList:function(){
        $('.addressList').empty();
        $.each(addressList,function(index,value){
            var str= '<div data-listId="'+value.id+'" class="list clearfix">\
                <div class="no"><span>'+(index+1)+'</span></div>\
                <div class="addressType"><span>'+value.addressType+'</span></div>\
                <div class="short"><span>'+value.abbreviation+'</span></div>\
                <div class="address"><span>'+value.address+'</span></div>\
                <div style="height:20px;" class="postcode"><span>'+value.zipCode+'</span></div>\
                <div class="operation"><a class="editAdd" href="javascript:void(0);">修改</a> <a class="delAdd redColor" href="javascript:void(0);">删除</a></div>\
                </div>';
            $('.addressList').append(str);
        });
    }
};

/*联系人*/
var contactsList = [
    /*{
        id:1,
        name:'张三',
        obligation:'市场推广',
        demp:'研发中心前端开发',
        duty:'负责前端项目的研发',
        fixPhone:'0527-8888888888',
        phone:'18810513105',
        email:'zhangshasmo@pj0l.com',
        address:'北京市朝阳区来广营西路望京诚盈中心A座9层'
    }*/
];
var contactsObj = {
    getContactsList:function(){
        $('.contactList').empty();
        $.each(contactsList,function(index,value){
            var str= '<div data-listId="'+value.id+'" class="list clearfix">\
                <div style="min-height:20px;word-wrap:break-word" class="no2"><span>'+(index+1)+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="name"><span>'+value.name+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="obligation"><span>'+value.obligation+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="demp"><span>'+value.demp+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="duty"><span>'+value.duty+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="tel"><span>'+value.fixPhone+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="phone"><span>'+value.phone+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="email"><span>'+value.email+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="qq"><span>'+value.qq+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="weChat"><span>'+value.weChat+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="address2"><span>'+value.address+'</span></div>\
                <div style="min-height:20px;word-wrap:break-word" class="operation"><a class="editAdd" href="javascript:void(0);">修改</a> <a class="delAdd redColor" href="javascript:void(0);">删除</a></div>\
                </div>';
            $('.contactList').append(str);
        });
    }
};

/*表单提交参数*/
var  options ={
    url : 'http://'+gPathUrl+'/partner/details/updatePartnerDetailsById',
    type:'post',
    dataType:'json',
    data:{
        email:$.cookie('front_useremail')
    },
    beforeSend:function(){
        $('#savePartner').addClass('disabled');
    },
    success:function(data) {
        if(data.code == '200'){
            alert('保存成功！');
            $(window).unbind("scroll");
            location.hash = vipspa.stringify('partnerManage')
        } else if(data.code == '400'){
            var errorCode = parseInt(data.data);
            if(errorCode == 0){
                $('#alertWords').text('助记码不能重复！');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('#mnemonicCode').offset().top)-200
                    },300);
                    $('#mnemonicCode').focus();
                });
            }else if(errorCode == 1){
                $('#alertWords').text('中文全称不能重复！');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('#chineseName').offset().top)-200
                    },300);
                    $('#chineseName').focus();
                });
            }else if(errorCode == 2){
                $('#alertWords').text('中文简称不能重复！');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('#chineseAbbreviation').offset().top)-200
                    },300);
                    $('#chineseAbbreviation').focus();
                });
            }else if(errorCode == 3){
                $('#alertWords').text('英文全称不能重复！');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('#englishName').offset().top)-200
                    },300);
                    $('#englishName').focus();
                });
            }else if(errorCode == 4){
                $('#alertWords').text('英文简称不能重复！');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('#englishAbbreviation').offset().top)-200
                    },300);
                    $('#englishAbbreviation').focus();
                });
            }else if(errorCode == 5){
                $('#alertWords').text('纳税人识别码不能重复！');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('.Taxpayers').offset().top)-200
                    },300);
                    $('.Taxpayers').focus();
                });
            }else if(errorCode == 6){
                $('#alertWords').text('代码不能重复！');
                $('.alertShow').show().delay(3000).hide(300,function(){
                    $('#alertWords').text('');
                    $('html,body').animate({
                        scrollTop: parseInt($('.code').offset().top)-200
                    },300);
                    $('.code').focus();
                });
            }
        }
    },error:function() {

    },complete:function() {
        $('#savePartner').removeClass('disabled');
    }
};
