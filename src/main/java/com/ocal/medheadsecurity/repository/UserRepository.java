package com.ocal.medheadsecurity.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.ocal.medheadsecurity.model.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity,Long> {
	Optional<UserEntity> findByUsername(String username);
	Boolean existsByUsername(String username);
}
