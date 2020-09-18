package com.portal.api.usertests;

import java.io.IOException;
import java.util.Random;
import java.util.UUID;
import org.testng.annotations.Test;
import com.portal.domain.orgs.Organizations;

import io.restassured.response.Response;

public class OrgsTest {
	
	
	@Test
	public void createOrgWithDefeaultAccountPlan() throws IOException, IllegalAccessException {
		Organizations org = generateOrgPayload();
		String orgPayload = CountriesTests.getJsonString(org);
		
		Response orgResp = CountriesTests.createEntityReturnRawResponse(orgPayload);
		 org = CountriesTests.getListOfObjectFromJsonString(orgResp.getBody().asString(), Organizations.class);
		 System.out.println(org.getName());
	}
	
	 public static Organizations generateOrgPayload() {
		 Organizations org = new Organizations();
		    org.setUuid(UUID.randomUUID().toString());
		    System.out.println("org uuid in random pay load object: " + org.getUuid());
		    int randomInt = new Random().nextInt();
		    org.setName("KtOrg" + randomInt);
		    org.setDescription("KtDescOrganization " + randomInt);
		    org.setAccountPlanUuid("71ec8a37-9362-11e3-b742-000c2911a4db");
		    return org;
		  }

}
