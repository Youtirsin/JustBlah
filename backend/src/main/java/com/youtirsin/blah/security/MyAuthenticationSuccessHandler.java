package com.youtirsin.blah.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.youtirsin.blah.message.MessageResult;
import com.youtirsin.blah.message.ResultTool;

@Configuration
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		MessageResult result = ResultTool.success();
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(result));
	}
}
