package com.mantulife.demo.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限（token）验证
 *
 * @author W_wang
 * @date 2019-06-30
 */

@Configuration
@Slf4j
public class AuthFilter extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求地址
        String path = request.getRequestURI();
        //过滤验证
        if (path.endsWith("/favicon.ico") ||
                path.endsWith("/favicon.css") ||
                path.endsWith("/favicon.js")
        ) {
            return false;
        }
        log.info("请求地址：" + path);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        // 放行
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 放行
        super.afterCompletion(request, response, handler, ex);
    }

}