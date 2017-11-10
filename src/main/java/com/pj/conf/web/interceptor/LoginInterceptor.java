//package com.pj.conf.web.interceptor;
//
//import com.pj.auth.controller.AuthPostMenuController;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class LoginInterceptor implements HandlerInterceptor {
//
//    public static final String excludeURL = "/getLoginUserDetails";
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        StringBuffer url = request.getRequestURL();
//        if(!url.toString().contains(excludeURL)) {
//            Object o = request.getSession().getAttribute(AuthPostMenuController.TAG);
//            if (o == null) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//
//    }
//}
