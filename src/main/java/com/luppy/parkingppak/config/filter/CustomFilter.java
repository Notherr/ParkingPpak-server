package com.luppy.parkingppak.config.filter;

import com.luppy.parkingppak.utils.JwtUtil;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import java.util.*;

@Component
public class CustomFilter implements Filter {

    private final JwtUtil jwtUtil;

    public CustomFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HeaderMapRequestWrapper requestWrapper = new HeaderMapRequestWrapper(req);

        String header = ((HttpServletRequest) request).getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            requestWrapper.addHeader("AccountId", null);
            chain.doFilter(requestWrapper, response);
            return;
        }

        String jwt = ((HttpServletRequest) request).getHeader("Authorization").replace("Bearer ", "");

        if(!jwtUtil.validateToken(jwt)) {
            requestWrapper.addHeader("AccountId", null);
            chain.doFilter(requestWrapper, response);
            return;
        }

        String accountId = jwtUtil.getAccountId(jwt);

        requestWrapper.addHeader("AccountId", accountId);
        chain.doFilter(requestWrapper, response);
    }

    public class HeaderMapRequestWrapper extends HttpServletRequestWrapper {
        /**
         * construct a wrapper for this request
         *
         * @param request
         */
        public HeaderMapRequestWrapper(HttpServletRequest request) {
            super(request);
        }

        private Map<String, String> headerMap = new HashMap<String, String>();

        /**
         * add a header with given name and value
         *
         * @param name
         * @param value
         */
        public void addHeader(String name, String value) {
            headerMap.put(name, value);
        }

        @Override
        public String getHeader(String name) {
            String headerValue = super.getHeader(name);
            if (headerMap.containsKey(name)) {
                headerValue = headerMap.get(name);
            }
            return headerValue;
        }

        /**
         * get the Header names
         */
        @Override
        public Enumeration<String> getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            for (String name : headerMap.keySet()) {
                names.add(name);
            }
            return Collections.enumeration(names);
        }

        @Override
        public Enumeration<String> getHeaders(String name) {
            List<String> values = Collections.list(super.getHeaders(name));
            if (headerMap.containsKey(name)) {
                values.add(headerMap.get(name));
            }
            return Collections.enumeration(values);
        }
    }
}
