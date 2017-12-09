package com.pj.Aspect;


import com.pj.auth.pojo.AuthMenu;
import com.pj.auth.pojo.User;
import com.pj.auth.service.AuthMenuService;
import com.pj.auth.service.AuthUserService;
import com.pj.cache.PartnerDetailsCache;
import com.pj.partner.mapper.PartnerDetailsShifFileMapper;
import com.pj.partner.pojo.PartnerDetails;
import com.pj.partner.pojo.PartnerDetailsShifFile;
import com.pj.partner.service.PartnerDetailsService;
import com.pj.partner.service.impl.PartnerDetailsServiceImpl;
import com.pj.user.Utils.RequestDate;
import com.pj.user.pojo.Operation;
import com.pj.user.pojo.Permissions;
import com.pj.user.pojo.RequestParams;
import com.pj.user.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

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
public class AspectServer {

    private static String clazzName = AspectServer.class.getName();

    @Autowired(required = false)
    private LogService logService;
    private static AspectServer aspectServer;

    @Autowired(required = false)
    private AuthMenuService authMenuService;

    @Autowired(required = false)
    private AuthUserService authUserService;


    @Autowired(required = false)
    private PartnerDetailsService partnerDetailsService;



    @Autowired(required = false)
    private PartnerDetailsServiceImpl partnerDetailsServiceImpl;

    public PartnerDetailsServiceImpl getPartnerDetailsServiceImpl() {
        return partnerDetailsServiceImpl;
    }

    public void setPartnerDetailsServiceImpl(PartnerDetailsServiceImpl partnerDetailsServiceImpl) {
        this.partnerDetailsServiceImpl = partnerDetailsServiceImpl;
    }
    @Autowired(required = false)
    private PartnerDetailsShifFileMapper partnerDetailsShifFileMapper;



    public void setUserInfo(LogService logService) {
        this.logService = logService;
    }

    @PostConstruct
    public void init() {
        aspectServer = this;
        aspectServer.logService = this.logService;
        aspectServer.partnerDetailsServiceImpl = this.partnerDetailsServiceImpl;

    }

    //  private static final Logger log = LoggerFactory.getLogger(AspectServer.class);

    // 关注 信用等级的更新操作 记录日志
    @Pointcut("execution(* com.pj.user.service.*.updateLevelById(..))")
    public void updateLevelByIdexecution() {
    }


    // 关注 层级位数修改操作 记录日志
    @Pointcut("execution(* com.pj.user.service.*.updateHierarchyList(..))")
    public void updateHierarchyListexecution() {
    }

    // 关注 刪除联系人 记录日志
    @Pointcut("execution(* com.pj.partner.service.*.deletePartnerLinkmanByDetailsId(..))")
    public void deletePartnerLinkmanByDetailsId() {
    }

    // 关注 新增联系人 记录日志
    @Pointcut("execution(* com.pj.partner.*.*.PartnerLinkmanServiceImpl.insert(..))")
    public void PartnerLinkmanServiceImplInsertList() {
    }


    // 关注 刪除联系地址 记录日志
    @Pointcut("execution(* com.pj.partner.*.*.deletePartnerAddressByDetails(..))")
    public void deletePartnerAddressByDetails() {
    }

    // 关注 新增联系地址 记录日志
    @Pointcut("execution(* com.pj.partner.*.*.PartnerAddressServiceImpl.insert(..))")
    public void PartnerAddressServiceImplInsert() {
    }

    // 关注 更新合作伙伴 记录日志
    @Pointcut("execution(* com.pj.partner.*.*.PartnerDetailsServiceImpl.updateByPrimaryKey(..))")
    public void PartnerDetailsServiceImplUpdateByPrimaryKey() {
    }


    // 关注 新增合作伙伴 记录日志
    @Pointcut("execution(* com.pj.partner.*.*.PartnerDetailsServiceImpl.insertSelective(..))")
    public void PartnerDetailsServiceImplInsertSelective() {
    }

    // 关注 删除合作伙伴 记录日志
    @Pointcut("execution(* com.pj.partner.service.impl.PartnerDetailsServiceImpl.deletePartnerDetailsById(..))")
    public void PartnerDetailsServiceImplDeletePartnerDetailsById() {
    }


    // 关注 权限 记录日
    @Pointcut("execution(* com.pj.auth.service.impl.AuthPostMenuServiceImpl.editPostAuthority(..))")
    public void editPostAuthorityExecution() {
    }

    // 关注 合作伙伴文件转移 记录日
    @Pointcut("execution(* com.pj.partner.service.impl.PartnerDetailsServiceImpl.shiftPartnerDetailsFileByIds(..))")
    public void shiftPartnerDetailsFileByIdsexecution() {
    }



    // 关注 合作伙伴文件转移   before
    @Before("shiftPartnerDetailsFileByIdsexecution( )")
    public void shiftPartnerDetailsFileByIdsexecutionBefore(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        HttpServletRequest request = requestinit();
        Object[] args = point.getArgs();

        //  查询转移的文件
        Object o = PartnerDetailsCache.getValueByKey("details");
        List<PartnerDetailsShifFile> shifFileList = (List<PartnerDetailsShifFile>) o;
        List<PartnerDetailsShifFile> deleteFileList = new ArrayList<>();
        //  获取转移文件所有的子集
        for (PartnerDetailsShifFile fds:shifFileList) {
            List<PartnerDetailsShifFile> childList =partnerDetailsShifFileMapper.getChildList(fds.getId());
            //   如果子集中存在转移直的目录将其删除
            for (PartnerDetailsShifFile childFds:childList) {
                if(childFds.getId() == Integer.parseInt(args[0].toString()) ){
                    deleteFileList.add(childFds);
                }
            }
        }
        shifFileList.removeAll(deleteFileList);
        request.getSession().setAttribute("oldShifFileList",shifFileList);
    }

    // 关注 合作伙伴文件转移   after
    @AfterReturning("shiftPartnerDetailsFileByIdsexecution( )")
    public void shiftPartnerDetailsFileByIdsexecutionAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        HttpServletRequest request = requestinit();
        Object[] args = point.getArgs();

        String actionData = " 目录转移  ：";
        // 获取元数据集合
        List<PartnerDetailsShifFile> oldShifFileList = (List<PartnerDetailsShifFile>) request.getSession().getAttribute("oldShifFileList");
        boolean flage  = false;
        // 遍历添加 日志
        if(null!=oldShifFileList  || oldShifFileList.size()!=0){
            for (PartnerDetailsShifFile oldfile :oldShifFileList) {
                Field[] declaredFields = getfieldsMethod(oldfile);
                // 获取 id序列号
                for (Field fiel:declaredFields) {
                    if(fiel.getName().equals("id")){
                        PropertyDescriptor pd = new PropertyDescriptor(fiel.getName(), oldfile.getClass());
                        Method getMethod = pd.getReadMethod();//获得get方法  
                        Object o = getMethod.invoke(oldfile);//执行get方法返回一个Object
                     // 日志内容拼接
                        actionData+=""+"ID( "+o+" )-目录等级";
                    }
                    if(fiel.getName().equals("pId")){
                        PropertyDescriptor pd = new PropertyDescriptor(fiel.getName(), oldfile.getClass());
                        Method getMethod = pd.getReadMethod();//获得get方法  
                        Object o = getMethod.invoke(oldfile);//执行get方法返回一个Object
                        // 日志内容拼接
                        actionData+=""+"< "+o+" >（ " + args[0] + " ） ; ";
                        flage = true;
                    }
                }
            }

        }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
        removeAttribute("oldShifFileList",request);

    }
        //  获取已删除的权限 记录日志
    @Before("editPostAuthorityExecution( )")
    public void editPostAuthorityPointcutBefore(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        HttpServletRequest request = requestinit();
        Object[] args = point.getArgs();
        String actionData = "权限管理  ：";
        boolean flage = false;
        // 获取 未删除的 权限  根基postID 去查询
        List<AuthMenu> authMenuList = authMenuService.findAuthMenuListBypostId(Integer.parseInt(args[0].toString()));
        request.getSession().setAttribute("oldAuthority",authMenuList);
        /*获取  权限操作涉及人*/
        // String emailsByPostId = authUserService.getEmailsByPostId(args[0].toString());

      /*  if (null != authMenuList || authMenuList.size() != 0) {

            // 循环获取 菜单
            for (AuthMenu menu : authMenuList) {
                if (menu.getIsMenu() == 1) {

                    // 循环  获取按钮
                    for (AuthMenu button : authMenuList) {
                        if (button.getIsMenu() == 0) {
                            if ((button.getPId() == null ? 0 : button.getPId()) == menu.getId()) {
                                actionData += menu.getName() + "-" + button.getName().split("-")[1] + " ; ";
                                flage = true;
                            }
                        }
                    }
                }
            }
            try {
                addAuthLogMethod(flage, request, actionData, "删除", emailsByPostId);
            } catch (Exception e) {
                System.out.println(e);
            }
        }*/
    }

    //  记录新增的权限···· 记录日志
    @AfterReturning("editPostAuthorityExecution()")
    public void editPostAuthorityPointcutAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        HttpServletRequest request = requestinit();
        Object[] args = point.getArgs();
        String actionData = "权限管理  ：";
        boolean flage = false;
          /*获取  权限操作涉及人*/
        String emailsByPostId = authUserService.getEmailsByPostId(args[0].toString());
        // 获取 新增的 权限  根基postID 去查询
        List<AuthMenu> authMenuList = authMenuService.findAuthMenuListBypostId(Integer.parseInt(args[0].toString()));
        // 获取 旧的 权限  根基postID 去查询
        List<AuthMenu>  oldAuthority = new ArrayList<AuthMenu>();
        try {
            oldAuthority = (List<AuthMenu>) request.getSession().getAttribute("oldAuthority");
        }catch (Exception e){

        }

        // 比对 新旧权限
        if(null != authMenuList && null != oldAuthority ){
            List<AuthMenu>  oldIsmenu = new LinkedList<AuthMenu>();
            List<AuthMenu>  newIsmenu = new LinkedList<AuthMenu>();
            /*查找旧权限中独有的权限*/
            for(AuthMenu olda : oldAuthority){
                 boolean  flage2 = true;

                for(AuthMenu news : authMenuList){
                    if(olda.getName().equals(news.getName())){
                        flage2 = false;
                        break;
                    }
                }
                /*判断并添加父级*/
                if(flage2){
                    oldIsmenu.add(olda);
                    if(olda.getIsMenu()!=1){
                        for(AuthMenu olda2 : oldAuthority){
                            if(olda.getPId().equals(olda2.getId())){
                                oldIsmenu.add(olda2);
                            }
                        }
                    }
                }

            }
            // 循环获取 菜单
            for (AuthMenu button : oldIsmenu) {
                if (button.getIsMenu() == 0) {
                        for(AuthMenu dat : oldIsmenu){
                            if(button.getPId().equals(dat.getId())){
                                actionData += dat.getName() + "-" + button.getName().split("-")[1] + " ; ";
                                flage = true;
                            }
                        }
                }
            }
            try {
                addAuthLogMethod(flage, request, actionData, "删除", emailsByPostId);
            } catch (Exception e) {
                System.out.println(e);
            }

            flage = false;

            /*查找新权限中独有的权限*/
            for(AuthMenu news : authMenuList){
                boolean  flage2 = true;

                for(AuthMenu olda : oldAuthority){

                    if(news.getName().equals(olda.getName())){
                        flage2 = false;
                        break;
                    }
                }
                if(flage2){
                    newIsmenu.add(news);
                    if(news.getIsMenu()!=1){
                        for(AuthMenu news2 : authMenuList){
                            if(news.getPId().equals(news2.getId())){
                                newIsmenu.add(news2);
                                break;
                            }
                        }
                    }
                }

            }
            actionData = "权限管理  ：";
            // 循环获取 菜单
            for (AuthMenu button : newIsmenu) {

                if (button.getIsMenu() == 0) {
                    for(AuthMenu dat : newIsmenu){
                        if(button.getPId().equals(dat.getId())){
                            actionData += dat.getName() + "-" + button.getName().split("-")[1] + " ; ";
                            flage = true;
                        }
                    }
                }
            }
            try {
                addAuthLogMethod(flage, request, actionData, "新增", emailsByPostId);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // 新增  合伙人信息
    @AfterReturning("PartnerDetailsServiceImplInsertSelective()")
    public void PartnerDetailsServiceImplInsertSelectiveAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        Object[] args = point.getArgs();
        HttpServletRequest request = requestinit();

        Object newData = getDateMethod(request, "new_partnerDetails");

        Field[] declaredFields = getfieldsMethod(newData);

        boolean flage = false;
        // 循环 已删除的旧数据
        actionData += "合作伙伴管理：";

        for (int i = 0; i < declaredFields.length; i++) {

            if (checkSeralize(declaredFields[i].getName())) {
                PropertyDescriptor pd = new PropertyDescriptor(declaredFields[i].getName(), newData.getClass());
                Method getMethod = pd.getReadMethod();//获得get方法  
                Object o = getMethod.invoke(newData);//执行get方法返回一个Object
                // 判断字段名称
                for (int j = 0; j < BasicProperties.Basic_PartnerDeta_paramName.length; j++) {
                    if (declaredFields[i].getName().toString().equals(BasicProperties.Basic_PartnerDeta_paramName[j].toString())) {
                        actionData += " " + BasicProperties.Basic_PartnerDeta_paramVal[j];
                        actionData += "< 新增数据 >（ " + o + " ） ; ";
                        flage = true;
                        break;
                    }

                }

            }
        }
        // 操作日志追加
        addLogMethod(flage, request, actionData,args[args.length-1].toString());
        removeAttribute("new_partnerDetails", request);
    }
    // 删除  合伙人信息
    @Before("PartnerDetailsServiceImplDeletePartnerDetailsById()")
    public void PartnerDetailsServiceImplDeletePartnerDetailsByIdBefore(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        HttpServletRequest request  = requestinit();
        Object[] args = point.getArgs();
        // 获取将要删除的合作伙伴人信息
        PartnerDetails partnerDetails = partnerDetailsService.selectByPrimaryKey(Integer.parseInt(args[0].toString()));
        request.getSession().setAttribute("deletepartnerDetails",partnerDetails);
    }
    // 删除  合伙人信息
  @AfterReturning("PartnerDetailsServiceImplDeletePartnerDetailsById()")
    public void PartnerDetailsServiceImplDeletePartnerDetailsByIdAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
      Object[] args = point.getArgs();
      HttpServletRequest request  = requestinit();
      Object oldData = request.getSession().getAttribute("deletepartnerDetails");
      Field[] declaredFields=  getfieldsMethod(oldData);
      boolean flage = false;
        // 循环 已删除的旧数据
        actionData+="合作伙伴管理：";

            for (int i = 0 ; i <declaredFields.length;i++ ) {
                if(checkSeralize(declaredFields[i].getName())){
                    PropertyDescriptor pd = new PropertyDescriptor(declaredFields[i].getName(), oldData.getClass());
                    Method getMethod = pd.getReadMethod();//获得get方法  
                    Object o = getMethod.invoke(oldData);//执行get方法返回一个Object
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_PartnerDeta_paramName.length; j++) {
                        if (declaredFields[i].getName().toString().equals(BasicProperties.Basic_PartnerDeta_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_PartnerDeta_paramVal[j];
                            actionData += "< "+o+">（删除数据  ） ; ";
                            flage = true;
                            break;
                        }

                    }

                }
            }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
      removeAttribute("deletepartnerDetails",request);
      }



    // 更新 合伙人信息
    @AfterReturning("PartnerDetailsServiceImplUpdateByPrimaryKey()")
    public void PartnerDetailsServiceImplUpdateByPrimaryKeyAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        // 数组设定
        String actionData = "";
        Object[] args = point.getArgs();
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
                            actionData += "< " + o + " >（ " + o2 + " ） ; ";
                            flage = true;
                            break;
                        }
                    }

                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
        removeAttribute("old_partnerDetails",request);
        removeAttribute("new_partnerDetails",request);
    }

    // 新增  新增联系地址  日志统计
    @AfterReturning("PartnerAddressServiceImplInsert()")
    public void PartnerAddressServiceImplInsertAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        Object[] args = point.getArgs();
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
                            actionData += "< 新增数据 >（ " + o + " ） ; ";
                            flage = true;
                            break;
                        }
                    }

                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
        removeAttribute("new_partnerAddress",request);

    }


    // 删除  刪除联系地址  日志统计
    @AfterReturning("deletePartnerAddressByDetails()")
    public void deletePartnerAddressByDetailsAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        Object[] args = point.getArgs();
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
                            actionData += "< " + o + " >（ 信息已删除 ） ; ";
                            flage = true;
                            break;
                        }
                    }

                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
        removeAttribute("old_partnerAddress",request);
    }

    // 新增  新增联系人  日志统计
    @AfterReturning("PartnerLinkmanServiceImplInsertList()")
    public void PartnerLinkmanServiceImplInsertListAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        ServletRequestAttributes attributes = RequestDate.requestInit();
        HttpServletRequest request = attributes.getRequest();
        Object[] args = point.getArgs();
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
                            actionData += "< 新增数据 >（ " + o + " ） ; ";
                            flage = true;
                            break;
                        }
                    }

                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
        removeAttribute("new_partnerLinkman",request);
    }


    // 删除  刪除联系人  日志统计
    @AfterReturning("deletePartnerLinkmanByDetailsId()")
    public void deletePartnerLinkmanByDetailsIdAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
            String actionData = "";
            ServletRequestAttributes attributes = RequestDate.requestInit();
            HttpServletRequest request = attributes.getRequest();
        Object[] args = point.getArgs();
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
                                actionData += "< " + o + " >（ 信息已删除 ） ; ";
                                flage = true;
                                break;
                            }
                        }

                    }
                }
            }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
        removeAttribute("old_partnerLinkman",request);
    }


    @AfterReturning("updateHierarchyListexecution()")
    public void updateHierarchyListexecutionAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        String actionData = "";
        Object[] args = point.getArgs();


        HttpServletRequest request  = requestinit();

        List<Object> oldData = (List<Object>) getDateMethod(request, "oldHierarchyData");
        List<Object> newData = (List<Object>) getDateMethod(request, "newHierarchyData");

        boolean flage = false;
        actionData+="层级位数管理：";
        for(int k = 0 ; k < oldData.size();k++){

            for (int i = 0; i < oldData.get(k).getClass().getDeclaredFields().length; i++ ) {
                if (checkSeralize(oldData.get(k).getClass().getDeclaredFields()[i].getName())) {
                    PropertyDescriptor pd = new PropertyDescriptor(oldData.get(k).getClass().getDeclaredFields()[i].getName(), oldData.get(k).getClass());
                    Method getMethod = pd.getReadMethod();//获得get方法  
                    Object o = getMethod.invoke(oldData.get(k));//执行get方法返回一个Object
                    PropertyDescriptor pd2 = new PropertyDescriptor(newData.get(k).getClass().getDeclaredFields()[i].getName(), newData.get(k).getClass());
                    Method getMethod2 = pd2.getReadMethod();//获得get方法  
                    Object o2 = getMethod2.invoke(newData.get(k));//执行get方法返回一个Object


                    if (!(o == null ? "" : o).toString().equals(o2 == null ? "" : o2.toString())) {
                            actionData += "   第"+(k+1)+"层 ";
                            actionData += "< " + o + " >（" + o2 + " ） ; ";
                            flage = true;
                    }

                }
            }
        }
        // 操作日志追加
        addLogMethod(flage,request , actionData,args[args.length-1].toString());
        removeAttribute("oldHierarchyData",request);
        removeAttribute("newHierarchyData",request);
    }


    @AfterReturning("updateLevelByIdexecution()")
    public void updateLevelByIdAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        // 数组设定
        String actionData = "";
            HttpServletRequest request  = requestinit();
        Object[] args = point.getArgs();
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
                if(oldfields[i].getName().equals("id")){
                    actionData+="序号 "+o+" ";
                }
                PropertyDescriptor pd2 = new PropertyDescriptor(newfields[i].getName(), oldData.getClass());
                Method getMethod2 = pd2.getReadMethod();//获得get方法  
                Object o2 = getMethod.invoke(newData);//执行get方法返回一个Object
                if (!((o==null?"":o).toString()).equals(o2==null?"":o2.toString())) {
                    // 判断字段名称
                    for (int j = 0; j < BasicProperties.Basic_UserLevel_paramName.length; j++) {
                        if (oldfields[i].getName().toString().equals(BasicProperties.Basic_UserLevel_paramName[j].toString())) {
                            actionData += " " + BasicProperties.Basic_UserLevel_paramVal[j];
                            actionData += "< " + o + " >（ " + o2 + " ） ; ";
                            flage = true;
                            break;
                        }
                    }

                }
            }
        }
            // 操作日志追加
            addLogMethod(flage,request , actionData,args[args.length-1].toString());
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

/*权限日志*/
    private void addAuthLogMethod(boolean flage, HttpServletRequest request, String actionData, String type, String involvuser) {
        if(flage){
            Permissions permission =  new Permissions();
            permission.setInvolvesPermissions(actionData);
            permission.setType(type);
            permission.setInvolvesUser(involvuser);
            permission.setCreateDate(new Date());
            // 追加日志记录
            aspectServer.logService.addPermissionslLog(permission);
        }
    }
/*用户操作日志*/
    private void addLogMethod(boolean flage, HttpServletRequest request,String actionData,String email) {
        if(flage){
            // 获取  登录人信息
          /* User user_object = (User) request.getAttribute("user");*/
            User user = findUserDateByemail(email);
            Operation operation =  new Operation();
            operation.setCreateDate(new Date());
            operation.setAction(actionData);
            operation.setUserId(user.getEmail());
            operation.setUserName(user.getUsername());
            operation.setDepartment(user.getDempname());
            operation.setCompany(user.getCompanyname());
            operation.setJobs(user.getPostname());
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

    /*获取用户信息*/

    private User findUserDateByemail(String email){
        return authUserService.selectUserByEmail(email);
    }
}
