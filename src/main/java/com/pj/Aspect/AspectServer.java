package com.pj.Aspect;


import com.pj.user.Utils.RequestDate;
import com.pj.user.pojo.Operation;
import com.pj.user.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;


/**
 * Created by SenevBoy on 2017/11/9.
 */

@Aspect
@Configuration
@Component
public class AspectServer {

   private static String clazzName = AspectServer.class.getName();

    @Autowired(required=false)
    private LogService logService;
    private static AspectServer aspectServer;

    public void setUserInfo(LogService logService) {
        this.logService = logService;
    }
    @PostConstruct
    public void init() {
        aspectServer = this;
        aspectServer.logService = this.logService;

    }

    //  private static final Logger log = LoggerFactory.getLogger(AspectServer.class);

    // 关注 信用等级的更新操作 记录日志
   @Pointcut("execution(* com.pj.user.service.*.updateLevelById(..))")
    public void updateLevelByIdexecution(){}



   // 关注 层级位数修改操作 记录日志
   @Pointcut("execution(* com.pj.user.service.*.updateHierarchyList(..))")
    public void updateHierarchyListexecution(){}

        // 关注 刪除联系人 记录日志
        @Pointcut("execution(* com.pj.partner.service.*.deletePartnerLinkmanByDetailsId(..))")
        public void deletePartnerLinkmanByDetailsId(){}

   // 关注 新增联系人 记录日志
        @Pointcut("execution(* com.pj.partner.*.*.PartnerLinkmanServiceImpl.insert(..))")
        public void PartnerLinkmanServiceImplInsertList(){}


        // 关注 刪除联系地址 记录日志
        @Pointcut("execution(* com.pj.partner.*.*.deletePartnerAddressByDetails(..))")
        public void deletePartnerAddressByDetails(){}

         // 关注 新增联系地址 记录日志
        @Pointcut("execution(* com.pj.partner.*.*.PartnerAddressServiceImpl.insert(..))")
         public void PartnerAddressServiceImplInsert(){}

        // 关注 更新合作伙伴 记录日志
        @Pointcut("execution(* com.pj.partner.*.*.PartnerDetailsServiceImpl.updateByPrimaryKey(..))")
        public void PartnerDetailsServiceImplUpdateByPrimaryKey(){}


        // 关注 新增合作伙伴 记录日志
        @Pointcut("execution(* com.pj.partner.*.*.PartnerDetailsServiceImpl.insertSelective(..))")
        public void PartnerDetailsServiceImplInsertSelective(){}


    // 关注 新增权限 记录日
         @Pointcut("execution(* com.pj.auth.mapper.AuthPostMenuMapper.insert(..))")
         public void editPostAuthorityInsert() {}


    // 关注 刪除权限 记录日志
        @Pointcut("execution(* com.pj.auth.mapper.AuthPostMenuMapper.delete(..))")
        public void editPostAuthorityDelete(){}

    //  新增权限 记录日志
    @AfterReturning("editPostAuthorityInsert( )")
    public void editPostAuthorityInsertAfter( JoinPoint point ) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        Object[] args = point.getArgs();
        for (Object arg: args){
            System.out.println("org"+arg.toString());
            Field[] declaredFields = arg.getClass().getDeclaredFields();
            for (int i = 0 ; i<declaredFields.length;i++){
                PropertyDescriptor pd = new PropertyDescriptor(declaredFields[i].getName(), arg.getClass());
                Method getMethod = pd.getReadMethod();//获得get方法  
                Object o = getMethod.invoke(arg);//执行get方法返回一个Object
                System.out.println(o);
            }
        }
        String actionData = "";
    }

    // 新增  刪除权限 记录日志
    @AfterReturning("editPostAuthorityDelete()")
    public void editPostAuthorityDeleteAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        Object[] args = point.getArgs();
        for (Object arg: args){
            System.out.println(arg);
        }
        String actionData = "";
    }
        // 新增  合伙人信息
  @AfterReturning("PartnerDetailsServiceImplInsertSelective()")
    public void PartnerDetailsServiceImplInsertSelectiveAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";

        HttpServletRequest request  = requestinit();

        Object newData =   getDateMethod(request, "new_partnerDetails");

        Field[] declaredFields=  getfieldsMethod(newData);

        boolean flage = false;
        // 循环 已删除的旧数据
        actionData+="合作伙伴管理：";

            for (int i = 0 ; i <declaredFields.length;i++ ) {

                if(checkSeralize(declaredFields[i].getName())){
                    PropertyDescriptor pd = new PropertyDescriptor(declaredFields[i].getName(), newData.getClass());
                    Method getMethod = pd.getReadMethod();//获得get方法  
                    Object o = getMethod.invoke(newData);//执行get方法返回一个Object
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_PartnerDeta_paramName.length; j++) {
                        if (declaredFields[i].getName().toString().equals(BasicProperties.Basic_PartnerDeta_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_PartnerDeta_paramVal[j];
                            break;
                        }

                    }
                    actionData += "< 新增数据 >（ " + o + " ） ; ";
                    flage = true;
                }
            }
        // 操作日志追加
        addLogMethod(flage,request , actionData);
      removeAttribute("new_partnerDetails",request);
      }



    // 更新 合伙人信息
    @AfterReturning("PartnerDetailsServiceImplUpdateByPrimaryKey()")
    public void PartnerDetailsServiceImplUpdateByPrimaryKeyAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        // 数组设定
        String actionData = "";

        HttpServletRequest request  = requestinit();

        Object oldData =   getDateMethod(request, "old_partnerDetails");
        Object newData =   getDateMethod(request, "new_partnerDetails");
        Field[] oldfields=  getfieldsMethod(oldData);
        Field[] newfields=  getfieldsMethod(newData);

        actionData+="合作伙伴管理：";
        boolean  flage = false;
        for (int i = 0 ; i <oldfields.length;i++ ) {
            if(checkSeralize(oldfields[i].getName())){
                PropertyDescriptor pd = new PropertyDescriptor(oldfields[i].getName(), oldData.getClass());
                Method getMethod = pd.getReadMethod();//获得get方法  
                Object o = getMethod.invoke(oldData);//执行get方法返回一个Object
                PropertyDescriptor pd2 = new PropertyDescriptor(newfields[i].getName(), oldData.getClass());
                Method getMethod2 = pd2.getReadMethod();//获得get方法  
                Object o2 = getMethod.invoke(newData);//执行get方法返回一个Object
                if (!(o==null?"":o).toString().equals((o2==null?"":o2).toString())) {
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_PartnerDeta_paramName.length; j++) {
                        if (oldfields[i].getName().toString().equals(BasicProperties.Basic_PartnerDeta_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_PartnerDeta_paramVal[j];
                        }
                    }
                    actionData += "< " + o + " >（ " + 02 + " ） ; ";
                    flage = true;
                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData);
        removeAttribute("old_partnerDetails",request);
        removeAttribute("new_partnerDetails",request);
    }

    // 新增  新增联系地址  日志统计
    @AfterReturning("PartnerAddressServiceImplInsert()")
    public void PartnerAddressServiceImplInsertAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        ServletRequestAttributes attributes = RequestDate.requestInit();
        HttpServletRequest request = attributes.getRequest();
        List<Object> newData = (List<Object>) request.getSession().getAttribute("new_partnerAddress");
        boolean flage = false;

        actionData+="合作伙伴管理-联系人：";
        for(Object  old  : newData){
            for (int i = 0 ; i <old.getClass().getDeclaredFields().length;i++ ) {
                if (checkSeralize(old.getClass().getDeclaredFields()[i].getName())) {
                    PropertyDescriptor pd = new PropertyDescriptor(old.getClass().getDeclaredFields()[i].getName(), old.getClass());
                    Method getMethod = pd.getReadMethod();//获得get方法  
                    Object o = getMethod.invoke(old);//执行get方法返回一个Object
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_address_paramName.length; j++) {
                        if (old.getClass().getDeclaredFields()[i].getName().toString().equals(BasicProperties.Basic_address_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_address_paramVal[j];
                            break;
                        }
                    }
                    actionData += "< 新增数据 >（ " + o + " ） ; ";
                    flage = true;
                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData);
        removeAttribute("new_partnerAddress",request);

    }


    // 删除  刪除联系地址  日志统计
    @AfterReturning("deletePartnerAddressByDetails()")
    public void deletePartnerAddressByDetailsAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        ServletRequestAttributes attributes = RequestDate.requestInit();
        HttpServletRequest request = attributes.getRequest();

        List<Object> oldData = (List<Object>) request.getSession().getAttribute("old_partnerAddress");

        boolean flage = false;
        // 循环 已删除的旧数据
        actionData+="合作伙伴管理-联系人：";
        for(Object  old  : oldData){
            for (int i = 0 ; i <old.getClass().getDeclaredFields().length;i++ ) {
                if (checkSeralize(old.getClass().getDeclaredFields()[i].getName())) {
                    PropertyDescriptor pd = new PropertyDescriptor(old.getClass().getDeclaredFields()[i].getName(), old.getClass());
                    Method getMethod = pd.getReadMethod();//获得get方法  
                    Object o = getMethod.invoke(old);//执行get方法返回一个Object
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_address_paramName.length; j++) {

                        if (old.getClass().getDeclaredFields()[i].getName().toString().equals(BasicProperties.Basic_address_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_address_paramVal[j];
                            break;
                        }
                    }
                    actionData += "< " + o + " >（ 信息已删除 ） ; ";
                    flage = true;
                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData);
        removeAttribute("old_partnerAddress",request);
    }

    // 新增  新增联系人  日志统计
    @AfterReturning("PartnerLinkmanServiceImplInsertList()")
    public void PartnerLinkmanServiceImplInsertListAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        ServletRequestAttributes attributes = RequestDate.requestInit();
        HttpServletRequest request = attributes.getRequest();

        List<Object> newData = (List<Object>) request.getSession().getAttribute("new_partnerLinkman");
        boolean flage = false;
        // 循环 已删除的旧数据
        actionData+="合作伙伴管理-联系地址：";
        for(Object  newdata  : newData){
            for (int i = 0 ; i <newdata.getClass().getDeclaredFields().length;i++ ) {
                if (checkSeralize(newdata.getClass().getDeclaredFields()[i].getName())) {
                    PropertyDescriptor pd = new PropertyDescriptor(newdata.getClass().getDeclaredFields()[i].getName(), newdata.getClass());
                    Method getMethod = pd.getReadMethod();//获得get方法  
                    Object o = getMethod.invoke(newdata);//执行get方法返回一个Object
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_linkmanCN_paramName.length; j++) {

                        if (newdata.getClass().getDeclaredFields()[i].getName().toString().equals(BasicProperties.Basic_linkmanCN_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_linkmanCN_paramVal[j];
                            break;
                        }
                    }
                    actionData += "< 新增数据 >（ " + o + " ） ; ";
                    flage = true;
                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData);
        removeAttribute("new_partnerLinkman",request);
    }


    // 删除  刪除联系人  日志统计
    @AfterReturning("deletePartnerLinkmanByDetailsId()")
    public void deletePartnerLinkmanByDetailsIdAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
            String actionData = "";
            ServletRequestAttributes attributes = RequestDate.requestInit();
            HttpServletRequest request = attributes.getRequest();

            List<Object> oldData = (List<Object>) request.getSession().getAttribute("old_partnerLinkman");

            boolean flage = false;
            // 循环 已删除的旧数据
            actionData+="合作伙伴管理-联系地址：";
            for(Object  old  : oldData){
                for (int i = 0 ; i <old.getClass().getDeclaredFields().length;i++ ) {
                    if (checkSeralize(old.getClass().getDeclaredFields()[i].getName())) {
                        PropertyDescriptor pd = new PropertyDescriptor(old.getClass().getDeclaredFields()[i].getName(), old.getClass());
                        Method getMethod = pd.getReadMethod();//获得get方法  
                        Object o = getMethod.invoke(old);//执行get方法返回一个Object
                        // 判断字段名称
                        for (int j = 0; j < BasicProperties.Basic_linkmanCN_paramName.length; j++) {

                            if (old.getClass().getDeclaredFields()[i].getName().toString().equals(BasicProperties.Basic_linkmanCN_paramName[j].toString())) {
                                actionData += " " + BasicProperties.Basic_linkmanCN_paramVal[j];
                                break;
                            }
                        }
                        actionData += "< " + o + " >（ 信息已删除 ） ; ";
                        flage = true;
                    }
                }
            }
        // 操作日志追加
        addLogMethod(flage,request , actionData);
        removeAttribute("old_partnerLinkman",request);
    }


    @AfterReturning("updateHierarchyListexecution()")
    public void updateHierarchyListexecutionAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";

        HttpServletRequest request  = requestinit();

        List<Object> oldData = (List<Object>) getDateMethod(request, "oldHierarchyData");
        List<Object> newData = (List<Object>) getDateMethod(request, "newHierarchyData");

        boolean flage = false;
        actionData+="层级位数管理：";
        for(int k = 0 ; k < oldData.size();k++){
            for (int i = 0; i < oldData.get(i).getClass().getDeclaredFields().length; i++ ) {
                if (checkSeralize(oldData.get(i).getClass().getDeclaredFields()[i].getName())) {
                    PropertyDescriptor pd = new PropertyDescriptor(oldData.get(i).getClass().getDeclaredFields()[i].getName(), oldData.get(i).getClass());
                    Method getMethod = pd.getReadMethod();//获得get方法  
                    Object o = getMethod.invoke(oldData.get(i));//执行get方法返回一个Object
                    PropertyDescriptor pd2 = new PropertyDescriptor(newData.get(i).getClass().getDeclaredFields()[i].getName(), newData.get(i).getClass());
                    Method getMethod2 = pd2.getReadMethod();//获得get方法  
                    Object o2 = getMethod2.invoke(newData.get(i));//执行get方法返回一个Object

                    if (!(o == null ? "" : o).toString().equals(o2 == null ? "" : o2.toString())) {
                        actionData += "   第"+k+1+"层 ";
                    }
                    actionData += "< " + o + " >（" + o2 + " ） ; ";
                    flage = true;
                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData);
        removeAttribute("oldHierarchyData",request);
        removeAttribute("newHierarchyData",request);
    }


    @AfterReturning("updateLevelByIdexecution()")
    public void updateLevelByIdAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        // 数组设定
        String actionData = "";
            HttpServletRequest request  = requestinit();

            Object oldData =   getDateMethod(request, "oldUserLevelData");
            Object newData =   getDateMethod(request, "newUserLevelData");
            Field[] oldfields=  getfieldsMethod(oldData);
            Field[] newfields=  getfieldsMethod(newData);

        actionData+="信用等级管理：";
        boolean  flage = false;
        for (int i = 0 ; i <oldfields.length;i++ ){
            if (checkSeralize(oldfields[i].getName())) {
                PropertyDescriptor pd = new PropertyDescriptor(oldfields[i].getName(), oldData.getClass());
                Method getMethod = pd.getReadMethod();//获得get方法  
                Object o = getMethod.invoke(oldData);//执行get方法返回一个Object
                PropertyDescriptor pd2 = new PropertyDescriptor(newfields[i].getName(), oldData.getClass());
                Method getMethod2 = pd2.getReadMethod();//获得get方法  
                Object o2 = getMethod.invoke(newData);//执行get方法返回一个Object
                if (!((o==null?"":o).toString()).equals(o2==null?"":o2.toString())) {
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_UserLevel_paramName.length; j++) {
                        if (oldfields[i].getName().toString().equals(BasicProperties.Basic_UserLevel_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_UserLevel_paramVal[j];
                        }
                    }
                    actionData += "< " + o + " >（ " + o2 + " ） ; ";
                    flage = true;
                }
            }
        }
            // 操作日志追加
            addLogMethod(flage,request , actionData);
        removeAttribute("oldUserLevelData",request);
        removeAttribute("newUserLevelData",request);
    }




    /**
     * 获取被代理对象的真实类全名
     * @param point 连接点对象
     * @return 类全名
     */
    private String getRealClassName(JoinPoint point) {
        return point.getTarget().getClass().getName();
    }

    /**
     * 获取代理执行的方法名
     * @param point 连接点对象
     * @return 调用方法名
     */
    private String getMethodName(JoinPoint point) {
        return point.getSignature().getName();
    }

    public String startAdd(Object o,Object o2,Field[] oldfields,String actionData,String[] paramName,String[] paramVal,int i,boolean flage){

        return actionData+"--"+flage;
    }


    private void addLogMethod(boolean flage, HttpServletRequest request,String actionData) {
        if(flage){
            // 获取  登录人信息
         /*   User user_object = (User) request.getSession().getAttribute("user");*/
            Operation operation =  new Operation();
            operation.setAction(actionData);
         /*   operation.setUserId(user_object.getEmail());
            operation.setUserName(user_object.getUsername());
            operation.setDepartment(user_object.getDempname());
            operation.setCompany(user_object.getCompanyname());
            operation.setJobs(user_object.getDempname());*/
            // 追加日志记录
            aspectServer.logService.addOperationlLog(operation);
        }
    }
    /*获取请求*/
    private HttpServletRequest requestinit() {
        ServletRequestAttributes attributes = RequestDate.requestInit();
        HttpServletRequest request = attributes.getRequest();
        return request;
    }
/*获取对象*/
    private Object getDateMethod(HttpServletRequest request, String key) {
       return  request.getSession().getAttribute(key);
    }
/*获取属性*/
private Field[] getfieldsMethod(Object objectdata) {
    return objectdata.getClass().getDeclaredFields();
}

    private boolean checkSeralize(String  name){
        if (name.equals("getSerialVersionUID") || name.equals("serialVersionUID")) {
            return false;
        }else{
            return true;
        }
    }

    /*删除作用域*/
    private void removeAttribute(String key,HttpServletRequest request){
        request.getSession().removeAttribute(key);
    }
}
