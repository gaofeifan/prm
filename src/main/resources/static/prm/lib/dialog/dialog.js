
/**
 * Created by Administrator on 2017/4/26.
 */

function Widget(){
    this.boundingBox = null;//属性：最外层容器
}
Widget.prototype = {
    on:function(type,handler){
        if(typeof this.handlers[type] == 'undefined'){
            this.handlers[type] = [];
        }
        this.handlers[type].push(handler);
        return this;
    },
    fire:function(type,data){
        if(this.handlers[type] instanceof Array){
            var handlers = this.handlers[type];
            for(var i = 0,len = handlers.length;i<len;i++){
                handlers[i](data);
            }
        }
    },
    render:function(container){//方法：渲染组件
        this.renderUI();
        this.handlers = {};
        this.bindUI();
        this.syncUI();
        $(container || document.body).append(this.boundingBox);
    },
    destroy:function(){     //方法：销毁组件
        this.destructor();
        this.boundingBox.off();
        this.boundingBox.remove();
    },
    renderUI:function(){},//接口：添加dom节点
    bindUI:function(){},//接口：监听事件
    syncUI:function(){},//接口：初始化组件属性
    destructor:function(){}//接口：销毁前的处理函数
};
function Window(){
    this.config = {
        width:435,
        height:280,
        title:'系统消息',
        content:'',
        hasCloseBtn:false,
        hasMask:true,
        isDraggable:true,
        dragHandle:null,
        skinClassName:null,
        text4AlertBtn:'确定',
        text4ConfirmBtn:"确定",
        text4CancelBtn:"取消",
        handler4AlertBtn:null,
        handler4CloseBtn:null,
        handler4ConfirmBtn:null,
        handler4CancelBtn:null
    };
}
Window.prototype = $.extend({},new Widget(),{
    renderUI:function(){
        var footerContent = "";
        switch (this.config.winType){
            case "alert":
                footerContent = '<input type="button" value="'+ this.config.text4ConfirmBtn +'" class="window_alertBtn">';
                break;
            case "confirm":
                footerContent = '<input type="button" value="'+this.config.text4ConfirmBtn+'" class="window_confirmBtn">' +
                    '<input type="button" value="'+ this.config.text4CancelBtn +'" class="window_cancelBtn">';
                break;
        }
        this.boundingBox = $('<div class="window_boundingBox">' +
            '<div class="window_header">'+ this.config.title+ '</div>'+
            '<div class="window_body">'+ this.config.content + '</div>'+
            '<div class="window_footer">'+ footerContent +'</div>'+
            '</div>');
        if(this.config.hasMask){
            this._mask = $('<div class="window_mask"></div>');
            this._mask.appendTo("body");
        }
        if(this.config.hasCloseBtn){
            this.boundingBox.append('<span class="window_closeBtn">X</span>');
        }
        this.boundingBox.appendTo(document.body);
    },
    bindUI:function(){
        var that = this;
        this.boundingBox.delegate(".window_alertBtn","click",function () {
            that.fire("alert");
            that.destroy();
        }).delegate(".window_closeBtn","click",function(){
            that.fire("close");
            that.destroy();
        }).delegate(".window_confirmBtn","click",function(){
            that.fire('confirm');
           /* that.destroy();*/
        }).delegate('.window_cancelBtn','click',function(){
            that.fire('cancel');
            that.destroy();
        });
        if(this.config.handler4AlertBtn){
            this.on("alert",this.config.handler4AlertBtn);
        }
        if(this.config.handler4CloseBtn){
            this.on('close',this.config.handler4CloseBtn);
        }
        if(this.config.handler4ConfirmBtn){
            this.on("confirm",this.config.handler4AlertBtn);
        }
        if(this.config.handler4CancelBtn){
            this.on('cancel',this.config.handler4CancelBtn);
        }
    },
    syncUI:function(){
        this.boundingBox.css({
            width:this.config.width+'px',
            height:this.config.height+'px',
            left:(this.config.x || (window.innerWidth - this.config.width) / 2)+'px',
            top:(this.config.y || (window.innerHeight - this.config.height) / 2)+'px'
        });

        if(this.config.skinClassName){
            this.boundingBox.addClass(this.config.skinClassName);
        }
        // if(this.config.isDraggable){
        //     if(this.config.dragHandle){
        //         this.boundingBox.draggable({
        //             handle:this.config.dragHandle
        //         })
        //     }else{
        //         this.boundingBox.draggable();
        //     }
        // }
    },
    destructor:function(){
        this._mask && this._mask.remove();
    },
    alert:function(cfg){
        $.extend(this.config,cfg);
        this.render();
        return this;
    },
    confirm:function(cfg){
        $.extend(this.config,cfg,{
            winType:'confirm'
        });
        this.render();
        return this;
    },
    prompt:function(){

    }
});


