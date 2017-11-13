package com.pj.Aspect;


import com.pj.user.Utils.RequestDate;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import static javax.swing.UIManager.get;

/**
 * Created by SenevBoy on 2017/11/9.
 */
@Aspect
@Configuration
public class AspectServer {

  //  private static final Logger log = LoggerFactory.getLogger(AspectServer.class);
    // 只关注方法名为find前缀的
  @Pointcut("execution(* com.pj.user.service.*.*(..))")
  public void executeService()
  {

  }

    @Before("executeService()")
    public void beforeAdvide(JoinPoint point){
        Object[] args = point.getArgs();
        if (args != null && args.length > 0 && args[0].getClass() == String.class) {
            for (Object ar : args){
                System.out.println(ar);
            }
        }

    }

    // 关注 信用等级的更新操作 记录日志
   @Pointcut("execution(* com.pj.user.service.*.updateLevelById(..))")
    public void updateLevelById(){}



    @After("updateLevelById()")
    public void updateLevelByIdAfter(JoinPoint point) throws NoSuchFieldException, IllegalAccessException, IntrospectionException, InvocationTargetException {
        // 数组设定
        String actionData = "";
        ServletRequestAttributes attributes = RequestDate.requestInit();
        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> enu=request.getParameterNames();
        Object oldData = request.getSession().getAttribute("oldData");
        Object newData = request.getSession().getAttribute("newData");
        Field[] oldfields = oldData.getClass().getDeclaredFields();
        Field[] newfields = newData.getClass().getDeclaredFields();
        actionData+="信用等级管理：";
        boolean  flage = false;
        for (int i = 0 ; i <oldfields.length;i++ ){
            PropertyDescriptor pd = new PropertyDescriptor(oldfields[i].getName(), oldData.getClass());
            Method getMethod = pd.getReadMethod();//获得get方法  
            Object o = getMethod.invoke(oldData);//执行get方法返回一个Object
            PropertyDescriptor pd2 = new PropertyDescriptor(newfields[i].getName(), oldData.getClass());
            Method getMethod2 = pd2.getReadMethod();//获得get方法  
            Object o2 = getMethod.invoke(newData);//执行get方法返回一个Object
         //   actionData = startAdd(o, o2, oldfields, actionData, BasicProperties.Basic_UserLevel_paramName, BasicProperties.Basic_UserLevel_paramVal, i, flage);
            if(!o.toString().equals(o2.toString())){
                // 判断字段名称
                for (int j = 0 ; j< BasicProperties.Basic_UserLevel_paramName.length ;j++){
                    if(oldfields[i].getName().toString().equals(BasicProperties.Basic_UserLevel_paramName[j].toString())){
                        actionData+=" "+BasicProperties.Basic_UserLevel_paramVal[j];
                    }
                }
                actionData+="< "+ o+" >（ "+02+" ） ; ";
                flage = true;
            }
            if(flage){
                // 获取  登录人信息
                User user_object = request.getSession().getAttribute("user_object");
                
                // 追加日志记录

            }
        }
    }


/*

  @After("executeService()")
    public void after(JoinPoint point)
    {
        String realClassName = getRealClassName(point);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = attributes.getRequest();
        Enumeration<String> enu=request.getParameterNames();

        while(enu.hasMoreElements()){

            String paraName=(String)enu.nextElement();

            System.out.println(paraName+": "+request.getParameter(paraName));

        }

       // log.debug("调用-----"+ realClassName + " 执行 " + getMethodName(point) + " 方法之后");
    }
*/



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



}
