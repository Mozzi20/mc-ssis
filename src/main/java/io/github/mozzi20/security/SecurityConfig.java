package io.github.mozzi20.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import io.github.mozzi20.user.User;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private McOidcUserService mcOidcUserService;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
			.authorizeRequests()
				.antMatchers("/user").authenticated()
				.antMatchers("/admin").hasAnyRole("ADMIN")
			.and()
			.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
		.and()
			.oauth2Login()
				.loginPage("/oauth2/authorization/google")
				.userInfoEndpoint()
					.customUserType(User.class, "google")
					.oidcUserService(mcOidcUserService)
			.and()
		.and();
		// @formatter:on
	}
	
}