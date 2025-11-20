package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.LoginResponse;


public class LoginSteps {
	private Response response;
	private LoginResponse loginResponse;

	@Given("Send the post request with the {string} and {string} parameters")
	public void sendThePostRequest(String email, String password) {

		response = RestAssured.given().contentType("application/x-www-form-urlencoded").
				formParam("email", email).formParam("password", password).
				post("/verifyLogin").then().extract().response();
		System.out.println("Response Body: " + response.getBody().asString());
	}

	@When("Should receive this {string} and {int}")
	public void shouldReceiveThis(String Message, int value) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		loginResponse = mapper.readValue(response.getBody().asString(), LoginResponse.class);

		System.out.println("API Response Code" + loginResponse.getResponseCode());
		System.out.println("API Response Message" + loginResponse.getMessage());

		// Validate response
		Assert.assertEquals(loginResponse.getResponseCode(), value);
		Assert.assertEquals(loginResponse.getMessage(), Message);
	}

	@Then("Verify the response code is 200")
	public void verifyTheResponseCodeIs200() {
		Assert.assertEquals(response.getStatusCode(), 200, "Response code is not 200");
		System.out.println("HTTP Status Code: " + response.getStatusCode());
	}

	@Given("Send the post request with the {string} parameters")
	public void sendThePostRequestWithTheParameters(String password) {
		response = RestAssured.given().contentType("application/x-www-form-urlencoded").
				formParam("password", password).post("/verifyLogin").then().extract().response();
		System.out.println("Response Body: " + response.getBody().asString());
	}

	@When("Should receive this error {string} and {int}")
	public void shouldReceiveThisError(String Message, int value) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		loginResponse = mapper.readValue(response.getBody().asString(), LoginResponse.class);
		System.out.println("API Response Code " + loginResponse.getResponseCode());
		System.out.println("API Response Message " + loginResponse.getMessage());
		// Validate response
		Assert.assertEquals(loginResponse.getResponseCode(), value);
		Assert.assertEquals(loginResponse.getMessage(), Message);
	}

	@Given("Send the delete request to delete the user account")
	public void sendTheDeleteTheUserAccount() {
		response = RestAssured.given().contentType("application/json").when().
				delete("/verifyLogin").then().extract().response();
		System.out.println("Response Body: " + response.getBody().asString());
	}
}
