package cz.muni.fi.pa165.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebFilter("/*")
public class AddCurrentYearFilter implements Filter {
    final static Logger log = LoggerFactory.getLogger(CharacterEncodingFilter.class);

    public void doFilter(ServletRequest r, ServletResponse s, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) r;
        HttpServletResponse response = (HttpServletResponse) s;
        request.setAttribute("currentyear",new SimpleDateFormat("yyyy",request.getLocale()).format(new Date()));
        filterChain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        log.debug("filter initialized ...");
    }

    public void destroy() {
    }
}
