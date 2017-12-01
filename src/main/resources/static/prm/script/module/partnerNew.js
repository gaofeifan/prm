/**
 * Created by Administrator on 2017/11/22.
 */
$(function(){
    var codes = $('.code');
    var nn = 0;//控制编辑的是哪个code
    var urlParameter = vipspa.parse();
    var partnerId = urlParameter.param.id;
    $('#pId').val(partnerId);
    console.log(partnerId);
    /*控制代码填写区域*/
    if(partnerId == 'null'){
        codes.prop('disabled','disabled');
        $('.code1').prop('disabled',false);
    }else if(!!partnerId){
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
    //光标移开，校验代码字段的填写
    codes.blur(function(){
        var that = this;
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
    });
    /*校验中文名称*/
    $('#chineseName').blur(function(){
        var that = this;
        checkRepeat(that,'中文全称')
    });
    /*校验中文简称*/
    $('#chineseAbbreviation').blur(function(){
        var that = this;
        checkRepeat(that,'中文简称')
    });
    /*校验英文名称*/
    $('#englishName').blur(function(){
        var that = this;
        checkRepeat(that,'英文全称')
    });
    /*校验英文简称*/
    $('#englishAbbreviation').blur(function(){
        var that = this;
        checkRepeat(that,'英文简称')
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
            $('input[name="disableRemark"]').attr('required',true);
            $('.disableMark').css('color','#ed6e56');
        }else{
            $('input[name="isDisable"]').val('0');
        }
    });
    /*信用等级同时改变*/
    $('.wbkhCreditRating').change(function(){
        $('.wbkhCreditRating').val($(this).val());
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
    /*开票类型额度*/
    $('input[name="wbkhInvoiceType"]').change(function(){
        console.log($(this).val());
        $('input[name="wbkhInvoiceType"]').val($(this).val());
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
        }else{
            $('input[name="sfhrIsConsignee"]').val('0');
        }
    });
    /*发货人*/
    $('#sfhrIsShipper').change(function(){
        var isShipper = $(this).prop('checked');
        if(isShipper){
            $('input[name="sfhrIsShipper"]').val('1');
        }else{
            $('input[name="sfhrIsShipper"]').val('0');
        }
    });
    /*与收货人地址相同*/
    $('#sfhrIsConsigneesAddress').change(function(){
        var isShipper = $(this).prop('checked');
        if(isShipper){
            $('input[name="sfhrIsConsigneesAddress"]').val('1');
        }else{
            $('input[name="sfhrIsConsigneesAddress"]').val('0');
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
            <div class="addressType"><input  style="width: 80%;" type="text"></div>\
            <div class="short"><input style="width: 80%;" type="text"></div>\
            <div class="address"><input style="width: 88%;" type="text"></div>\
            <div class="postcode"><input  style="width: 80%;" type="text"></div>\
            <div class="operation"><a class="confirmAdd" href="javascript:void(0);">确定</a> <a class="cancelAdd redColor" href="javascript:void(0);">取消</a></div>\
            </div>');
    });
    /*点击取消*/
    $('.addressList ').on('click','.cancelAdd',function(){
        $('.addingAdd').remove();
    });
    /*点击确定*/
    $('.addressList ').on('click','.confirmAdd',function(){
        var newAddObj = {};
        var addingAdd = $('.addingAdd');
        newAddObj.id = addingAdd.find('.no').find('span').text();
        newAddObj.addressType = addingAdd.find('.addressType input').val();
        newAddObj.abbreviation = addingAdd.find('.short input').val();
        newAddObj.address = addingAdd.find('.address input').val();
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
            <div class="addressType"><input  style="width: 80%;" type="text" value="'+editAddressType+'"></div>\
            <div class="short"><input style="width: 80%;" type="text" value="'+editAbbreviation+'"></div>\
            <div class="address"><input style="width: 88%;" type="text" value="'+editAddress+'"></div>\
            <div class="postcode"><input  style="width: 80%;" type="text" value="'+editZipCode+'"></div>\
            <div class="operation"><a class="confirmEdit" href="javascript:void(0);">确定</a> <a class="cancelEdit redColor" href="javascript:void(0);">取消</a></div>\
            </div>');
        thisList.remove();
    });
    /*修改的时候取消*/
    $('.addressList ').on('click','.cancelEdit',function(){
        addressObj.getAddressList();
    });
    /*修改的时候确定*/
    $('.addressList ').on('click','.confirmEdit',function(){
        var EditObj = {};
        var Editing = $(this).parents('.editingAdd');
        EditObj.id = Editing.attr('data-ListId');
        EditObj.addressType = Editing.find('.addressType input').val();
        EditObj.abbreviation = Editing.find('.short input').val();
        EditObj.address = Editing.find('.address input').val();
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
        newConObj.id = addingCon.find('.no').find('span').text();
        newConObj.name = addingCon.find('.name input').val();
        newConObj.obligation = addingCon.find('.obligation input').val();
        newConObj.demp = addingCon.find('.demp input').val();
        newConObj.duty = addingCon.find('.duty input').val();
        newConObj.fixPhone = addingCon.find('.tel input').val();
        newConObj.phone = addingCon.find('.phone input').val();
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
        EditConObj.id = EditingCon.attr('data-listId');
        EditConObj.name = EditingCon.find('.name input').val();
        EditConObj.obligation = EditingCon.find('.obligation input').val();
        EditConObj.demp = EditingCon.find('.demp input').val();
        EditConObj.duty = EditingCon.find('.duty input').val();
        EditConObj.fixPhone = EditingCon.find('.tel input').val();
        EditConObj.phone = EditingCon.find('.phone input').val();
        EditConObj.email = EditingCon.find('.email input').val();
        EditConObj.address = EditingCon.find('.address2 input').val();
        mm.removeObjWithArr(contactsList,EditConObj.id);
        contactsList.push(EditConObj);
        contactsObj.getContactsList();
    });

    /*表单提交*/
    $('#newForm').submit(function(){
        $('#linkmans').val(JSON.stringify(contactsList));
        $('#address').val(JSON.stringify(addressList));
        //业务范畴循环
        var businessCheckbox = [];
        $('.businessCheckbox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    businessCheckbox.push($(n).val());
                }
            }
        });
        $('#scopeBusiness').val(businessCheckbox.join(','));
        //合作伙伴循环
        var PartnersCheckbox = [];
        $('.partnersCheckbox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    PartnersCheckbox.push($(n).val());
                }
            }
        });
        $('#partnerCategory').val(PartnersCheckbox.join(','));
        //客户分类
        var customerClass = [];
        $('#customerClass').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    customerClass.push($(n).val());
                }
            }
        });
        $('#wbkhCustomerClass').val(customerClass.join(','));
        //服务类别
        var gxcyrClassOfServiceArr = [];
        $('.classOfServiceBox').find("input:checkbox").each(function(i,n) {
            if ($(n).prop('checked') === true) {
                if (typeof $(n).val() != "undefined") {
                    gxcyrClassOfServiceArr.push($(n).val());
                }
            }
        });
        $('#gxcyrClassOfService').val(gxcyrClassOfServiceArr.join(','));

        $(this).ajaxSubmit(options);
        return false;//阻止表单提交
    })
});
var addressList = [
    {
        id:1,
        addressType:'注册地址',
        abbreviation:'来广营',
        address:'北京市朝阳区来广营地铁站望京城诚盈中心A座',
        zipCode:'10000'
    }
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
                <div class="postcode"><span>'+value.zipCode+'</span></div>\
                <div class="operation"><a class="editAdd" href="javascript:void(0);">修改地址</a> <a class="delAdd redColor" href="javascript:void(0);">删除地址</a></div>\
                </div>';
            $('.addressList').append(str);
        });
    }
};

/*联系人*/
var contactsList = [
    {
        id:1,
        name:'张三',
        obligation:'市场推广',
        demp:'研发中心前端开发',
        duty:'负责前端项目的研发',
        fixPhone:'0527-8888888888',
        phone:'18810513105',
        email:'zhangshasmo@pj0l.com',
        address:'北京市朝阳区来广营西路望京诚盈中心A座9层'
    }
];
var contactsObj = {
    getContactsList:function(){
        $('.contactList').empty();
        $.each(contactsList,function(index,value){
            var str= '<div data-listId="'+value.id+'" class="list clearfix">\
                <div class="no"><span>'+(index+1)+'</span></div>\
                <div class="name"><span>'+value.name+'</span></div>\
                <div class="obligation"><span>'+value.obligation+'</span></div>\
                <div class="demp"><span>'+value.demp+'</span></div>\
                <div class="duty"><span>'+value.duty+'</span></div>\
                <div class="tel"><span>'+value.fixPhone+'</span></div>\
                <div class="phone"><span>'+value.phone+'</span></div>\
                <div class="email"><span>'+value.email+'</span></div>\
                <div class="address2"><span>'+value.address+'</span></div>\
                <div class="operation"><a class="editAdd" href="javascript:void(0);">修改</a> <a class="delAdd redColor" href="javascript:void(0);">删除</a></div>\
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
    success:function(data) {
        if(data.code == '200'){
            alert('保存成功！');
            location.hash = vipspa.stringify('partnerManage2')
        }
    },error:function() {

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