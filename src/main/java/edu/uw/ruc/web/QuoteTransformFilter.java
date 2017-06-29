package edu.uw.ruc.web;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
        final CharResponseWrapper responseWrapper = new CharResponseWrapper((HttpServletResponse)response);
        String rsType = request.getParameter("rstype");
        if(rsType == null){
        	rsType = "html";
        }
        ctx.log("'rsType = '" + rsType + "'");
        chain.doFilter(request, responseWrapper);
        final String xml = responseWrapper.toString();
        
        Source styleSource = null;
        String responseStr = xml;
        if(!rsType.equals("xml")){
        	switch(rsType){
        	case "Plain":
        		response.setContentType("text/plain");
        		//styleSource = new StreamSource(plainstylePath);
        		break;
        	case "json":
        		response.setContentType("application/json");
        		//styleSource = new StreamSource(jsonstylePath);
        		break;
        	default:
        		response.setContentType("application/html");
        		//styleSource = new StreamSource(htmlstylePath);
        		        		       		
        	}
        	
        /*	final StringReader sr = new StringReader(xml);
        	final Source xmlSource = new StreamSource(sr);
        	try{
        		final TransformerFactory transformerFactory = TransformerFactory.newInstance();
        		Transformer transformer;
        		transformer = 	transformerFactory.newTransformer(styleSource);
        		final StringWriter wtr = new StringWriter();
        		final StreamResult result = new StreamResult(wtr);
        		transformer.transform(xmlSource, result);
        		responseStr = wtr.toString();
        	}catch(final TransformerConfigurationException ex){
        		ctx.log("Error: config Error", ex);
        			return;
        	} catch ( final TransformerException e){
        		ctx.log("Error:Transform error", e);
        		return;
        				
        				
        	}*/
        	 response.setContentLength(responseStr.length());
             response.getWriter().print(responseStr);
        }
        
    }

    public void init(FilterConfig fConfig) throws ServletException {
        
    	ctx = fConfig.getServletContext();
      /*  String styleSheet = fConfig.getInitParameter("html-transform");
         htmlstylePath = ctx.getRealPath(styleSheet);
         styleSheet = this.cfg.getInitParameter("json-transform");
         jsonstylePath = ctx.getRealPath(styleSheet);
         styleSheet = this.cfg.getInitParameter("plain-transform");
         plainstylePath = ctx.getRealPath(styleSheet);
        
        ctx.log(htmlstylePath);
        ctx.log(jsonstylePath);
        ctx.log(plainstylePath);*/

    }
}
