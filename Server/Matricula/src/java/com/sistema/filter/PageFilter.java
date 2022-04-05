package com.sistema.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "PageFilter", urlPatterns = {"/*"})
public class PageFilter implements Filter {
    List<String> protectedPages;

    public PageFilter(){
        protectedPages = new ArrayList<>();
        protectedPages.add("/PersonasIntegradoMultipart/listado.html"); 
    }
    public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(true);
        String recurso=httpRequest.getRequestURI();
        if (protectedPages.contains(recurso) && session.getAttribute("user")==null)
            request.getRequestDispatcher("/about.html").forward( request, response);
        else
            chain.doFilter(request, response);
    }
}
