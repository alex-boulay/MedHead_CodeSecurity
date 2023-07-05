package com.ocal.medheadsecurity.repository;

import com.ocal.medheadsecurity.model.*;

import java.util.List;
import java.util.ArrayList;
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
public class UserRepositoryTest {

	@Autowired 
	private RoleRepository roleRep;
	
	@Autowired 
	private UserRepository userRep;
	
	
	// Exécurte findbyusername et existsbyUsername qui sont similaire en terme de fonctionnement
	@Test
	@Transactional
	public void testFindByUsername(){
		Role role1 = new Role();
		role1.setName("Dev");
		role1 = roleRep.save(role1);
		
		Role role2 = new Role();
		role2.setName("Assistant");
		role2= roleRep.save(role2);
		
		/*
		List<Role> lrole = (List<Role>) roleRep.findAll();
		for (Role r : lrole) {
			System.out.println(r.getId());
			System.out.println(r.getName());	
		}*/
		
		List<Role> roles = new ArrayList<Role>();
		roles.add(role1);
		
		List<Role> roles2 = new ArrayList<Role>();
		roles2.add(role2);
		
		UserEntity ue1 = new UserEntity();
		ue1.setUsername("Marc");
		ue1.setPassword("password");
		ue1.setRoles(roles);
		ue1 = userRep.save(ue1);
		
		UserEntity ue2 = new UserEntity();
		ue2.setUsername("Maurice");
		ue2.setPassword("password2");
		ue2.setRoles(roles2);
		ue2 = userRep.save(ue2);
		
		Assertions.assertTrue(userRep.existsByUsername("Marc"),"L'utilisateur Marc devrait exister");
		Assertions.assertTrue(userRep.existsByUsername("Maurice"),"L'utilisateur Maurice devrait exister");
		
		Assertions.assertFalse(userRep.existsByUsername("OIYUVBODIEBZAE784984"),"L'utilisateur spécifié devrait ne pas exister");
		Assertions.assertNull(userRep.findByUsername("OIYUVBODIEBZAE784984").orElse(null),"L'utilisateur spécifié ne devrait pas exister");
		
		UserEntity oue1 = userRep.findByUsername("Marc").orElse(null);
		UserEntity oue2 = userRep.findByUsername("Maurice").orElse(null);
		
		Assertions.assertNotNull(oue1,"Entité 1 doit exister");
		Assertions.assertNotNull(oue2,"Entité 2 doit exister");
		
		Assertions.assertEquals(oue1, ue1,"Entité 1 doit correspondre");
		Assertions.assertEquals(oue2, ue2,"Entité 2 doit correspondre");
		Assertions.assertNotEquals(oue2, oue1,"Entité 1 et 2 doivent ne pas correspondre");
		
		userRep.delete(ue2);
		userRep.delete(ue1);
		roleRep.delete(role2);
		roleRep.delete(role1);
	}

	
}
