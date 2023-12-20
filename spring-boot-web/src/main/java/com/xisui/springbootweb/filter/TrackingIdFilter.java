package com.xisui.springbootweb.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class TrackingIdFilter extends OncePerRequestFilter {
    private final static String TRACKING_SESSION_ID = "trackingSessionID";
    private final static String KEY_TRACKING_ID = "trackingID";
    private static final String FILTER_TYPE = "FILTER_TYPE";

    private String extractTrackingID(HttpServletRequest request) {
        String trackingID = (String) request.getAttribute(KEY_TRACKING_ID);
        String trackingSessionID = getCookieValue(request.getCookies(), TRACKING_SESSION_ID);
        if(trackingSessionID==null){
            trackingSessionID = request.getHeader(TRACKING_SESSION_ID);
        }
        MDC.put(TRACKING_SESSION_ID, trackingSessionID);

        if (StringUtils.isBlank(trackingID)) {
            trackingID = request.getHeader(KEY_TRACKING_ID);
        }
        if (StringUtils.isBlank(trackingID)) {
            trackingID = request.getParameter(KEY_TRACKING_ID);
        }
        if (StringUtils.isBlank(trackingID)) {
            //Fix trackingSessionID XSS, ignore invalid value, trackingSessionID must be 0-9a-zA-Z
            if (StringUtils.isBlank(trackingSessionID) || !trackingSessionID.matches("\\w+")) {
                trackingSessionID = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                trackingID = trackingSessionID + "_" + System.currentTimeMillis();
            } else {
                trackingID = trackingSessionID + "_" + System.currentTimeMillis();
            }
        }

        return trackingID;
    }

    private String getCookieValue(Cookie[] cookies, String cookieName) {
        if (cookies == null) {
            return null;
        }
        for (int i = 0; i < cookies.length; i++) {
            if (cookies[i].getName().equals(cookieName)) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ThreadContext.remove(FILTER_TYPE);

        String trackingID = extractTrackingID(request);
        response.setHeader(KEY_TRACKING_ID, trackingID);
        request.setAttribute(KEY_TRACKING_ID, trackingID);

        MDC.put(KEY_TRACKING_ID, trackingID);
        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(KEY_TRACKING_ID);
            MDC.remove(TRACKING_SESSION_ID);
        }
    }
}
