package ca.sheridancollege.vuongv.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.sheridancollege.vuongv.bean.User;

public interface UserRepository extends JpaRepository<User, Long> {

	public User findByEmail(String username);

}
