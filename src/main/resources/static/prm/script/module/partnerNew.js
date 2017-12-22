/**
 * Created by Administrator on 2017/11/22.
 */
frontcookie();
$(function(){
    $("body,html").scrollTop(0);
    var codes = $('.code');
    var nn = 0;//控制编辑的是哪个code
    var urlParameter = vipspa.parse();
    var partnerId = urlParameter.param.id;
    if(isNaN(partnerId)){
        $('#pId').val('');
    }else{
        $('#pId').val(partnerId);
    }
    var parentsCode = '';
    /*控制代码填写区域*/
    if(isNaN(partnerId)){
        codes.prop('disabled','disabled');
        $('.code1').prop('disabled',false);
    }else{
        $.ajax({
            url: 'http://' + gPathUrl + '/partner/details/getParentCodeList',
            type: 'get',
            data: {
                id:partnerId
            },
            success: function (data) {
                if(data.code == '200'){
                    $.each(data.data,function (index,value) {
                        codes.eq(index).val(value).attr('disabled','disabled');
                        nn = ++index;
                    });
                    codes.each(function (i,val) {
                        if(nn <i){
                            $('.code').eq(i).attr('disabled','disabled');
                        }
                    })
                }
            }
        })
    }
    /*加载信用等级*/
    qualityRating();
    //光标移开，校验代码字段的填写
    codes.blur(function(){
        var that = this;
        if($(this).val().length > 0){
            $.ajax({
                url: 'http://' + gPathUrl + '/partner/details/getCodeLength',
                type: 'get',
                success: function (data) {
                    if(data.code == 200){
                        if(parseInt($(that).val().length) != parseInt(data.data[nn])){
                            alert('必须'+data.data[nn]+'位');
                            $(that).val('');
                            return false;
                        }
                    }
                }
            })
        }
    });
    /*校验中文名称*/
    $('#chineseName').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat('chineseName',that,'中文全称')
        }
    });
    /*校验中文简称*/
    $('#chineseAbbreviation').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat('chineseAbbreviation',that,'中文简称')
        }
    });
    /*校验英文名称*/
    $('#englishName').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat('englishName',that,'英文全称')
        }
    });
    /*校验英文简称*/
    $('#englishAbbreviation').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat('englishAbbreviation',that,'英文简称')
        }
    });
    /*校验纳税人识别码*/
    $('.headingCode').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat('headingCode',that,'纳税人识别码')
        }
    });

    /*提醒接受者逻辑*/
    $('#dutyInput').keyup(function(){
        var that = this;
        var inputVal = $('#dutyInput').val();
        if ( inputVal.length >0) {
            $('.userNameLi').empty();
            $.ajax({
                url: 'http://' + oaPathUrl + '/oa/user/list.do',
                type: 'get',
                data: {
                    username: inputVal
                },
                success: function (data) {
                    if(data.data.pagination.list.length >=1){
                        $('.userNameLi').show();
                        $('.warning').text('');
                        $.each(data.data.pagination.list,function(index,value){
                            var str = ' <li data-Id = '+value.id+' class="nameList"><span class="staffName">'+value.username+'</span><span>'+value.companyname+'</span><span>'+value.dempname+'</span><span>'+value.postname+'</span></li>'
                            $(str).appendTo('.userNameLi');
                        });
                    }else if(data.data.pagination.list.length <1){
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
    /*flow*/
    StatusOn(1,13);
    /*滚动*/
    $('#navigationBar').on('click','.status-div',function(){
        var thisIndex = parseInt($(this).index());
       /* StatusOn(thisIndex+1,13);*/
        var _top = parseInt($('#newForm .lump').eq(thisIndex).offset().top);
        $("body,html").animate({scrollTop:(_top-288)},500);
        console.log(_top-288);
    });
    var _top0 = parseInt($('#newForm .lump').eq(0).offset().top)-288;
    var _top1 = parseInt($('#newForm .lump').eq(1).offset().top)-288;
    var _top2 = parseInt($('#newForm .lump').eq(2).offset().top)-288;
    var _top3 = parseInt($('#newForm .lump').eq(3).offset().top)-288;
    var _top4 = parseInt($('#newForm .lump').eq(4).offset().top)-288;
    var _top5 = parseInt($('#newForm .lump').eq(5).offset().top)-288;
    var _top6 = parseInt($('#newForm .lump').eq(6).offset().top)-288;
    var _top7 = parseInt($('#newForm .lump').eq(7).offset().top)-288;
    var _top8 = parseInt($('#newForm .lump').eq(8).offset().top)-288;
    var _top9 = parseInt($('#newForm .lump').eq(9).offset().top)-288;
    var _top10 = parseInt($('#newForm .lump').eq(10).offset().top)-288;
    var _top11 = parseInt($('#newForm .lump').eq(11).offset().top)-288;
    var _top12 = parseInt($('#newForm .lump').eq(12).offset().top)-288;
    $(window).scroll(function() {
        var htmlScrollTop = parseInt($(document).scrollTop())+50;
        if(0 < htmlScrollTop && htmlScrollTop < _top1){ //基础表单
            StatusOn(1,13);
        }else if(_top1 <= htmlScrollTop && htmlScrollTop<_top2){//联系人资料
            StatusOn(2,13);
        }else if(_top2 <= htmlScrollTop && htmlScrollTop<_top3){//联系人资料
            StatusOn(3,13);
        }else if(_top3 <= htmlScrollTop && htmlScrollTop < _top4){//业务范畴
            StatusOn(4,13);
        }else if(_top4 <= htmlScrollTop && htmlScrollTop<_top5){//合作伙伴分类
            StatusOn(5,13);
        }else if(_top5 <= htmlScrollTop && htmlScrollTop<_top6){//外部客户
            StatusOn(6,13);
        }else if(_top6 <= htmlScrollTop && htmlScrollTop<_top7){//互为代理
            StatusOn(7,13);
        }else if(_top7 <= htmlScrollTop && htmlScrollTop<_top8){//海外代理
            StatusOn(8,13);
        }else if(_top8 <= htmlScrollTop && htmlScrollTop<_top9){//干线承运人
            StatusOn(9,13);
        }else if(_top9 <= htmlScrollTop && htmlScrollTop<_top10){//不可控供应商
            StatusOn(10,13);
        }else if(_top10 <= htmlScrollTop && htmlScrollTop<_top11){//延伸服务供应商
            StatusOn(11,13);
        }else if(_top11 <= htmlScrollTop && htmlScrollTop<_top12){//收发货人
            StatusOn(12,13);
        }else if((_top12) <= htmlScrollTop){//收发货人
            StatusOn(13,13);
        }
    });


/*选中合作伙伴分类显示必填项校验*/
    /*外部客户*/
    var externalClientMark = $('#externalClient .mark');
    externalClientMark.css('color','#fff');
    var externalClientFlag = 0;
    $('.partnersCheckbox').on('change','.externalClient',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            externalClientFlag = 1;
            externalClientMark.css('color','#ed6e56');
            $('#externalClient .must').prop('required',true);
        }else{//取消勾选
            externalClientFlag = 0;
            externalClientMark.css('color','#fff');
            $('#externalClient .must').prop('required',false);
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
            $('.bankInfo1 .hCMark1').css('color','#ed6e56');
            $('.bankInfo1 input').attr('required',false);
            $('.Taxpayers1').attr('required',true);
        }else if(selectInvoiceType =="增值税专票"){
            $('.bankInfo1 .mark').show().css('color','#ed6e56');
            $('.bankInfo1 input').attr('required',true);
        }else if(selectInvoiceType =="DebitNote"){
            $('.bankInfo1 .mark').css('color','#fff');
            $('.bankInfo1 input').attr('required',false);
        }
    });


    /*互为代理*/
    var eachAgentMark = $('#eachAgent .mark');
    eachAgentMark.css('color','#fff');
    $('.partnersCheckbox').on('change','.eachAgent',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            eachAgentMark.css('color','#ed6e56');
            $('#eachAgent .must').prop('required',true);
        }else{//取消勾选
            eachAgentMark.css('color','#fff');
            $('#eachAgent .must').prop('required',false);
        }
    });
    /*开票类型 2*/
    $('.wbkhInvoiceType2').change(function(){
        var selectInvoiceType2 = $(this).val();
        $('.wbkhInvoiceType').val(selectInvoiceType2);
        $('.invoiceTypeTitle').text(selectInvoiceType2);
        if(selectInvoiceType2 =="增值税普票"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 .hCMark2').css('color','#ed6e56');
            $('.bankInfo2 input').attr('required',false);
            $('.Taxpayers2').attr('required',true);
        }else if(selectInvoiceType2 =="增值税专票"){
            $('.bankInfo2 .mark').show().css('color','#ed6e56');
            $('.bankInfo2 input').attr('required',true);
        }else if(selectInvoiceType2 =="DebitNote"){
            $('.bankInfo2 .mark').css('color','#fff');
            $('.bankInfo2 input').attr('required',false);
        }
    });

    /*海外代理*/
    var overseasAgencyMark = $('#overseasAgency .mark');
    overseasAgencyMark.css('color','#fff');
    $('.partnersCheckbox').on('change','.overseasAgency',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            overseasAgencyMark.css('color','#ed6e56');
            $('#overseasAgency .must').prop('required',true);
        }else{//取消勾选
            overseasAgencyMark.css('color','#fff');
            $('#overseasAgency .must').prop('required',false);
        }
    });
    /*干线承运人*/
    var trunkCarrierMark = $('#trunkCarrier .mark');
    trunkCarrierMark.css('color','#fff');
    $('.partnersCheckbox').on('change','.trunkCarrier',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            trunkCarrierMark.css('color','#ed6e56');
            $('#trunkCarrier .must').prop('required',true);
        }else{//取消勾选
            trunkCarrierMark.css('color','#fff');
            $('#trunkCarrier .must').prop('required',false);
        }
    });
    /*控制三个服务类别同时变动*/
    $('.gxcyrClassOfServiceBox input[type=checkbox]').change(function () {
        var className = $(this).prop('className');
        var isChecked = $(this).prop('checked');
        $('.'+className).prop('checked',isChecked)
    });
    //服务类别
    $('.classOfServiceBox input').change(function(){
        var gxcyrClassOfServiceArr = [];
        $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
        $('.service1').val(gxcyrClassOfServiceArr.join(','));
        $('.service2').val(gxcyrClassOfServiceArr.join(','));
        $('.service3').val(gxcyrClassOfServiceArr.join(','));
        $('.classOfServiceBox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    gxcyrClassOfServiceArr.push($(n).val());
                }
            }
        });
        $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));
        $('.service1').val(gxcyrClassOfServiceArr.join(','));
        $('.service2').val(gxcyrClassOfServiceArr.join(','));
        $('.service3').val(gxcyrClassOfServiceArr.join(','));
    });




    /*不可控供应商*/
    var uncontrollableSupplierMark = $('#uncontrollableSupplier .mark');
    uncontrollableSupplierMark.css('color','#fff');
    $('.partnersCheckbox').on('change','.uncontrollableSupplier',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            uncontrollableSupplierMark.css('color','#ed6e56');
            $('#uncontrollableSupplier .must').prop('required',true);
        }else{//取消勾选
            uncontrollableSupplierMark.css('color','#fff');
            $('#uncontrollableSupplier .must').prop('required',false);
        }
    });

    /*延伸服务供应商*/
    var extendServiceProvidersMark = $('#extendServiceProviders .mark');
    extendServiceProvidersMark.css('color','#fff');
    $('.partnersCheckbox').on('change','.extendServiceProviders',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            extendServiceProvidersMark.css('color','#ed6e56');
            $('#extendServiceProviders .must').prop('required',true);
        }else{//取消勾选
            extendServiceProvidersMark.css('color','#fff');
            $('#extendServiceProviders .must').prop('required',false);
        }
    });

    /*收发货人*/
    var consigneeAndConsignerMark = $('#consigneeAndConsigner .mark');
    consigneeAndConsignerMark.css('color','#fff');
    $('.partnersCheckbox').on('change','.consigneeAndConsigner',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            consigneeAndConsignerMark.css('color','#ed6e56');
           /* $('#consigneeAndConsigner input').prop('required',true)*/
        }else{//取消勾选
            consigneeAndConsignerMark.css('color','#fff');
            /*$('#consigneeAndConsigner input').prop('required',false)*/
        }
    });

    /*结算对象*/
    var settlementObjectMark = $('#settlementObject .mark');
    settlementObjectMark.css('color','#fff');
    $('.partnersCheckbox').on('change','.settlementObject',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            settlementObjectMark.css('color','#ed6e56');
            $('#settlementObject .must').prop('required',true);
        }else{//取消勾选
            settlementObjectMark.css('color','#fff');
            $('#settlementObject .must').prop('required',false);
        }
    });
    /*开票类型 2*/
    $('.wbkhInvoiceType3').change(function(){
        var selectInvoiceType3 = $(this).val();
        $('.wbkhInvoiceType').val(selectInvoiceType3);
        $('.invoiceTypeTitle').text(selectInvoiceType3);
        if(selectInvoiceType3 =="增值税普票"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 .hCMark3').show().css('color','#ed6e56');
            $('.bankInfo3 input').attr('required',false);
            $('.Taxpayers3').attr('required',true);
        }else if(selectInvoiceType3 =="增值税专票"){
            $('.bankInfo3 .mark').show().css('color','#ed6e56');
            $('.bankInfo3 input').attr('required',true);
        }else if(selectInvoiceType3 =="DebitNote"){
            $('.bankInfo3 .mark').css('color','#fff');
            $('.bankInfo3 input').attr('required',false);
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
        }else if(selectVal=='签约在途'){
            $('.wbkhCreditPeriod').val('').attr('disabled',true);
            $('.wbkhLineCredit').val('').attr('disabled',true);
            $('.wbkhIsPayForAnother').attr('disabled',false);
        }else{
            $('.wbkhCreditPeriod').val('').attr('disabled',true);
            $('.wbkhLineCredit').val('').attr('disabled',true);
            $('.wbkhIsPayForAnother').prop('checked',false);
            $('.wbkhIsPayForAnother').attr('disabled',true);
            $('input[name="wbkhIsPayForAnother"]').val('0');
            $('.daiDian ').val('').attr('disabled',true);
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
            $('.daiDian').attr('disabled',true);
        }
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
    $('.partnersCheckbox').on('change','input',function(){
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
        var delListId = $(this).parents('.list').find('.no span').text();
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

    //操作联系人
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
            <div class="no"><span>'+ listNum +'</span></div>\
            <div class="name"><input  style="width: 80%;" type="text"></div>\
            <div class="obligation"><input style="width: 80%;" type="text"></div>\
            <div class="demp"><input style="width: 88%;" type="text"></div>\
            <div class="duty"><input  style="width: 80%;" type="text"></div>\
            <div class="tel"><input  style="width: 80%;" type="text"></div>\
            <div class="phone"><input  style="width: 80%;" type="text"></div>\
            <div class="email"><input  style="width: 90%;" type="text"></div>\
            <div class="address2"><input  style="width: 90%;" type="text"></div>\
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
        var addLinkmanObl= addingCon.find('.obligation input').val();
        var addLinkmanPhone = addingCon.find('.phone input').val();
        if(addLinkmanName.length <=0){
            $('.name input').focus();
            return false;
        }
        if(addLinkmanObl.length <=0){
            $('.obligation input').focus();
            return false;
        }
        if(addLinkmanPhone.length <=0){
            $('.phone input').focus();
            return false;
        }
        newConObj.id = addingCon.find('.no').find('span').text();
        newConObj.name = addLinkmanName;
        newConObj.obligation = addLinkmanObl;
        newConObj.demp = addingCon.find('.demp input').val();
        newConObj.duty = addingCon.find('.duty input').val();
        newConObj.fixPhone = addingCon.find('.tel input').val();
        newConObj.phone = addLinkmanPhone;
        newConObj.email = addingCon.find('.email input').val();
        newConObj.address = addingCon.find('.address2 input').val();
        contactsList.push(newConObj);
        contactsObj.getContactsList();
    });
    /*删除联系人*/
    $('.contactList ').on('click','.delAdd',function(){
        var delListId = $(this).parents('.list').find('.no span').text();
        mm.removeObjWithArr(contactsList,delListId);
        contactsObj.getContactsList();
    });
    /*修改联系人*/
    $('.contactList ').on('click','.editAdd',function(){
        var thisList = $(this).parents('.list');
        var editId = thisList.attr('data-ListId');
        var editIndex = thisList.find('.no').find('span').text();
        var editContactName = thisList.find('.name span').text();
        var editContactObligation = thisList.find('.obligation span').text();
        var editContactDemp = thisList.find('.demp span').text();
        var editContactDuty = thisList.find('.duty span').text();
        var editContactTel = thisList.find('.tel span').text();
        var editContactPhone = thisList.find('.phone span').text();
        var editContactEmail = thisList.find('.email span').text();
        var editContactAddress = thisList.find('.address2 span').text();
        thisList.after('' +
            '<div data-ListId="'+editId+'" class="editingCon clearfix">\
            <div class="no"><span>'+ editIndex +'</span></div>\
            <div class="name"><input  style="width: 80%;" type="text" value="'+editContactName+'"></div>\
            <div class="obligation"><input style="width: 80%;" type="text" value="'+editContactObligation+'"></div>\
            <div class="demp"><input style="width: 88%;" type="text" value="'+editContactDemp+'"></div>\
            <div class="duty"><input  style="width: 80%;" type="text" value="'+editContactDuty+'"></div>\
            <div class="tel"><input  style="width: 80%;" type="text" value="'+editContactTel+'"></div>\
            <div class="phone"><input  style="width: 80%;" type="text" value="'+editContactPhone+'"></div>\
            <div class="email"><input  style="width: 80%;" type="text" value="'+editContactEmail+'"></div>\
            <div class="address2"><input  style="width: 80%;" type="text" value="'+editContactAddress+'"></div>\
            <div class="operation"><a class="confirmEdit" href="javascript:void(0);">确定</a> <a class="cancelEdit redColor" href="javascript:void(0);">取消</a></div>\
            </div>');
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
        var editLinkmanObl= EditingCon.find('.obligation input').val();
        var editLinkmanPhone = EditingCon.find('.phone input').val();
        if(editLinkmanName.length <=0){
            $('.name input').focus();
            return false;
        }
        if(editLinkmanObl.length <=0){
            $('.obligation input').focus();
            return false;
        }
        if(editLinkmanPhone.length <=0){
            $('.phone input').focus();
            return false;
        }
        EditConObj.id = EditingCon.attr('data-listId');
        EditConObj.name = editLinkmanName;
        EditConObj.obligation = editLinkmanObl;
        EditConObj.demp = EditingCon.find('.demp input').val();
        EditConObj.duty = EditingCon.find('.duty input').val();
        EditConObj.fixPhone = EditingCon.find('.tel input').val();
        EditConObj.phone = editLinkmanPhone;
        EditConObj.email = EditingCon.find('.email input').val();
        EditConObj.address = EditingCon.find('.address2 input').val();
        mm.removeObjWithArr(contactsList,EditConObj.id);
        contactsList.push(EditConObj);
        contactsObj.getContactsList();
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
    /*合作伙伴*/
    var PartnersCheckbox = [];
    $('.partnersCheckbox input').change(function(){
        PartnersCheckbox = [];
        $('#partnerCategory').val(PartnersCheckbox.join(','));
        $('.partnersCheckbox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    PartnersCheckbox.push($(n).val());
                }
            }
        });
        $('#partnerCategory').val(PartnersCheckbox.join(','));
    });
    /*取消提交*/
    $('#callOff').click(function(){
        $(window).unbind("scroll");
        location.hash = vipspa.stringify('partnerManage');
    });
    /*表单提交*/
    $('#newForm').submit(function(){
        $('#linkmans').val(JSON.stringify(contactsList));
        $('#address').val(JSON.stringify(addressList));
        //判断地址和联系人必须维护一个
        if(addressList.length <=0){
            alert('必须维护一个注册地址');
            return false;
        }else{
            var hasFlag = false;
            $.each(addressList,function(i,n) {
                if(n.addressType == "注册地址"){
                    hasFlag = true;
                }
            });
            if(!hasFlag){
                alert('必须维护一个注册地址');
                return false;
            }
        }
        if(contactsList.length <=0){
            alert('必须维护一个联系人！');
            return false;
        }
        $(this).ajaxSubmit(options);
       /* ('#savePartner').attr('disabled',true);//阻止重复点击多次提交*/
        return false;//阻止表单提交
    })
});
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
            if(data.data[0].protocolType =='协议/保函'){
                 $('.wbkhCreditPeriod').attr('disabled',false);
                 $('.wbkhLineCredit').attr('disabled',false);
                $('.wbkhIsPayForAnother').attr('disabled',false);
            }else if(data.data[0].protocolType =='签约在途 '){
                $('.wbkhIsPayForAnother').attr('disabled',false);
            }
        }
    });
}

var addressList = [];
var addressObj = {
    getAddressList:function(){
        $('.addressList').empty();
        $.each(addressList,function(index,value){
            var str= '<div data-listId="'+value.id+'" class="list clearfix">\
                <div class="no"><span>'+(index+1)+'</span></div>\
                <div class="addressType"><span>'+value.addressType+'</span></div>\
                <div class="short"><span>'+value.abbreviation+'</span></div>\
                <div class="address"><span>'+value.address+'</span></div>\
                <div style="height:20px;"  class="postcode"><span>'+value.zipCode+'</span></div>\
                <div class="operation"><a class="editAdd" href="javascript:void(0);">修改</a> <a class="delAdd redColor" href="javascript:void(0);">删除</a></div>\
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
                <div style="height:20px;" class="no"><span>'+(index+1)+'</span></div>\
                <div style="height:20px;" class="name"><span>'+value.name+'</span></div>\
                <div style="height:20px;" class="obligation"><span>'+value.obligation+'</span></div>\
                <div style="height:20px;" class="demp"><span>'+value.demp+'</span></div>\
                <div style="height:20px;" class="duty"><span>'+value.duty+'</span></div>\
                <div style="height:20px;" class="tel"><span>'+value.fixPhone+'</span></div>\
                <div style="height:20px;" class="phone"><span>'+value.phone+'</span></div>\
                <div style="height:20px;" class="email"><span>'+value.email+'</span></div>\
                <div style="height:20px;" class="address2"><span>'+value.address+'</span></div>\
                <div style="height:20px;" class="operation"><a class="editAdd" href="javascript:void(0);">修改</a> <a class="delAdd redColor" href="javascript:void(0);">删除</a></div>\
                </div>';
            $('.contactList').append(str);
        });
    }
};

/*表单提交参数*/
var  options ={
    url : 'http://'+gPathUrl+'/partner/details/insertPartnerDetails',
    type:'post',
    dataType:'json',
    data:{
        email:$.cookie('front_useremail')
    },
    success:function(data) {
        if(data.code == '200'){
            alert('保存成功！');
            $(window).unbind("scroll");
            location.hash = vipspa.stringify('partnerManage');
        }
    },error:function() {
            alert('保存失败，请重试！')
    },complete:function() {
       /* $('#savePartner').removeAttr('disabled');*/
    }
};
/**
 * 校验名称字段重复
 * @param that
 * @param name
 */
function  checkRepeat(name,that,worlds){
    $.ajax({
        url: 'http://' + gPathUrl + '/partner/details/verifyValueRepeat',
        type: 'get',
        data:{
            fieldName:name,
            fieldValue:$(that).val()
        },
        success: function (data) {
            if(data.code == 200){
                if(!data.data){
                    alert(''+worlds+'不允许重复！');
                    $(that).val('');
                    return false;
                }
            }
        }
    })
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