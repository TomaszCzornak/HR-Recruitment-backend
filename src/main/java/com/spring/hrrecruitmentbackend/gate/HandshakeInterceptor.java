package com.spring.hrrecruitmentbackend.gate;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

public class HandshakeInterceptor extends HttpSessionHandshakeInterceptor {


        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
            HttpServletRequest origRequest = ((ServletServerHttpRequest)request).getServletRequest();
            String origin = origRequest.getHeader("origin");
            response.getHeaders().add("Access-Control-Allow-Origin", origin);
            return super.beforeHandshake(request, response, wsHandler, attributes);
        }
    }

