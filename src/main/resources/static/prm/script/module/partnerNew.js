/**
 * Created by Administrator on 2017/11/22.
 */
frontcookie();
$(function(){
    $('body').scrollTop(0);
    var codes = $('.code');
    var nn = 0;//控制编辑的是哪个code
    var urlParameter = vipspa.parse();
    var partnerId = urlParameter.param.id;
    if(isNaN(partnerId)){
        $('#pId').val('');
    }else{
        $('#pId').val(partnerId);
    }
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

    /*/!*置灰分类详情*!/
    $('#externalClient input[type="text"]').val('').prop('disabled',true);//外部客户
    $('#externalClient input[type="checkbox"]').removeAttr('checked').prop('disabled',true);
    $('#externalClient select').removeAttr('selected').prop('disabled',true);

    $('#eachAgent input').prop('disabled',true);//互为代理
    $('#eachAgent select').prop('disabled',true);
    $('#overseasAgency input').prop('disabled',true);//海外代理
    $('#overseasAgency select').prop('disabled',true);
    $('#trunkCarrier input').prop('disabled',true);//干线承运人
    $('#trunkCarrier select').prop('disabled',true);
    $('#uncontrollableSupplier input').prop('disabled',true);//不可控供应商
    $('#uncontrollableSupplier select').prop('disabled',true);
    $('#extendServiceProviders input').prop('disabled',true);//延伸服务供应商
    $('#extendServiceProviders select').prop('disabled',true);
    $('#consigneeAndConsigner input').prop('disabled',true);//收发货人
    $('#consigneeAndConsigner select').prop('disabled',true);
    $('#settlementObject input').prop('disabled',true);//结算对象
    $('#settlementObject select').prop('disabled',true);
     */
    var externalClientMark = $('#externalClient .mark');
    externalClientMark.css('color','#fff');

    $('.partnersCheckbox').on('change','.externalClient',function(){
        var thisVal = $(this).prop('checked');
        if(thisVal){
            //勾选
            externalClientMark.css('color','#ed6e56');
            $('input[name="wbkhCustomerClass"]').prop('required',true);//客户分类
            $('input[name="wbkhCreditRating"]').prop('required',true);//信用等级
            $('input[name="wbkhTypeCreditPeriod"]').prop('required',true);//信用期限类型
            $('input[name="wbkhCreditPeriod"]').prop('required',true);//信用期限（天）
            $('input[name="wbkhLineCredit"]').prop('required',true);//信用额度(万元)
            $('input[name="wbkhInvoiceType"]').prop('required',true);//开票类型
            $('input[name="headingCode"]').prop('required',true);//纳税人识别码
        }else{//取消勾选
            externalClientMark.css('color','#fff');
            $('input[name="wbkhCustomerClass"]').prop('required',false);//客户分类
            $('input[name="wbkhCreditRating"]').prop('required',false);//信用等级
            $('input[name="wbkhTypeCreditPeriod"]').prop('required',false);//信用期限类型
            $('input[name="wbkhCreditPeriod"]').prop('required',false);//信用期限（天）
            $('input[name="wbkhLineCredit"]').prop('required',false);//信用额度(万元)
            $('input[name="wbkhInvoiceType"]').prop('required',false);//开票类型
            $('input[name="headingCode"]').prop('required',false);//纳税人识别码
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
            $('input[name="hwdlTaxRate"]').prop('required',true);//进项税率%
            $('input[name="wbkhCreditRating"]').prop('required',true);//信用等级
            $('input[name="wbkhTypeCreditPeriod"]').prop('required',true);//信用期限类型
            $('input[name="wbkhCreditPeriod"]').prop('required',true);//信用期限（天）
            $('input[name="wbkhLineCredit"]').prop('required',true);//信用额度(万元)
            $('input[name="wbkhInvoiceType"]').prop('required',true);//开票类型
            $('input[name="headingCode"]').prop('required',true);//纳税人识别码
        }else{//取消勾选
            eachAgentMark.css('color','#fff');
        }
    });


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
            checkRepeat(that,'中文全称')
        }
    });
    /*校验中文简称*/
    $('#chineseAbbreviation').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat(that,'中文简称')
        }
    });
    /*校验英文名称*/
    $('#englishName').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat(that,'英文全称')
        }
    });
    /*校验英文简称*/
    $('#englishAbbreviation').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat(that,'英文简称')
        }
    });
    /*校验纳税人识别码*/
    $('.headingCode').blur(function(){
        var that = this;
        if($(this).val().length >0){
            checkRepeat(that,'纳税人识别码')
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
    /*信用等级同时改变*/
    $('.wbkhCreditRating').change(function(){
        $('.wbkhCreditRating').val($(this).val());
        var selectVal = $(this).val().slice(2,7);
        if(selectVal=='协议/保函'){
            $('.wbkhCreditPeriod').attr('disabled',false);
            $('.wbkhLineCredit').attr('disabled',false);
            $('.wbkhIsPayForAnother').attr('disabled',false);
        }else if(selectVal=='签约在途'){
            $('.wbkhIsPayForAnother').attr('disabled',false);
        }else{
            $('.wbkhCreditPeriod').val('').attr('disabled',true);
            $('.wbkhLineCredit').val('').attr('disabled',true);
            $('.wbkhIsPayForAnother').attr('disabled',true);
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
    /*开票类型*/
    $('.wbkhInvoiceType').change(function(){
        var selectInvoiceType = $(this).val();
        $('.wbkhInvoiceType').val(selectInvoiceType);
        $('.invoiceTypeTitle').text(selectInvoiceType);
        if(selectInvoiceType =="增值税普票"){
            $('.bankInfo .mark').hide();
            $('.sbmMark').show();
            $('.bankInfo input').attr('required',false);
            $('.Taxpayers').attr('required',true);
        }else if(selectInvoiceType =="增值税专票"){
            $('.bankInfo .mark').show();
            $('.bankInfo input').attr('required',true);
        }else if(selectInvoiceType =="DebitNote"){
            $('.bankInfo .mark').hide();
            $('.bankInfo input').attr('required',false);
        }
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
    /*服务类别*/
    $('.gxcyrClassOfServiceBox input[type=checkbox]').change(function () {
        var className = $(this).prop('className');
        var isChecked = $(this).prop('checked');
        $('.'+className).prop('checked',isChecked)
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
        }else{
            $('input[name="sfhrIsConsignee"]').val('0');
            $('.consigneeInput input').attr('disabled',true);
        }
    });
    /*发货人*/
    $('#sfhrIsShipper').change(function(){
        var isShipper = $(this).prop('checked');
        if(isShipper){
            $('input[name="sfhrIsShipper"]').val('1');
            //与发货人地址相同的逻辑
            $('input[name="sfhrIsConsigneesAddress"]').val('0');
            $('#sfhrIsConsigneesAddress').prop('checked',false);
            $('.shipperInput input').attr('disabled',false);
        }else{
            $('input[name="sfhrIsShipper"]').val('0');
            $('.shipperInput input').attr('disabled',true);
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
        }else{
            $('input[name="sfhrIsShipper"]').val('0');
            $('input[name="sfhrIsConsigneesAddress"]').val('0');
            $('#sfhrIsShipper').prop('checked',false);
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
        $('.addingAdd').remove();
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
    });
    /*联系人收起*/
    $('.packUpAdd').click(function(){
        $('.addressList').slideUp();
    });
    /*联系人展开*/
    $('.spreadCon').click(function(){
        $('.contactList').slideDown();
    });
    /*联系人收起*/
    $('.packUpCon').click(function(){
        $('.contactList').slideUp();
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
    $('.partnersCheckbox input').change(function(){
        var PartnersCheckbox = [];
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
    //服务类别
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
    });

    /*取消提交*/
    $('#callOff').click(function(){
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
            location.hash = vipspa.stringify('partnerManage');
        }
    },error:function() {
            alert('保存失败，请重试！')
    },complete:function() {

    }
};
/**
 * 校验名称字段重复
 * @param that
 * @param name
 */
function  checkRepeat(that,name){
    $.ajax({
        url: 'http://' + gPathUrl + '/partner/details/verifyValueRepeat',
        type: 'get',
        data:{
            fieldName:$(that).attr('name'),
            fieldValue:$(that).val()
        },
        success: function (data) {
            if(data.code == 200){
                if(!data.data){
                    alert(''+name+'不允许重复！');
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