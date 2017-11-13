package com.pj.user.Utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Created by SenevBoy on 2017/11/10.
 */
public   class RequestDate {
    public static ServletRequestAttributes requestInit(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return  requestAttributes;
    }
}
