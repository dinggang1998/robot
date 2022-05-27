//package com.learn.robot.security;
//
//
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//	@Override
//    protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/403").permitAll()
//		.antMatchers("/500").hasAnyRole("122");
//		http.formLogin().usernameParameter("username").passwordParameter("pwd");
//	    http.rememberMe().rememberMeParameter("remeber");
//    }
//
//	@Override
//	public void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).
//				withUser("dinggang").password(new BCryptPasswordEncoder().encode("123456")).roles("122");
//	}
//}
