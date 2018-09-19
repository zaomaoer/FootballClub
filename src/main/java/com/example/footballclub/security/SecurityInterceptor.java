package com.example.footballclub.security;

import com.example.footballclub.service.RedisService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SecurityInterceptor implements HandlerInterceptor {

    private static Logger logger = LogManager.getLogger(SecurityInterceptor.class);

    @Resource
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证权限
        if (this.hasPermission(handler,request)) {
            //System.out.println(request.getRequestURL() + "权限校验通过");
            logger.info(request.getRequestURL() + "权限校验通过");
            return true;
        }
        //  null == request.getHeader("x-requested-with") TODO 暂时用这个来判断是否为ajax请求
        // 如果没有权限 则抛403异常 springboot会处理，跳转到 /error/403 页面
        //response.sendError(HttpStatus.FORBIDDEN.value(), "无权限");
        //System.out.println(request.getRequestURL() + "没有权限访问!");
        logger.warn(request.getRequestURL() + "没有权限访问!");
        return false;
    }

    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasPermission(Object handler,HttpServletRequest request) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            RequiredPermission requiredPermission = handlerMethod.getMethod().getAnnotation(RequiredPermission.class);
            // 如果方法上的注解为空 则获取类的注解
            if (requiredPermission == null) {
                requiredPermission = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RequiredPermission.class);
            }
            // 如果标记了注解，则判断权限
            if (requiredPermission != null && !"".equals(requiredPermission.value())) {
                if(null == request.getAttribute("thirdSessionId")){
                    return false;
                }
                if(PermissionConstants.MEMBER.equals(requiredPermission.value())){//会员权限
                    // redis 中获取会员信息 并判断是否有权限
                    String result = redisService.getUserIdByThirdSessionId(request.getAttribute("thirdSessionId").toString());
                    if (result == null){
                        return false;
                    }
                }
                if (PermissionConstants.ADMIN.equals(requiredPermission.value())){//管理员权限

                }
            }
        }
        return true;
    }
}
