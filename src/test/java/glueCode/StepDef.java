package glueCode;

import BDD.VenuetizeDemo.CompleteOrder;
import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.response.Response;

public class StepDef extends CompleteOrder{
	
	Response response;

	@Given("^URI for API Endpoint \"([^\"]*)\"$")
	public void uri_for_API_Endpoint(String arg1) throws Throwable {		
		initializeURI();
	   
	}
	@Given("^I have the request headers defined as follows$")
	public void i_have_the_request_headers_defined_as_follows(DataTable arg1) throws Throwable {
		addHeaders();
	}

	
	@Then("^I verify the response status code as \"([^\"]*)\"$")
	public void i_verify_the_response_status_code_as(String arg1) throws Throwable {
		//verifyStatusCode(Integer.parseInt(arg1));	    
	}

	
	@When("^I send a GET request to get token details$")
	public void i_send_a_GET_request_to_get_token_details() throws Throwable {		
		getTokenDetails();
	}

	

	@When("^I send a GET request top get vendor products$")
	public void i_send_a_GET_request_top_get_vendor_products() throws Throwable {
		getVendorProducts();
	}

	@When("^I send a POST request with cart details$")
	public void i_send_a_POST_request_with_cart_details() throws Throwable {
	    createCart();
	}

	@When("^I send a GET request to fetch user wallet$")
	public void i_send_a_GET_request_to_fetch_user_wallet() throws Throwable {
		fetchUserWallet();
	}

	@When("^I send a GET request to get checck out information$")
	public void i_send_a_GET_request_to_get_checck_out_information() throws Throwable {		
		checkoutInfo();
	}

	

	@When("^I send a POST request to complete order$")
	public void i_send_a_POST_request_to_complete_order() throws Throwable {
		placeOrderV2();	 
	}



	

		
}
