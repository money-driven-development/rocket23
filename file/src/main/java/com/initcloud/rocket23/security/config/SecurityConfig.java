package com.initcloud.rocket23.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.initcloud.rocket23.security.filter.JwtAuthenticationFilter;
import com.initcloud.rocket23.security.filter.JwtGlobalEntryPoint;
import com.initcloud.rocket23.security.provider.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtGlobalEntryPoint jwtGlobalEntryPoint;
	private final JwtTokenProvider jwtTokenProvider;
	private final Properties properties;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean()
		throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();

		http.httpBasic()
			.disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/v2/api-docs/**").permitAll()
			.antMatchers("/swagger-resources/**").permitAll()
			.antMatchers("/swagger-ui/**").permitAll()
			.and()
			.authorizeRequests()
			.antMatchers("/rocket23/**").permitAll()
			.and()
			.authorizeRequests().antMatchers("/api/v1/admin/**").hasRole("ADMIN")
			.and()
			.authorizeRequests()
			.anyRequest().authenticated()
			.and()
			.exceptionHandling().authenticationEntryPoint(jwtGlobalEntryPoint)
			.and()
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, properties),
				UsernamePasswordAuthenticationFilter.class);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
