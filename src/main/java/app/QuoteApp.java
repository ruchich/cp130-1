package app;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import com.fasterxml.jackson.databind.ObjectMapper;

public class QuoteApp {

	  public QuoteApp() {
	    }

	    private static String exec(String symbol, String format)throws IOException {
	        HttpURLConnection conn = null;
	        String baseUrl = "http://localhost:8080/StockQuote/QuoteServlet";
	        final String urlStr = String.format("%s?symbol=%s&rstype=%s", baseUrl,symbol, format);
	       
	             final  URL e = new URL(urlStr);
	               
	                conn = (HttpURLConnection)e.openConnection();
	                System.out.println(conn.getURL().toString());
	                conn.setRequestMethod("GET");
	           
	          
	         final InputStream e2 = conn.getInputStream();
	         final Reader rdr2 = new InputStreamReader(e2);
	         final char[] buf1 = new char[1024];
	            int len2 = 0;
	           final StringWriter wtr = new StringWriter();
	            while((len2 = rdr2.read(buf1)) != -1) {
	                wtr.write(buf1, 0, len2);
	            }
	            rdr2.close();
	            return wtr.toString();
	      
	    }

	    public static void main(String[] args)throws IOException {
	    final String symbol = "GOOG";
	     String result;
	    System.out.println("Processing GET requests...");
	    
	    System.out.println("....................................");
	        System.out.println("JSON:");
	        result = exec(symbol, "json");
	        System.out.println();
	        System.out.println();
	        System.out.println("plain:");
	        result = exec(symbol, "plain");
	        System.out.println();
	        System.out.println("HTML:");
	        result =  exec(symbol, "HTML");
	        System.out.println();
	        System.out.println("XML:");
	        result = exec(symbol, "XML");
	        System.out.println();
	        System.out.println();
	        result =  exec(symbol, "json");
	    }
	}
