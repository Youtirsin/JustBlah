package com.youtirsin.blah.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.youtirsin.blah.user.UserService;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public UserService userService;
	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	public MyAuthenticationEntryPoint myAuthenticationEntryPoint;
	@Autowired
	public MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
	@Autowired
	public MyAuthenticationFailureHandler myAuthenticationFailureHandler;
	@Autowired
	public MyLogoutSuccessHandler myLogoutSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
//			.cors()
//				.and()
			.csrf().disable()
			.authorizeRequests()
				.antMatchers("/api/user/**").permitAll()
				.antMatchers("/test").permitAll()			// for test
				.antMatchers("/api/invite/**").permitAll()	// for dev test
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.permitAll()
				.successHandler(myAuthenticationSuccessHandler)
				.failureHandler(myAuthenticationFailureHandler)
				.loginProcessingUrl("/api/user/login")
				.usernameParameter("name")
				.passwordParameter("password")
				.and()
			.logout()
				.permitAll()
				.logoutUrl("/api/user/logout")
				.logoutSuccessHandler(myLogoutSuccessHandler)
				.deleteCookies("JSESSIONID")
				.and()
			.exceptionHandling()
				.authenticationEntryPoint(myAuthenticationEntryPoint);
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(bCryptPasswordEncoder);
		provider.setUserDetailsService(userService);
		return provider;
	}
}
