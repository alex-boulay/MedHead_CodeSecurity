package com.ocal.medheadsecurity.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RoleTest {

	@Test
	public void testRoleEntity(){
		Role role = new Role();
		Long id = 2L;
		role.setName("DEV");
		role.setId(id);
		
		Assertions.assertEquals(role.getName(),"DEV","Les noms des roles doivent correspondre !");
		Assertions.assertEquals(role.getId(), id,"Les Ids des Rôles doivent correspondre");
		
		Assertions.assertNotEquals(role.getName(),"","Le nom ne doit pas correspondre");
		Assertions.assertNotEquals(role.getId(),1L,"L'id ne doit pas correspondre");
	}
}
