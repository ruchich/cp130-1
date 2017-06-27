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

	    private static void exec(String symbol, String format, boolean useBody) {
	        HttpURLConnection conn = null;
	        String baseUrl = "http://localhost:8080/StockQuote/QuoteServlet";

	        try {
	            if(useBody) {
	                URL e = new URL(baseUrl);
	                conn = (HttpURLConnection)e.openConnection();
	                conn.setRequestMethod("POST");
	                conn.setDoOutput(true);
	                conn.setRequestProperty("Content-Type", "application/json");
	                Properties rdr = new Properties();
	                rdr.put("symbol", symbol);
	                rdr.put("rstype", format);
	                StringWriter buf = new StringWriter();
	                ObjectMapper len = new ObjectMapper();
	                len.writeValue(buf, rdr);
	                OutputStream out = conn.getOutputStream();
	                out.write(buf.toString().getBytes());
	            } else {
	                String e1 = String.format("%s?symbol=%s&rstype=%s", new Object[]{baseUrl, symbol, format});
	                System.out.println(e1);
	                URL rdr1 = new URL(e1);
	                conn = (HttpURLConnection)rdr1.openConnection();
	                conn.setRequestMethod("GET");
	            }

	            System.out.printf("Content-Type: %s%n", new Object[]{conn.getContentType()});
	            InputStream e2 = conn.getInputStream();
	            InputStreamReader rdr2 = new InputStreamReader(e2);
	            char[] buf1 = new char[1024];
	            boolean len1 = false;

	            int len2;
	            while((len2 = rdr2.read(buf1)) != -1) {
	                System.out.print(new String(buf1, 0, len2));
	            }
	        } catch (MalformedURLException var10) {
	            var10.printStackTrace();
	        } catch (IOException var11) {
	            var11.printStackTrace();
	        }

	    }

	    public static void main(String[] args) {
	        String symbol = "GOOG";
	        System.out.println("JSON:");
	        exec(symbol, "json", false);
	        System.out.println();
	        System.out.println();
	        System.out.println("plain:");
	        exec(symbol, "plain", false);
	        System.out.println();
	        System.out.println("HTML:");
	        exec(symbol, "html", false);
	        System.out.println();
	        System.out.println("XML:");
	        exec(symbol, "xml", false);
	        System.out.println();
	        System.out.println();
	        exec(symbol, "html", true);
	    }
	}
