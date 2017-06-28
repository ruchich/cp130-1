package edu.uw.ruc.web;


import java.io.CharArrayWriter;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


public class CharResponseWrapper  extends HttpServletResponseWrapper {
    private final StringWriter output;

    public CharResponseWrapper(final HttpServletResponse response) {
        super(response);
        output = new StringWriter();
    }

    @Override
    public PrintWriter getWriter() {
        return new PrintWriter(output);
    }

    @Override
    public String toString() {
        return output.toString();
    }
}



