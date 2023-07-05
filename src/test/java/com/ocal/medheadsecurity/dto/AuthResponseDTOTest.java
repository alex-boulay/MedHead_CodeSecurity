package com.ocal.medheadsecurity.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthResponseDTOTest {

	@Test
	public void AuthResponseDTO() {
		AuthResponseDTO rd = new AuthResponseDTO("test");
		Assertions.assertEquals(rd.getAccessToken(), "test","Le Token doit être identique");
		Assertions.assertEquals(rd.getTokenType(), "Bearer ","Le type de Token doit être identique");
		rd.setAccessToken("2ndtest");
		rd.setTokenType("2ndpassword");
		Assertions.assertNotEquals(rd.getAccessToken(), "test","Le Token doit être identique");
		Assertions.assertNotEquals(rd.getTokenType(), "class","Le type de Token doit être différent");
		Assertions.assertEquals(rd.getAccessToken(),"2ndtest","Le Token doit être identique 2");
		Assertions.assertEquals(rd.getTokenType(), "2ndpassword","Le type de Token doit être identique 2");
	}
}
