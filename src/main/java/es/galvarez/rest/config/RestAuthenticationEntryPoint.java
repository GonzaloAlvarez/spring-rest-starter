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
// System : spring-rest-bootstrap
// Sub-System : es.galvarez.rest.config
// File Name : RestAuthenticationEntryPoint.java
//
// Author : Gonzalo Alvarez
// Creation Date : 25/09/2013
//
// -----------------------------------------------------------------------------
package es.galvarez.rest.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

/**
 * @author Gonzalo Alvarez
 *
 */
public class RestAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {
	
	 @Override
     public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
         response.addHeader("Access-Control-Allow-Origin", "null");
         response.addHeader("WWW-Authenticate", "RestBasic realm=\"" + getRealmName() + "\"");
         response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
         response.setContentLength(0);     
	 }
}
