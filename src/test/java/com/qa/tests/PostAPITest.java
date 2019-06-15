package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

public class PostAPITest extends TestBase {

	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String uri;
	RestClient restClient;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod
	public void setUp() {
		testBase = new TestBase();
		serviceUrl = prop.getProperty("url");
		apiUrl = prop.getProperty("serviceurl");
		// https://reqres.in/api/users

		uri = serviceUrl + apiUrl;	
		
	}
	
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException {
		
		 restClient = new RestClient();
		 HashMap<String, String> headerMap = new HashMap<String, String>();
	     headerMap.put("Content-Type", "application/json");
	     
	     //to generate JSON we need JACKSON API. Also called marshelling.
	     ObjectMapper mapper = new ObjectMapper();
	     Users users = new Users("morpheus", "leader"); //expected users object
	     
	     //object to json file conversion
	     mapper.writeValue(new File("C:\\Users\\welcome\\eclipse-workspace\\restapi\\src\\main\\java\\com\\qa\\data\\users.json"), users);
	     
	     //object to JSON in string
	     String usersJsonString = mapper.writeValueAsString(users);
	     System.out.println(usersJsonString);
	     
	     closeableHttpResponse = restClient.post(uri, usersJsonString, headerMap); //call the api
	     
	     //Validate response from API
	     //1.Status code:
	     int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
	     Assert.assertEquals(statusCode, testBase.RESPONSE_STATUS_CODE_201);
	     
	     //2.Check JSON String
	     String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
	     JSONObject responseJson = new JSONObject(responseString);
	     System.out.println("Response JSON from API--->" + responseJson);
	     
	     //JSON to Java object conversion. Unmarshelling
	     Users usersResObj = mapper.readValue(responseString, Users.class); //actual users object
	     System.out.println(usersResObj);
	     
	     Assert.assertTrue(users.getName().equals(usersResObj.getName()));
	     Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
	     
	     System.out.println(usersResObj.getId());
	     System.out.println(usersResObj.getCreatedAt());
	
	}
	
}
