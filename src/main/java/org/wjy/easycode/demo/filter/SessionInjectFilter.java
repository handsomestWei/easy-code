package org.wjy.easycode.demo.filter;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;

/**
 * 测试用注入登录信息。需要在启动类添加@ServletComponentScan注解
 * 
 * @author weijiayu
 * @date 2021/7/5 15:12
 */
@WebFilter(urlPatterns = "*", filterName = "wuToolSessionInjectFilter")
public class SessionInjectFilter implements Filter {

    @Value("${inject_session_enable:false}")
    private boolean enable;

    // default method : public void init
    // java.lang.AbstractMethodError: Receiver class xx.filter.xxxFilter does not
    // define or inherit an implementation of the resolved method 'abstract void init(javax.servlet.FilterConfig)' of
    // interface javax.servlet.Filter.
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        final HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        if (enable) {
            if (httpServletRequest.getSession().getAttribute("account") == null) {
                // TODO
                Object account = new Object();
                httpServletRequest.getSession().setAttribute("account", account);
            }
        }
        chain.doFilter(request, response);
    }
}
