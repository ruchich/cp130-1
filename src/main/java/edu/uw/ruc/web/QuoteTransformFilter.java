package edu.uw.ruc.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class QuoteTransformFilter implements Filter {
    FilterConfig cfg;
    private ServletContext  ctx;
    private String htmlstylePath;
    private String jsonstylePath;
    private String plainstylePath;
    

    public QuoteTransformFilter() {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        this.cfg.getServletContext().log("Visited! " + Calendar.getInstance().getTime());
        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        this.cfg = fConfig;
    	ctx = this.cfg.getServletContext();
        String styleSheet = this.cfg.getInitParameter("html-transform");
         htmlstylePath = ctx.getRealPath(styleSheet);
         styleSheet = this.cfg.getInitParameter("json-transform");
         jsonstylePath = ctx.getRealPath(styleSheet);
         styleSheet = this.cfg.getInitParameter("plain-transform");
         plainstylePath = ctx.getRealPath(styleSheet);
        
        ctx.log(htmlstylePath);
        ctx.log(jsonstylePath);
        ctx.log(plainstylePath);

    }
}
