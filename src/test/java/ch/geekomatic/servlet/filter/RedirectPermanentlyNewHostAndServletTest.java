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

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ch.geekomatic.servlet.filter.RedirectPermanentlyNewHostAndServlet;
import ch.geekomatic.servlet.mock.MockFilterConfig;
import ch.geekomatic.servlet.mock.MockHttpServletRequest;
import ch.geekomatic.servlet.mock.MockHttpServletResponse;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class RedirectPermanentlyNewHostAndServletTest 
    extends TestCase
{

    public RedirectPermanentlyNewHostAndServletTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( RedirectPermanentlyNewHostAndServletTest.class );
    }

	/*   Test filter...
	...The configuration...
	 *    - with    dns
	 *    - with    confServlet "/name"
	 *    - with    confServlet "name"
	 *    - with    confServlet "/"
	 *    - with    confServlet ""
	 *    - without confServlet 

	...The request...
	 *    - with    servletPath
	 *    - without servletPath
	 *    - with    queryString
	 *    - without queryString
	 */

    
    public void testFilter_0() {
    	testFilter("/index.html", "?happy=true", "sample.ch", "");
    }
    public void testFilter_1() {
    	testFilter("/index.html", null, "sample.ch", "");
    }
    public void testFilter_2() {
    	testFilter("/", null, "sample.ch", "");
    }
    public void testFilter_3() {
    	testFilter("", null, "sample.ch", "");
    }
    public void testFilter_4() {
    	testFilter(null, null, "sample.ch", "");
    }

    private void testFilter(String reqServlet, String reqQs, String confDns, String confServlet) {
    	
    	try {
    		HasBeenCalledFilterChain chain = new HasBeenCalledFilterChain();
        	FilterConfig config = new MockFilterConfig(confDns, confServlet);
        	HttpServletRequest request = new MockHttpServletRequest(reqServlet, reqQs);
        	HttpServletResponse response = new MockHttpServletResponse();
        	
        	RedirectPermanentlyNewHostAndServlet filter = new RedirectPermanentlyNewHostAndServlet();
			filter.init(config);
			filter.doFilter(request, response, chain);

			assertFalse("FilterChain ran",  chain.hasDoFilterCall() );			
			
    	} catch (ServletException e) {
    		assertTrue("Unexpected ServletException raised", false);
		} catch (IOException oie) {
    		assertTrue("Unexpected IOException raised", false);
		}

    }
}



