package io.github.mozzi20.security;

import static io.github.mozzi20.user.User.Role.ROLE_ADMIN;
import static io.github.mozzi20.user.User.Role.ROLE_USER;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import io.github.mozzi20.NameApi;
import io.github.mozzi20.NameApi.NameDto;
import io.github.mozzi20.user.User;
import io.github.mozzi20.user.User.Role;
import io.github.mozzi20.user.UserRepo;

@Component
public class McOidcUserService extends OidcUserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private NameApi nameApi;
	
	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		String sub = userRequest.getIdToken().getSubject();
		try {
			return userRepo.findById(sub).orElseThrow(() -> new UsernameNotFoundException("user not found"));
		} catch (UsernameNotFoundException e) {
			OidcUser oidcUser = super.loadUser(userRequest);
			User user = new User();
			user.setSub(sub);
			user.setEmail(oidcUser.getEmail());
			user.setRegisteredDate(new Date());
			// if its the first user, make them admin
			Collection<Role> roles =  userRepo.count() != 0 ? asList(ROLE_USER) : asList(ROLE_USER, ROLE_ADMIN);
			user.setRoles(roles);
			
			Optional<NameDto> nameDto = nameApi.getName(user.getEmail());
			if(nameDto.isPresent()) {
				user.setFirstname(nameDto.get().getFirstname());
				user.setLastname(nameDto.get().getLastname());
				user.setKlass(nameDto.get().getKlass());
			}
			
			userRepo.save(user);
			return user;
		}
	}
}
