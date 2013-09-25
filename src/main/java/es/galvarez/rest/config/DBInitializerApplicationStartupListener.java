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
// File Name : DBInitializerApplicationStartupListener.java
//
// Author : Gonzalo Alvarez
// Creation Date : 25/08/2013
//
// -----------------------------------------------------------------------------
package es.galvarez.rest.config;

import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

import es.galvarez.rest.model.Role;
import es.galvarez.rest.model.User;
import es.galvarez.rest.repositories.RoleRepository;
import es.galvarez.rest.repositories.UserRepository;

/**
 * @author Gonzalo Alvarez
 *
 */
public class DBInitializerApplicationStartupListener implements ApplicationListener<ContextRefreshedEvent> {

	public void onApplicationEvent(ContextRefreshedEvent event) {
		Role adminRole = null;
		Role userRole = null;

		ApplicationContext applicationContext = event.getApplicationContext();
		ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder(256);
		passwordEncoder.setEncodeHashAsBase64(true);
		
		if(applicationContext != null) {
			RoleRepository roleRepository = applicationContext.getBean(RoleRepository.class);
			if(roleRepository != null) {
				if(roleRepository.findOne("ADMIN") == null) {
					adminRole = new Role();
					adminRole.setAuthority("ADMIN");
					roleRepository.save(adminRole);
				}
				if(roleRepository.findOne("USER") == null) {
					userRole = new Role();
					userRole.setAuthority("USER");
					roleRepository.save(userRole);
				}
			}
			UserRepository userRepository = applicationContext.getBean(UserRepository.class);
			if(userRepository != null) {
				if(userRepository.loadUserByUsername("admin") == null) {
					User adminUser = new User();
					adminUser.setUsername("admin");
					adminUser.setPassword(passwordEncoder.encodePassword("admin123", ""));
					adminUser.setAuthorities(
									Arrays.asList(new Role[]{
											roleRepository.findOne("ADMIN")
											, roleRepository.findOne("USER")})
											);
					userRepository.save(adminUser);
				}
			}
		}
	}

}
