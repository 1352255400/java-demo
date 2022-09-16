package com.mantulife.demo.filter;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author W_wang
 * <p>
 * 此配置类可配置拦截器、参数解析器、返回值解析器、跨域支持等等
 * </p>
 */
@Configuration
public class FilterConfig implements WebMvcConfigurer {

    @Bean
    public AuthFilter setBean() {
        //启动时注入到spring中
        return new AuthFilter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        //由于spring boot 2.x依赖的spring 5.x版本，使用spring 5.x时，静态资源也会执行自定义的拦截器
        //所有导致静态资源不可访问的问题
        String[] exculudes = new String[]{"/*.html", "/static/**", "/html/**", "/js/**", "/css/**", "/images/**"};
        registry.addInterceptor(setBean()).addPathPatterns("/**").excludePathPatterns(exculudes);
    }
}