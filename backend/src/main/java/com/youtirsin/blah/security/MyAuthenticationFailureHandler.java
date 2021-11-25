package com.youtirsin.blah.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.alibaba.fastjson.JSON;
import com.youtirsin.blah.message.MessageResult;
import com.youtirsin.blah.message.ResultCode;
import com.youtirsin.blah.message.ResultTool;


@Configuration
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		MessageResult result = null;
		
		if (exception instanceof BadCredentialsException) {
            // password error
            result = ResultTool.fail(ResultCode.USER_CREDENTIALS_ERROR);
        } else if (exception instanceof InternalAuthenticationServiceException) {
            // no such user
            result = ResultTool.fail(ResultCode.USER_ACCOUNT_NOT_EXIST);
        }else{
            // other errors
            result = ResultTool.fail(ResultCode.COMMON_FAIL);
        }
		response.setContentType("text/json;charset=utf-8");
		response.getWriter().write(JSON.toJSONString(result));
	}

}
