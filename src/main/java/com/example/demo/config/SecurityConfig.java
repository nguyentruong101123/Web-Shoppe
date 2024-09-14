package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.services.UserService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	@Autowired 
	UserService service;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService userDetailsService() {
		return service;
	}
	
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
		dao.setUserDetailsService(service);
		dao.setPasswordEncoder(passwordEncoder());
		return dao;
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(authenticationProvider());
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.csrf(csrf->csrf.disable()).cors(cors->cors.disable())
		.authorizeHttpRequests(res->res
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/home/create/product").authenticated()
				.anyRequest().permitAll())
		.formLogin(log->log
				.loginPage("/auth/login/form")
				.loginProcessingUrl("/auth/login")
				.defaultSuccessUrl("/auth/login/success",false)
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll())
		.oauth2Login(oauth2->oauth2
				.loginPage("/auth/login")
				.defaultSuccessUrl("/auth/oauth2/login/success")
				.failureUrl("/auth/login/error")
                .authorizationEndpoint(auth ->auth
                .baseUri("/oauth2/authorization")))
		.exceptionHandling(handling->handling
				.accessDeniedPage("/auth/access/denied"))
		.logout(out->out
				.logoutUrl("/auth/logoff")
				.logoutSuccessUrl("/auth/logoff/success"));
			
		
		return http.build();
		
	}
	
	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().requestMatchers(HttpMethod.OPTIONS, "/**");
	}
	
}
