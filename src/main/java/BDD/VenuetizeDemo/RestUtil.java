package BDD.VenuetizeDemo;

import java.io.FileReader;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestUtil {
	
	static String endPointURI;
	static int empId;
	
	//------------ Functional Decomposition style coding -------------------------	
	public static Response GetRequest(String baseURI)
	{
		RestAssured.baseURI = baseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.request(Method.GET, "/employees");
		
		return response;
	}
	
	public static Response get(String endPoint, Map<String, String> headers, Map<String, ?> queryParam){
        Response response=null;
        RequestSpecification httpRequest;
        try {
            RestAssured.baseURI = endPoint;
            if(endPoint.contains("https")) {
                httpRequest = RestAssured.given().relaxedHTTPSValidation();
                httpRequest.port(443);
            }
            else{
                httpRequest = RestAssured.given();
            }
            if(headers!=null) {
                Set<String> headerKeys = headers.keySet();
                for (String headerKey : headerKeys)
                    httpRequest.header(headerKey, headers.get(headerKey));
            }
            if(queryParam!=null)
                httpRequest.params(queryParam);

            response = httpRequest.get();           
            return response;
        }
        catch(Exception e){
            e.printStackTrace();
            return response;
        }
    }
	
	public static JSONObject messageAsSimpleJson(String messageFilePath){
        JSONObject jsonobj;
        try {
            JSONParser parser = new JSONParser();
            jsonobj = (JSONObject) parser.parse(new FileReader(messageFilePath));
            return jsonobj;
        }
        catch (Exception e){
            return null;
        }
    }
	
	
	 public static Response post(String endPoint, boolean urlencodedForm, String requestBody, Map<String, String> requestBodyAsMap, Map<String, String> headers) {
	        Response response = null;
	        RequestSpecification httpRequest;
	        try {
	            RestAssured.baseURI = endPoint;
	            httpRequest = RestAssured.given();
	            if (urlencodedForm) {
	                httpRequest.config(RestAssured.config()
	                        .encoderConfig(EncoderConfig.encoderConfig()
	                                .encodeContentTypeAs("x-www-form-urlencoded", ContentType.URLENC)));
	            }
	            if(endPoint.contains("https")) {
	                httpRequest.relaxedHTTPSValidation();
	                httpRequest.port(443);
	            }
	            if(headers!=null) {
	                Set<String> headerKeys = headers.keySet();
	                for (String headerKey : headerKeys)
	                    httpRequest.header(headerKey, headers.get(headerKey));
	            }
	            if(urlencodedForm && requestBodyAsMap!=null)
	                httpRequest.formParams(requestBodyAsMap);
	            else
	                httpRequest.body(requestBody);
	            response = httpRequest.post();

	            return response;
	        }
	        catch(Exception e){
	            e.printStackTrace();
	            return response;
	        }
	    }
	
	public static Response GetRequest(String baseURI, String empId)
	{
		RestAssured.baseURI = baseURI;
		RequestSpecification request = RestAssured.given(); 
		Response response = request.request(Method.GET, "/employee/"+empId);
		return response;
	}

	public static Response PostRequest(String baseURI)
	{
		RestAssured.baseURI = baseURI;
		RequestSpecification request = RestAssured.given();
		 
		JSONObject requestParams = new JSONObject();
		requestParams.put("employee_name", "Jane");
		requestParams.put("employee_salary", "65000");
		requestParams.put("employee_age", "45");
		requestParams.put("profile_image", "");

		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		
		request.body(requestParams.toJSONString());
		Response response = request.post("/create");
		return response;
	}
	
	public static Response PutRequest(String baseURI, int empId)
	{
		RestAssured.baseURI = baseURI;
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("employee_name", "Jane");
		requestParams.put("employee_salary", "4500");
		requestParams.put("employee_age", "35");
		requestParams.put("profile_image", "");
		
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");	
		
		request.body(requestParams.toJSONString());
		Response response = request.put("/update/"+ empId);
		return response;
	}
	
	
	public static Response DeletRequest(String baseURI, int empid)
	{
		RestAssured.baseURI = baseURI;
		RequestSpecification request = RestAssured.given(); 
		 
//		Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json"); 
		 
//		Delete the request and check the response
		Response response = request.delete("/delete/"+ empid); 
		return response;
	}
	
//========================================================================================================================	
//------------------------------------------------- Cucumber style coding ------------------------------------------------	
	public static void setEndPointURI(String baseURI)
	{
		endPointURI=baseURI; //empCode
	}
	
	public static void setEmpCode(int empCode)
	{
		empId=empCode; //empCode
	}
	
	public static Response GetRequest()
	{
		RestAssured.baseURI = endPointURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.request(Method.GET, "/employees");
		return response;
	}
	
	public static Response GetRequest(int empId)
	{
		RestAssured.baseURI = endPointURI;
		RequestSpecification request = RestAssured.given(); 
		Response response = request.request(Method.GET, "/employee/"+empId);
		return response;
	}
	
	public static Response DeletRequest(int empid)
	{
		RestAssured.baseURI = endPointURI;
		RequestSpecification request = RestAssured.given(); 
		request.header("Content-Type", "application/json"); 
		Response response = request.delete("/delete/"+ empid); 
		return response;
	}
	
	public static Response PostRequest(String strName, String strSalary, String strAge)
	{
		RestAssured.baseURI = endPointURI;
		RequestSpecification request = RestAssured.given();
		 
		JSONObject requestParams = new JSONObject();
		requestParams.put("employee_name", strName);
		requestParams.put("employee_salary", strSalary);
		requestParams.put("employee_age", strAge);
		requestParams.put("profile_image", "");

		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");
		
		request.body(requestParams.toJSONString());
		Response response = request.post("/create");
		return response;
	}
	
	public static Response PutRequest(int empId, String strName, String strSalary, String strAge)
	{
		RestAssured.baseURI = endPointURI;
		RequestSpecification request = RestAssured.given();

		JSONObject requestParams = new JSONObject();
		requestParams.put("employee_name", strName);
		requestParams.put("employee_salary", strSalary);
		requestParams.put("employee_age", strAge);
		requestParams.put("profile_image", "");
		
		// Add a header stating the Request body is a JSON
		request.header("Content-Type", "application/json");	
		
		request.body(requestParams.toJSONString());
		Response response = request.put("/update/"+ empId);
		return response;
	}
}
