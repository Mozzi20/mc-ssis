package io.github.mozzi20.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, String>{

	@Query("SELECT COUNT(user) > 0 FROM User user WHERE username = ?1")
	boolean existsByUsername(String username);

}
