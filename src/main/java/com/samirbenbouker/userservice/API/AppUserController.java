package com.samirbenbouker.userservice.API;

import static java.util.Arrays.stream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samirbenbouker.userservice.Service.AppUserService;
import com.samirbenbouker.userservice.domain.AppRole;
import com.samirbenbouker.userservice.domain.AppUser;

import lombok.Data;

@RestController
@RequestMapping("api/v1")
public class AppUserController {
	
	private final AppUserService appUserService;
	
	@Autowired
	public AppUserController(AppUserService appUserService) {
		this.appUserService = appUserService;
	}
	
	// This end point return all users in our database
	@GetMapping("/users")
	public List<AppUser> getAllUser() {
		return appUserService.getAllUsers();
	}
	
	// This end point can save a App User in our database
	@PostMapping("/user/save")
	public AppUser saveAppUser(@RequestBody AppUser appUser) {
		return appUserService.saveUser(appUser);
	}
	
	//This end point can save a App Role in our database
	@PostMapping("/role/save")
	public AppRole saveAppRole(@RequestBody AppRole appRole) {
		return appUserService.saveRole(appRole);
	}
	
	//This end point can add a User Role in a User
	@PostMapping("/role/addtouser")
	public void addRoleToUser(@RequestBody RoleToUserForm form) {
		appUserService.addRoleToUser(form.getUsername(), form.getRoleName());
	}
	
	// This end point can refresh JSON Web Token user
	@GetMapping("/token/refresh")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
		String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			try {
				String refreshToken = authorizationHeader.substring("Bearer".length());
				Algorithm algorithm = Algorithm.HMAC256("secrect".getBytes());
				JWTVerifier verifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = verifier.verify(refreshToken);
				
				AppUser user = appUserService.getUser(decodedJWT.getSubject());
				String accessToken = JWT.create()
			             .withSubject(user.getUsername())
			             .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
			             .withIssuer(request.getRequestURL().toString())
			             .withClaim("roles", user.getRoles().stream().map(AppRole::getName).collect(Collectors.toList()))
			             .sign(algorithm);

			    Map<String, String> tokens = new HashMap<String, String>();
			    tokens.put("accessToken", accessToken);
			    tokens.put("refreshToken", refreshToken);
			    
			    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			    new ObjectMapper().writeValue(response.getOutputStream(), tokens);
			} catch(Exception e) {
				throw new IllegalStateException(e.getMessage());
			}
		} else {
			throw new RuntimeException("Refresh token is missing");
		}
	}
	
}

@Data
class RoleToUserForm {
	
	private String username;
	private String roleName;
	
	public RoleToUserForm() {}

	public RoleToUserForm(String username, String roleName) {
		super();
		this.username = username;
		this.roleName = roleName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	
}

