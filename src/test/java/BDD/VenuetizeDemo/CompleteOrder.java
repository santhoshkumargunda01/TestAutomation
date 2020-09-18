package BDD.VenuetizeDemo;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class CompleteOrder {	

	JSONParser parser = new JSONParser();

	String baseURI = "";

	Map<String, String>  headers = new HashMap<String, String>();
	String accessToken="";
	String identityGuid="";
	String cardId = "";
	String cartId = "";


	public void initializeURI() {
		baseURI = "http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/initiate";		
	}

	public void addHeaders() {
		headers.put("AUTHORIZATION","Bearer eyJraWQiOiJ0VDVzU0ZlZ2g3Y2xRZzhiWFJGbFdQYy1QUlAxeXFtU0hfRXVnejNlNUJRIiwiYWxnIjoiUlMyNTYifQ.eyJ2ZXIiOjEsImp0aSI6IkFULnNaYVNhMVVOck5ndXhnVU50X3BMN2czdlpfUHJNMEtKeVJiZllRU2lwUFkiLCJpc3MiOiJodHRwczovL2lkcy5tbGIuY29tL29hdXRoMi9hdXMxbTA4OHlLMDdub0JmaDM1NiIsImF1ZCI6ImFwaTovL21sYl9kZWZhdWx0IiwiaWF0IjoxNjAwNDA0NzAyLCJleHAiOjE2MDA0OTExMDIsImNpZCI6IjBvYXA3d2E4NTdqY3ZQbFo1MzU1IiwidWlkIjoiMDB1M2dnZjZ5Z2V5QnJ0RkgzNTciLCJzY3AiOlsiZW1haWwiLCJvcGVuaWQiXSwic3ViIjoic2F0aXNocmF5c0BnbWFpbC5jb20iLCJpcGlkIjoiIiwibWxiX2VudGl0bGVtZW50Ijp0cnVlLCJndWlkIjoiIn0.Cn_Z-fMtJnzV5U4mJd-_pH5lfKY8kUvwJh20gZH4CFcrxsOp5VvLLNJjy5VpFuaoARI3hid307R8YkNz9OhZWNoPkqvuwtMJIAVGn_JBvv1oWwcQaXz6Kde3zj18H-nPwfRpYe6vzZXrPQ7gyZfxBaOJhhlJkTKTzooOK3o9UyFfOpVZ_x2Y22c8Ml5YYjgi6Bu87kl_K120eUmeQjO48Z0P-1QZWTF6qZcIIT2c5sM9EnSMDzFG6-aTqDeFiS0GNGVaohdlM8V8iKvyLMYfm2RTXr0Dr4VbIuH0SwXbd5Gh8X2oDemjRMPDXliTMwVm-5eW1D93Zto8pFJPBW0NxA");
	}



	public void getTokenDetails()
	{   
		System.out.println("Getting Token Detials===============>");

		baseURI = "http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/initiate";

		//headers.put("Host","<calculated when request is sent>");
		Map<String, String>  queryParam = new HashMap<String, String>();
		queryParam.put("module", "expressOrder"); 

		Response response = RestUtil.get(baseURI, headers, queryParam); 
		Map cookies = response.getCookies();
		accessToken = cookies.get("vz-access-token").toString();
		identityGuid = cookies.get("vz-identity-guid").toString();
		System.out.println(accessToken+identityGuid);


		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);

		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");	
	}

	@Test(priority = 1)
	public void getVendorProducts() throws ParseException
	{   
		baseURI = "http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/2066/products/v1";
		System.out.println("Getting Vendor_Products===============>");
		Map<String, String>  headers = new HashMap<String, String>();
		JSONParser parser = new JSONParser();

		Response response = RestUtil.get(baseURI, headers,null); 			 


		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);

		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");	
		String body = response.getBody().asString();
		JSONObject jsonObject=(JSONObject)parser.parse(body);
		JSONArray jsonArray = (JSONArray)jsonObject.get("products");
		JSONObject json = (JSONObject)jsonArray.get(0);
		System.out.println(json.get("productId"));


	}

	@Test(priority = 2)
	public void createCart() throws ParseException
	{   
		System.out.println("createCart===============>");

		baseURI = "http://pwa-stg.venuetize.com/PWA/Tbrst34sba/22/shoppingcart/v2";

		Map<String, String>  header = new HashMap<String, String>();
		header.put("Content-Type","application/json");
		Map<String, String>  queryParam = new HashMap<String, String>();
		queryParam.put("module", "externalSeatId=7045"); 

		JSONObject jsobj = RestUtil.messageAsSimpleJson("/Users/satishkadiyala/Desktop/Venuetize/Pyramid Code/qa_automation_framework_pyramid/VenuetizeDemo/resources/cart.json");

		Response response = RestUtil.post(baseURI, false, jsobj.toString(),null, header);


		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);

		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode /*actual value*/, 201 /*expected value*/, "Correct status code returned");	
		JSONObject jsonObject=(JSONObject)parser.parse(response.getBody().asString());
		cartId= jsonObject.get("cartId").toString();
		System.out.println(cartId);

	}

	@Test(priority = 3)
	public void fetchUserWallet() throws ParseException
	{   
		System.out.println("fetchUserWallet===============>");

		JSONParser parser = new JSONParser();

		baseURI = "http://pwa-stg.venuetize.com/PWA/Tbrst34sba/22/userbyig/"+identityGuid+"/wallet/v2";


		Map<String, String>  header1 = new HashMap<String, String>();
		header1.put("vz-access-token",accessToken);


		Response response = RestUtil.get(baseURI, header1, null); 



		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);

		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");	
		String body = response.getBody().asString();
		JSONObject jsonObject=(JSONObject)parser.parse(body);
		JSONObject jsonwallet = (JSONObject)jsonObject.get("wallet");
		JSONObject creditCards = (JSONObject)jsonwallet.get("creditCards");
		JSONArray jsonArray = (JSONArray)creditCards.get("items");
		JSONObject json = (JSONObject)jsonArray.get(0);
		cardId = json.get("id").toString();
		System.out.println(json.get("id"));  

	}

	@Test(priority = 4)
	public void checkoutInfo() throws ParseException
	{   
		System.out.println("checkoutInfo===============>");

		JSONParser parser = new JSONParser();

		baseURI = "http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/userbyig/"+identityGuid+"/shoppingcart/"+cartId+"/checkoutinfo/v1";


		Map<String, String>  header1 = new HashMap<String, String>();
		header1.put("vz-access-token",accessToken);


		Response response = RestUtil.get(baseURI, header1, null); 



		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);

		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");	
		String body = response.getBody().asString();

	}


	@Test(priority = 5)
	public void placeOrderV2()
	{   
		System.out.println("placeOrderV2===============>");

		baseURI = "http://pwa-stg-1.venuetize.com:8080/PWA/Tbrst34sba/22/userbyig/"+identityGuid+"/shoppingcart/"+cartId+"/order/v2";

		Map<String, String>  header = new HashMap<String, String>();
		header.put("Content-Type","application/json");

		header.put("vz-access-token",accessToken);


		JSONObject jsobj = RestUtil.messageAsSimpleJson("/Users/satishkadiyala/Desktop/Venuetize/Pyramid Code/qa_automation_framework_pyramid/VenuetizeDemo/resources/orderV2.json");

		Response response = RestUtil.post(baseURI, false, jsobj.toString(),null, header);


		int statusCode = response.getStatusCode();
		System.out.println("The status code recieved: " + statusCode);

		// Assert that correct status code is returned.
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");	
	}
	
	
	public void verifyStatusCode(int statusCode, Response res) {
		
		Assert.assertEquals(statusCode /*actual value*/, 200 /*expected value*/, "Correct status code returned");
	}
	
}  

