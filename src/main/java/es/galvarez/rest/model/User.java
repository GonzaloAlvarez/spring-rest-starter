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
// Sub-System : es.galvarez.rest.model
// File Name : User.java
//
// Author : Gonzalo Alvarez
// Creation Date : 25/08/2013
//
// -----------------------------------------------------------------------------
package es.galvarez.rest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Gonzalo Alvarez
 *
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(name="USERNAME_U", columnNames="USERNAME"))
@NamedQuery(name="User.loadUserByUsername", query="SELECT u FROM User u WHERE u.username = ?1")
@Getter @Setter
public class User implements Serializable, UserDetails {

	private static final long serialVersionUID = -9005943024026207527L;
	public static final String ROLE_USER_JOIN = "USER_ROLES";

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@JsonIgnore
	private Long id;
	
	@NotNull
	@Size(min=4, max=56)
	private String username;
	
	@NotNull
	@JsonIgnore
	private String password;
	
	@JsonIgnore
	private boolean accountNonExpired = true;
	
	@JsonIgnore
	private boolean accountNonLocked = true;
	
	@JsonIgnore
	private boolean credentialsNonExpired = true;
	
	@JsonIgnore
	private boolean enabled = true;
	
	@ManyToMany(cascade=CascadeType.REFRESH, fetch=FetchType.EAGER)
	@JoinTable(name=User.ROLE_USER_JOIN)
	@JsonIgnore
	private List<Role> authorities;
}
