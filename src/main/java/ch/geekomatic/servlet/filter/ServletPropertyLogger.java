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

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletPropertyLogger implements Filter {
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    	doFilter((HttpServletRequest)  request, (HttpServletResponse) response, chain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		StringBuilder out = new StringBuilder();
		out.append("\n");
		
		out.append("getProtocol     " + request.getProtocol());
		out.append("\n");

		out.append("getMethod       " + request.getMethod());
		out.append("\n");

		out.append("getRequestURI   " + request.getRequestURI());
		out.append("\n");
		
		out.append("getRequestURL   " + request.getRequestURL());
		out.append("\n");
		
		out.append("getServletPath  " + request.getServletPath());
		out.append("\n");
				
		out.append("getQueryString  " + request.getQueryString());
		out.append("\n");
		
		log(out.toString());
		
		chain.doFilter(request, response);
    }
    
	public void destroy() {
	}

	public void init(FilterConfig config) throws ServletException {
	}
	
	private static void log(String message) {
		System.out.println(message);
	}
}