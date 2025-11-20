package stepDefinitions;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class BaseSteps {
	@Before
	public void theAPIEndpointIsAvailable() {
		RestAssured.baseURI = "https://automationexercise.com/api";
	}
}
