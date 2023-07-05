package com.ocal.medheadsecurity.repository;

import com.ocal.medheadsecurity.model.*;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class RoleRepositoryTest {
	@Autowired 
	private RoleRepository roleRep;
	
	@Test
	@Transactional
	public void testFindByName() {
		Role role = new Role ();
		role.setName("Développeur");
		
		Role roleoff = new Role();
		roleoff.setName("WrongRole");
		
		role = roleRep.save(role);
		roleoff = roleRep.save(roleoff);
		
		Optional<Role> getRole = roleRep.findByName("Développeur");
		if (getRole.isEmpty()) {
			Assertions.assertTrue(false, "N'a pas pu ajouter le Role qui a été enregistré");
		}
		else {			
			Assertions.assertEquals(role, getRole.get(),"Le Role ne correspond pas a celui enregistré");
			Assertions.assertNotEquals(getRole.get(),roleoff,"Le RoleOff ne doit pas correspondre a celui enregistré");
			Assertions.assertEquals(role.getId(), getRole.get().getId(),"Les Ids doivent correspondre");
			Assertions.assertNotEquals(roleoff.getId(), getRole.get().getId(),"Les Ids doivent être différents pour des Rôles différents");
		}
		roleRep.delete(roleoff);
		roleRep.delete(role);
	}
}
