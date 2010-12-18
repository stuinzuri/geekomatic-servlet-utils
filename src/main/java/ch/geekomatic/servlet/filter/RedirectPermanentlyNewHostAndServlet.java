/** 
Copyright (c) 2010 Stuart Thompson (stu@thompson.name / geekomatic.ch)

    MIT 'Expat' License 

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the
"Software"), to deal in the Software without restriction, including
without limitation the rights to use, copy, modify, merge, publish,
distribute, sublicense, and/or sell copies of the Software, and to
permit persons to whom the Software is furnished to do so, subject to
the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

*/

package ch.geekomatic.servlet.filter;

import static javax.servlet.http.HttpServletResponse.SC_MOVED_PERMANENTLY;
import static java.lang.String.format;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectPermanentlyNewHostAndServlet implements Filter {
	
	private String dns;
	private String servlet;
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	doFilter((HttpServletRequest)  request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String path = request.getServletPath();
		String qs   = request.getQueryString();
		
		String url;
		if (qs == null)
			url = format("http://%s%s%s",   dns, servlet, path);
		else
			url = format("http://%s%s%s%s", dns, servlet, path, qs);
		
		log(request.getRequestURL() + "-->" + url);
		
		response.setHeader("Location", url);
		response.setStatus(SC_MOVED_PERMANENTLY);
		
		response.getOutputStream().write(("This page has moved permanently: " + url).getBytes());
    }
    
	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
		dns     = config.getInitParameter("dns");
		servlet = config.getInitParameter("serlet");
	
		normalizeServlet();
	}
	
	private void normalizeServlet() {
		if (servlet == null)
			servlet = "";
	
		else if (servlet.startsWith("/"))
			servlet = servlet.substring(1);
	}
	
	private static void log(String message) {
		System.out.println(message);
	}
}