/**
 * Created by Administrator on 2017/9/12.
 */
var oaPathUrl = '10.0.0.18:8083';
var ssoPathUrl = '10.0.0.18:8085';
// var gPathUrl = '192.168.4.213:8083';
var gPathUrl = '192.168.4.168:8083';
// var gPathUrl = '10.0.0.105:8083';//测试环境


/*var oaPathUrl = '139.129.236.180:8080';
var ssoPathUrl = '139.129.236.180:8081';
var gPathUrl = '192.168.4.213:8083';*/
vipspa.start({
    view: '#ui-view',
    router: {
        'business': { //业务范畴
            templateUrl: 'view/business.html',
            controller: 'script/module/business.js'
        },
        'serve': { //服务类别
            templateUrl: 'view/serve.html',
            controller: 'script/module/serve.js'
        },
        'credit': { //信用等级
            templateUrl: 'view/credit.html',
            controller: 'script/module/credit.js'
        },
        'hierarchy': { //层级位数
            templateUrl: 'view/hierarchy.html',
            controller: 'script/module/hierarchy.js'
        },
        'partnerClassify': { //合作伙伴分类
            templateUrl: 'view/partnerClassify.html',
            controller: 'script/module/partnerClassify.js'
        },
        'partnerManage': { //合作伙伴管理
            templateUrl: 'view/partnerManage2.html',
            controller: 'script/module/partnerManage2.js'
        },
       /* 'partnerManage2': { //合作伙伴管理 有操作权限
            templateUrl: 'view/partnerManage2.html',
            controller: 'script/module/partnerManage2.js'
        },*/
        'partnerNew': { //合作伙伴 新增
            templateUrl: 'view/partnerNew.html',
            controller: 'script/module/partnerNew.js'
        },
        'partnerEdit': { //合作伙伴-修改
            templateUrl: 'view/partnerEdit.html',
            controller: 'script/module/partnerEdit.js'
        },
        'homePage': { //首页
            templateUrl: 'view/homePage.html',
            controller: 'script/module/homePage.js'
        },
        'user': { //用户管理
            templateUrl: 'view/user.html',
            controller: 'script/module/user.js'
        },
        'power': { //权限管理
            templateUrl: 'view/power.html',
            controller: 'script/module/power.js'
        },
        'log': { //系统日志
            templateUrl: 'view/log.html',
            controller: 'script/module/log.js'
        },
        'defaults': '' //默认路由
    },
    errorTemplateId: '#error'  //可选的错误模板，用来处理加载html模块异常时展示错误内容
});