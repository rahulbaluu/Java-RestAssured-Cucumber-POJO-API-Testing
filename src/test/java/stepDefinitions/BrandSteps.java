package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.Brand;
import pojo.BrandResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class BrandSteps {
	private Response response;
	private BrandResponse brandResponse;

	@Given("Send GET request to brand list")
	public void sendGETRequestToBrandList()
	{
		response = RestAssured.given().when().get("/brandsList").then().extract().response();
	}

	@When("Should receive the list of all brand")
		public void shouldReceiveTheListOfAllBrands() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		// Deserialize the JSON response into ProductsResponse
		brandResponse = objectMapper.readValue(response.getBody().asString(), BrandResponse.class);

		// Check that the list itself is not null
		assertNotNull(brandResponse.getBrands().toString(),"Brand list should not be null" );

		//Print total number of products
		System.out.println("Total products: " + brandResponse.getBrands().size() + "\n");

		for (Brand brand : brandResponse.getBrands()) {
			System.out.println("ID:" + brand.getId() + "\n" +
					"Brand:" + brand.getBrand());
			}
		}

	@Then("The status code should 200")
	public void theStatusCodeShould() {
		assertEquals(response.getStatusCode(), 200);
		System.out.println("Status code is " + response.getStatusCode());
	}

	@Given("Send PUT request to brand list")
	public void sendPUTRequestToBrandList() {
		response = RestAssured.given().when().put("/brandsList").then().extract().response();
	}

	@When("Should receive the error message")
	public void shouldReceiveTheErrorMessage() {
		assertEquals(response.getStatusCode(), 200);
		System.out.println("Status code is " + response.getStatusCode());
	}

	@Then("The status message for PUT brand is received")
	public void theStatusMessageForPUTBrandIsReceived() {
		// Extract message from JSON
		String actualMessage = response.jsonPath().getString("message");
		// Validate
		Assert.assertEquals(
				actualMessage,
				"This request method is not supported.",
				"Incorrect error message returned"
		);
		System.out.println("Status message is " + actualMessage);
	}
}
