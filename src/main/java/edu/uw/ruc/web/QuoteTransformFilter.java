package edu.uw.ruc.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class QuoteTransformFilter implements Filter {
    FilterConfig cfg;

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
        String path = this.cfg.getInitParameter("filename");
        String stylepath = this.cfg.getServletContext().getRealPath(path);
        this.cfg.getServletContext().log("Path: " + stylepath);

        try {
            FileReader e = new FileReader(stylepath);
            BufferedReader br = new BufferedReader(e);
            this.cfg.getServletContext().log("Message: " + br.readLine());
        } catch (IOException var6) {
            this.cfg.getServletContext().log("Uh oh", var6);
        }

    }
}
