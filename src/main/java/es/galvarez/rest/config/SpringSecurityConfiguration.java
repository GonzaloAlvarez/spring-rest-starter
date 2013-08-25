// --------------------------------------------------------------------------------
//
// Copyright 2013 Gonzalo Alvarez - GonzaloAlvarez.es
// Madrid, Spain
//
// This file is part of Spring Rest Starter.
// 
// Spring Rest Starter is free software: you can redistribute it and/or
// modify it under the terms of the GNU General Public License 
// as published by the Free Software Foundation, either version 3
// of the License, or (at your option) any later version.
// 
// Spring Rest Starter is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
// See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License 
// along with Spring Rest Starter. If not, see http://www.gnu.org/licenses/.
//
// --------------------------------------------------------------------------------
//
// System : spring-rest-starter
// Sub-System : es.galvarez.rest.config
// File Name : SpringSecurityConfiguration.java
//
// Author : Gonzalo Alvarez
// Creation Date : 25/08/2013
//
// -----------------------------------------------------------------------------
package es.galvarez.rest.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

import es.galvarez.rest.repositories.UserRepository;

/**
 * @author Gonzalo Alvarez
 *
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Resource
	private UserRepository userRepository;

	@Bean
	public DigestAuthenticationEntryPoint digestEntryPoint() {
		DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
		digestAuthenticationEntryPoint.setKey("48c23fa223f32e23b45b6a2344");
		digestAuthenticationEntryPoint.setRealmName("Rest Realm Authentication");
		return digestAuthenticationEntryPoint;
	}

	@Bean
	public DigestAuthenticationFilter digestAuthenticationFilter(DigestAuthenticationEntryPoint entryPoint) {
		DigestAuthenticationFilter filter = new DigestAuthenticationFilter();
		filter.setAuthenticationEntryPoint(entryPoint);
		filter.setUserDetailsService(userRepository);
		return filter;
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userRepository);
		return authProvider;
	}
	
	@Override
	protected void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
	    auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
// @formatter:off
		http
			.exceptionHandling().authenticationEntryPoint(digestEntryPoint())
		.and()
			.sessionManagement().enableSessionUrlRewriting(false).sessionCreationPolicy(SessionCreationPolicy.stateless)
		.and()
			.authorizeUrls().antMatchers("/users/**").authenticated()
		.and()
			.addFilter(digestAuthenticationFilter(digestEntryPoint()));
// @formatter:on
	}

}
