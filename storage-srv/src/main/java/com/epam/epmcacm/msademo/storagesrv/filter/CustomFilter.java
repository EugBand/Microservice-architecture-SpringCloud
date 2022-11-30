package com.epam.epmcacm.msademo.storagesrv.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

@Slf4j
public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        log.info("Custom filter in progress");
        chain.doFilter(request, response);
    }
}
