package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.Product;
import pojo.ProductResponse;

import static org.testng.Assert.assertEquals;
import static org.testng.AssertJUnit.assertNotNull;

public class ProductSteps {
	private Response response;
	private ProductResponse productResponse;

	@Given("Send GET request to products list")
	public void sendGETRequestToProductsList() {
		response = RestAssured.given().when().get("/productsList").then().extract().response();
	}

	@When("Should receive the list of all products")
	public void shouldReceiveTheListOfAllProducts() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		// Deserialize the JSON response into ProductsResponse
		productResponse = objectMapper.readValue(response.getBody().asString(), ProductResponse.class);

		// Check that the list itself is not null
		assertNotNull(productResponse.getProducts().toString(), "Products list should not be null");

		//Print total number of products
		System.out.println("Total products: " + productResponse.getProducts().size() + "\n");

		for (Product product : productResponse.getProducts()) {
			System.out.println("ID:" + product.getId() + "\n" +
					"Name:" + product.getName() + "\n" +
					"Price:" + product.getPrice() + "\n" +
					"Brand:" + product.getBrand());
			if (product.getCategory() != null) {
				System.out.println("Category:" + product.getCategory().getCategory());
				if (product.getCategory().getUsertype() != null) {
					System.out.println("UserType:" + product.getCategory().getUsertype().getUsertype() + "\n");
				}
			}
		}
	}

	@Then("The status code should be 200")
	public void theStatusCodeShouldBe200() {
		assertEquals(response.getStatusCode(), 200);
		System.out.println("Status code is " + response.getStatusCode());
	}

	@Given("Send the POST to products list")
	public void sendThePOSTToProductsList() {
		response = RestAssured.given().when().post("/productsList").then().extract().response();
	}

	@Then("The status message we received")
	public void theStatusMessageWeReceived() {
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

	@Given("Send the POST to Search product with keyword {string}")
	public void sendThePOSTToSearchProductWithKeyword(String dress) {
		response = RestAssured.given().formParam("search_product", dress).when().
				post("/searchProduct").then().extract().response();
	}

	@When("Should receive the list of searched products")
	public void shouldReceiveTheListOfSearchedProducts() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();

		productResponse = objectMapper.readValue(response.getBody().asString(), ProductResponse.class);

		if (productResponse.getProducts() == null || productResponse.getProducts().isEmpty()) {
			System.out.println("No products found for the keyword in searchbar");
		} else {
			System.out.println("Total products: " + productResponse.getProducts().size() + "\n");

			for (Product product : productResponse.getProducts()) {
				System.out.println("ID:" + product.getId() + "\n" + product.getName() + "\n" + product.getPrice() + "\n" + product.getBrand());
			}
		}
	}

	@Given("Send the POST request without search parameter")
	public void sendThePOSTRequestWithoutSearchParameter() {
		response = RestAssured.given().contentType("multipart/form-data").post("/searchProduct").then().extract().response();
	}

	@When("Should receive the error message for missing search parameter")
	public void shouldReceiveTheErrorMessageForMissingSearchParameter() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		productResponse =
				mapper.readValue(response.getBody().asString(), ProductResponse.class);

		System.out.println("API responseCode: " + productResponse.getResponseCode());
		System.out.println("API error message:" + productResponse.getMessage());

		Assert.assertEquals(productResponse.getMessage(),
				"Bad request, search_product parameter is missing in POST request.");

	}

	@Then("The status code should be 400")
	public void theStatusCodeShouldBe() {
		// Assert API responseCode = 400
		Assert.assertEquals(productResponse.getResponseCode(),
				400, "Expected responseCode 400");
		System.out.println("Status code is " + response.getStatusCode());
	}
}