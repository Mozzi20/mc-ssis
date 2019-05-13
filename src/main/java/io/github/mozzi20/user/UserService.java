package io.github.mozzi20.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	
	boolean usernameExists(String username) {
		return userRepo.existsByUsername(username);
	}
	
	void updateUsername(String username) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user.setUsername(username.trim());
		userRepo.save(user);
	}

	@Transactional(readOnly=true)
	public long userCount() { 
		return userRepo.count();
	}

	@Transactional(readOnly=true)
	public Iterable<User> getAllUsers() {
		return userRepo.findAll();
	}

}
