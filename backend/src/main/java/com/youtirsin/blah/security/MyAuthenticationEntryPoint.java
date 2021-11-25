package com.youtirsin.blah.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.alibaba.fastjson.JSON;
import com.youtirsin.blah.message.MessageResult;
import com.youtirsin.blah.message.ResultCode;
import com.youtirsin.blah.message.ResultTool;

@Configuration
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		MessageResult result = ResultTool.fail(ResultCode.USER_NOT_LOGIN);
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(result));
	}

}
