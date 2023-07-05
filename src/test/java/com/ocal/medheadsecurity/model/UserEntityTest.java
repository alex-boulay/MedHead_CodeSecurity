package com.ocal.medheadsecurity.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;


public class UserEntityTest {

	@Test
	public void testUserEntity(){
		
		Long id1 = 14L;
		String uname1 = "uname";
		String pword1 = "pword";
		
		Long id2 = 15L;
		String uname2 = "uname2";
		String pword2 = "pword2";
		
		Role role1 = new Role();
		String username1 = "username1";
		role1.setName(username1);
		
		Role role2 = new Role();
		String username2 = "username2";
		role2.setName(username2);
		
		Role role3 = new Role();
		String username3 = "username3";
		role3.setName(username3);
		
		Role role4 = new Role();
		String username4 = "username4";
		role4.setName(username4);
		
		
		List<Role> roles1 = new ArrayList<Role>();
		roles1.add(role1);
		roles1.add(role2);
		roles1.add(role3);
		
		List<Role> roles2 = new ArrayList<Role>();
		roles2.add(role4);

		UserEntity ue1 = new UserEntity();
		ue1.setId(id1);
		ue1.setUsername(uname1);
		ue1.setPassword(pword1);
		ue1.setRoles(roles1);
		
		UserEntity ue2 = new UserEntity(
				uname2,
				pword2,
				roles2
				);
		ue2.setId(id2);
		
		Assertions.assertEquals(ue1.getId(), id1,"Ids doivent correspondre ue1");
		Assertions.assertEquals(ue2.getId(), id2,"Ids doivent correspondre ue2");
		Assertions.assertNotEquals(ue2.getId(), ue1.getId(),"Ids de ue2 et ue1 doivent être différents");
		
		Assertions.assertEquals(ue1.getUsername(),uname1,"Le nom doit correspondre ue1");
		Assertions.assertEquals(ue2.getUsername(),uname2,"Le nom doit correspondre ue2");
		Assertions.assertNotEquals(ue2.getUsername(), ue1.getUsername(),"Les noms ue1 et ue2 ne doivent pas correspondre");
		
		Assertions.assertEquals(ue1.getPassword(), pword1,"Les password doivent correspondre ue1");
		Assertions.assertEquals(ue2.getPassword(), pword2, "Les password doivent correspondre ue2");
		Assertions.assertNotEquals(ue2.getPassword(), ue1.getPassword(),"Les passwords ue2 et ue1 ne doivent pas correspondre");
		
		Assertions.assertEquals(ue1.getRoles().size(),3,"La userEntity1 doit avoir 3 roles");
		Assertions.assertEquals(ue2.getRoles().size(), 1,"La userEntity2 doit avoir 1 role");
	}
}
