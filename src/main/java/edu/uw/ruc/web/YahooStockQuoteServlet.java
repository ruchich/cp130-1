package edu.uw.ruc.web;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.uw.ext.quote.YahooQuote;

/**
 * Servlet implementation class CourtesyServlet
 */

public class YahooStockQuoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletContext ctx;
    private String salutationXml;
    private String valedictionXml;
    private String defaultXml;
    
	
	/**
     * Default constructor. 
     */
    public YahooStockQuoteServlet() {
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig servletCfg) throws ServletException {
    	super.init(servletCfg);
    	//greeting = config.getInitParameter("greeting");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.serviceRequest(request, response);
    }

    void serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String symbol = null;
        String contentType = request.getContentType();
        if(contentType != null && contentType.startsWith("application/json")) {
            ObjectMapper quote = new ObjectMapper();
            Properties respStr = (Properties)quote.readValue(request.getInputStream(), Properties.class);
            symbol = respStr.getProperty("symbol");
        } else {
            symbol = request.getParameter("symbol");
        }

        YahooQuote quote1 = YahooQuote.getQuote(symbol);
        response.setContentType("text/xml");
        String respStr1 = String.format("<quote>%n  <symbol>%s</symbol>%n  <price>%6.2f</price>%n</quote>", new Object[]{quote1.getSymbol(), Double.valueOf((double)quote1.getPrice() / 100.0D)});
        response.setContentLength(respStr1.length());
        response.getWriter().print(respStr1);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.serviceRequest(request, response);
    }
}
