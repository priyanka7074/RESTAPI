package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.Testutil;

public class GetAPITest extends TestBase {
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

	
	@Test(priority = 1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException {
        restClient = new RestClient();
        closeableHttpResponse = restClient.get(uri);
		
		// a. Status Code
				int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
				System.out.println("Status Code--->" + statusCode);
				
				Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

				// b. JSON String
				String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		        JSONObject responseJson = new JSONObject(responseString);
				System.out.println("Response JSOn from API--->" + responseJson);
				
				//Single value assertions:
				//per_page
				String perPageValue  = Testutil.getValueByJPath(responseJson,  "/per_page");
				System.out.println("Value of per page is--->"+perPageValue);
				Assert.assertEquals(Integer.parseInt(perPageValue), 3);
				
				//total
				String totalValue  = Testutil.getValueByJPath(responseJson,  "/total");
				System.out.println("Value of total is--->"+totalValue);
				Assert.assertEquals(Integer.parseInt(totalValue), 12);
				
				//get the value from JSON array
				String lastName = Testutil.getValueByJPath(responseJson, "/data[0]/last_name");
				System.out.println(lastName);
				Assert.assertEquals(lastName, "Bluth");
				
				String id = Testutil.getValueByJPath(responseJson, "/data[0]/id");
				System.out.println(id);
				Assert.assertEquals(Integer.parseInt(id), 1);
				
				String avatar = Testutil.getValueByJPath(responseJson, "/data[0]/avatar");
				System.out.println(avatar);
				Assert.assertEquals(avatar, "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
				
				String firstName = Testutil.getValueByJPath(responseJson, "/data[0]/first_name");
				System.out.println(firstName);
				Assert.assertEquals(firstName, "George");

				// c. All Headers
				Header[] headerArray = closeableHttpResponse.getAllHeaders();
		        HashMap<String, String> allHeaders = new HashMap<String, String>();

				for (Header header : headerArray) {
					allHeaders.put(header.getName(), header.getValue());

				}

				System.out.println("Headers Array--->" + allHeaders);

	}
	
	@Test(priority = 2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException {
        restClient = new RestClient();
        
        HashMap<String, String> headerMap = new HashMap<String, String>();
        headerMap.put("Content-Type", "application/json");
//        headerMap.put("username", "priya@amazon.com");
//        headerMap.put("password", "test@123");
//        headerMap.put("Auth_Token", "12345");
        
        closeableHttpResponse = restClient.get(uri, headerMap );
		
		// a. Status Code
				int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();
				System.out.println("Status Code--->" + statusCode);
				
				Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");

				// b. JSON String
				String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		        JSONObject responseJson = new JSONObject(responseString);
				System.out.println("Response JSON from API--->" + responseJson);
				
				//Single value assertions:
				//per_page
				String perPageValue  = Testutil.getValueByJPath(responseJson,  "/per_page");
				System.out.println("Value of per page is--->"+perPageValue);
				Assert.assertEquals(Integer.parseInt(perPageValue), 3);
				
				//total
				String totalValue  = Testutil.getValueByJPath(responseJson,  "/total");
				System.out.println("Value of total is--->"+totalValue);
				Assert.assertEquals(Integer.parseInt(totalValue), 12);
				
				//get the value from JSON array
				String lastName = Testutil.getValueByJPath(responseJson, "/data[0]/last_name");
				System.out.println(lastName);
				Assert.assertEquals(lastName, "Bluth");
				
				String id = Testutil.getValueByJPath(responseJson, "/data[0]/id");
				System.out.println(id);
				Assert.assertEquals(Integer.parseInt(id), 1);
				
				String avatar = Testutil.getValueByJPath(responseJson, "/data[0]/avatar");
				System.out.println(avatar);
				Assert.assertEquals(avatar, "https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
				
				String firstName = Testutil.getValueByJPath(responseJson, "/data[0]/first_name");
				System.out.println(firstName);
				Assert.assertEquals(firstName, "George");

				// c. All Headers
				Header[] headerArray = closeableHttpResponse.getAllHeaders();
		        HashMap<String, String> allHeaders = new HashMap<String, String>();

				for (Header header : headerArray) {
					allHeaders.put(header.getName(), header.getValue());

				}

				System.out.println("Headers Array--->" + allHeaders);

	}


}
