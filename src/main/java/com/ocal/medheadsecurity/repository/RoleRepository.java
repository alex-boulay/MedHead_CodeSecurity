package com.ocal.medheadsecurity.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import com.ocal.medheadsecurity.model.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findByName(String name);

}
