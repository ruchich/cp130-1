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
    
    
	
	/**
     * Default constructor. 
     */
    public YahooStockQuoteServlet() {
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig servletCfg) throws ServletException {
    	ctx = servletCfg.getServletContext();
    	System.out.println("server started");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.serviceRequest(request, response);
    }

    void serviceRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String symbol = null;
       try{
            symbol = request.getParameter("symbol");
        final YahooQuote quote = YahooQuote.getQuote(symbol);
        
        String respStr1 = String.format("<quote>%n  <symbol>%s</symbol>%n  <price>%6.2f</price>%n</quote>", quote.getSymbol(),quote.getPrice() / 100.0D);
        response.setContentType("text/xml");
        response.setContentLength(respStr1.length());
        response.getWriter().print(respStr1);
    }catch(NumberFormatException e){
    	ctx.log("Error: Quote request failed", e);
    	response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR , "Quote not available for " + symbol);
    }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.serviceRequest(request, response);
    }
}
