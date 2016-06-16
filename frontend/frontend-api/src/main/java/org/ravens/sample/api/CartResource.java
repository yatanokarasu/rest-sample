/*  
 *  The MIT License (MIT)
 *  
 *  Copyright (c) 2016 Yusuke TAKEI.
 *  
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */
package org.ravens.sample.api;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;


/**
 * 
 */
@Path("/cart")
public class CartResource {
    
    private List<Item> items = new ArrayList<>();
    
    
    /**
     * @return items
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Cart getItems() {
        return new Cart(this.items);
    }
    
    
    /**
     * @param requestBody
     */
    @PUT
    public void addItem(final InputStream requestBody) {
        try {
            String name = new String(readFromStream(requestBody));
            this.items.add(new Item(name));
        } catch (IOException exception) {
            throw new WebApplicationException(exception);
        }
    }
    
    
    /**
     * @param request 
     */
    //@POST
    //public void login(final InputStream stream, @Context final HttpServletRequest request) {
    //    request.setAttribute("key", "value");
    //    final AsyncContext asyncContext = request.startAsync();
    //    asyncContext.addListener(new AsyncListenerImpl());
    //    asyncContext.start(new Runnable() {
    //        
    //        public void run() {
    //            System.out.println("async run");
    //        }
    //    });
    //}
    
    
    private byte[] readFromStream(InputStream stream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1000];
        int wasRead = 0;
        do {
            wasRead = stream.read(buffer);
            if (wasRead > 0) {
                baos.write(buffer, 0, wasRead);
            }
        } while (wasRead > -1);
        return baos.toByteArray();
    }
    
    
    private static final class AsyncListenerImpl implements AsyncListener {

        /**
         * @see javax.servlet.AsyncListener#onComplete(javax.servlet.AsyncEvent)
         */
        public void onComplete(AsyncEvent event) throws IOException {
            event.getAsyncContext().complete();
        }

        /**
         * @see javax.servlet.AsyncListener#onTimeout(javax.servlet.AsyncEvent)
         */
        public void onTimeout(AsyncEvent event) throws IOException {
            // TODO Auto-generated method stub
            
        }

        /**
         * @see javax.servlet.AsyncListener#onError(javax.servlet.AsyncEvent)
         */
        public void onError(AsyncEvent event) throws IOException {
            // TODO Auto-generated method stub
            
        }

        /**
         * @see javax.servlet.AsyncListener#onStartAsync(javax.servlet.AsyncEvent)
         */
        public void onStartAsync(AsyncEvent event) throws IOException {
            String value = (String) event.getAsyncContext().getRequest().getAttribute("key");
        }
        
    }
    
}
