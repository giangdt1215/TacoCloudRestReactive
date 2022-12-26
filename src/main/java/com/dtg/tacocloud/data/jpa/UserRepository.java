package com.dtg.tacocloud.data.jpa;

import org.springframework.data.repository.CrudRepository;

import com.dtg.tacocloud.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
}
