package stepDefinitions;

import Utility.Excelutils;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.UserAccount;
import pojo.UserAccountResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class UserAccountSteps {
	UserAccount reg;
	UserAccountResponse regResponse;
	Response response;

	@Given("Read the user registration data from")
	public void readTheUserRegistrationDataFromSheetRow(DataTable table) throws IOException {
		List<Map<String, String>> data = table.asMaps(String.class, String.class);
		Map<String, String> row = data.get(0);

		String sheetName = row.get("Sheet_name");
		int rowNum = Integer.parseInt(row.get("rowNum"));
		reg = new UserAccount();

		reg.name = Excelutils.getCellData(sheetName, rowNum, 0);
		reg.email = Excelutils.getCellData(sheetName, rowNum, 1);
		reg.password = Excelutils.getCellData(sheetName, rowNum, 2);
		reg.title = Excelutils.getCellData(sheetName, rowNum, 3);
		reg.birth_date = Excelutils.getCellData(sheetName, rowNum, 4);
		reg.birth_month = Excelutils.getCellData(sheetName, rowNum, 5);
		reg.birth_year = Excelutils.getCellData(sheetName, rowNum, 6);
		reg.firstname = Excelutils.getCellData(sheetName, rowNum, 7);
		reg.lastname = Excelutils.getCellData(sheetName, rowNum, 8);
		reg.company = Excelutils.getCellData(sheetName, rowNum, 9);
		reg.address1 = Excelutils.getCellData(sheetName, rowNum, 10);
		reg.address2 = Excelutils.getCellData(sheetName, rowNum, 11);
		reg.country = Excelutils.getCellData(sheetName, rowNum, 12);
		reg.zipcode = Excelutils.getCellData(sheetName, rowNum, 13);
		reg.state = Excelutils.getCellData(sheetName, rowNum, 14);
		reg.city = Excelutils.getCellData(sheetName, rowNum, 15);
		reg.mobile_number = Excelutils.getCellData(sheetName, rowNum, 16);

	}

	@When("Send post request to create account")
	public void sendPostRequestToCreateAccount() throws IOException {
		response = RestAssured.given().contentType("application/x-www-form-urlencoded").
				formParam("name", reg.name).
				formParam("email", reg.email).
				formParam("password", reg.password).
				formParam("title", reg.title).
				formParam("birth_date", reg.birth_date).
				formParam("birth_month", reg.birth_month).
				formParam("birth_year", reg.birth_year).formParam("firstname", reg.firstname)
				.formParam("lastname", reg.lastname)
				.formParam("company", reg.company)
				.formParam("address1", reg.address1)
				.formParam("address2", reg.address2)
				.formParam("country", reg.country)
				.formParam("zipcode", reg.zipcode)
				.formParam("state", reg.state)
				.formParam("city", reg.city)
				.formParam("mobile_number", reg.mobile_number)
				.post("/createAccount");
	}

	@Then("Verify user is created successfully")
	public void verifyUserIsCreatedSuccessfully() {
		int responseCode = response.jsonPath().getInt("responseCode");
		String message = response.jsonPath().getString("message");

		System.out.println("Status message is " + message);

		Assert.assertEquals(responseCode, 201, "Failed to create user");

	}

	@Given("Read the user credentials from")
	public void readTheUserCredentialsFrom(DataTable table) throws IOException {
		List<Map<String, String>> data = table.asMaps(String.class, String.class);
		Map<String, String> row = data.get(0);

		String sheetName = row.get("Sheet_name");
		int rowNum = Integer.parseInt(row.get("rowNum"));
		reg = new UserAccount();

		reg.name = Excelutils.getCellData(sheetName, rowNum, 0);
		reg.email = Excelutils.getCellData(sheetName, rowNum, 1);
		reg.password = Excelutils.getCellData(sheetName, rowNum, 2);
		reg.title = Excelutils.getCellData(sheetName, rowNum, 3);
		reg.birth_date = Excelutils.getCellData(sheetName, rowNum, 4);
		reg.birth_month = Excelutils.getCellData(sheetName, rowNum, 5);
		reg.birth_year = Excelutils.getCellData(sheetName, rowNum, 6);
		reg.firstname = Excelutils.getCellData(sheetName, rowNum, 7);
		reg.lastname = Excelutils.getCellData(sheetName, rowNum, 8);
		reg.company = Excelutils.getCellData(sheetName, rowNum, 9);
		reg.address1 = Excelutils.getCellData(sheetName, rowNum, 10);
		reg.address2 = Excelutils.getCellData(sheetName, rowNum, 11);
		reg.country = Excelutils.getCellData(sheetName, rowNum, 12);
		reg.zipcode = Excelutils.getCellData(sheetName, rowNum, 13);
		reg.state = Excelutils.getCellData(sheetName, rowNum, 14);
		reg.city = Excelutils.getCellData(sheetName, rowNum, 15);
		reg.mobile_number = Excelutils.getCellData(sheetName, rowNum, 16);
	}

	@When("Send delete request to delete account")
	public void sendDeleteRequestToDeleteAccount() {
		response = RestAssured.given().contentType("application/x-www-form-urlencoded")
				.formParam("email", reg.email).formParam("password", reg.password).delete("/deleteAccount");
	}

	@Then("Verify account is deleted successfully")
	public void verifyAccountIsDeletedSuccessfully() {
		int responseCode = response.jsonPath().getInt("responseCode");
		String actualMessage = response.jsonPath().getString("message");

		System.out.println("API responseCode = " + responseCode);
		System.out.println("Status message is " + actualMessage);

		Assert.assertEquals(responseCode, 200, "Failed to delete account");
	}

	@When("Send put request to update account")
	public void sendPutRequestToUpdateAccount() {
		response = RestAssured.given().contentType("application/x-www-form-urlencoded").
				formParam("name", reg.name).
				formParam("email", reg.email).
				formParam("password", reg.password).
				formParam("title", reg.title).
				formParam("birth_date", reg.birth_date).
				formParam("birth_month", reg.birth_month).
				formParam("birth_year", reg.birth_year).
				formParam("firstname", reg.firstname).
				formParam("lastname", reg.lastname).
				formParam("company", reg.company).
				formParam("address1", reg.address1).
				formParam("address2", reg.address2).
				formParam("country", reg.country).
				formParam("zipcode", reg.zipcode).
				formParam("state", reg.state).
				formParam("city", reg.city).
				formParam("mobile_number", reg.mobile_number).
				put("/updateAccount");
		System.out.println("Updating account for: " + reg.email);
	}

	@Then("Verify account is updated successfully")
	public void verifyAccountIsUpdatedSuccessfully() {
		int responseCode = response.jsonPath().getInt("responseCode");
		String actualMessage = response.jsonPath().getString("message");

		System.out.println("API responseCode = " + responseCode);
		System.out.println("Status message is " + actualMessage);

		Assert.assertEquals(responseCode, 200, "Failed to update user account");
	}

	@When("Send GET request to get user detail")
	public void sendGETRequestToGetUserDetail() {
		response = RestAssured.given()
				.header("Content-Type", "application/json")
				.queryParam("email", reg.email)
				.get("getUserDetailByEmail");
		System.out.println("Response status code: " + response.getStatusCode());
		System.out.println("Response body: " + response.getBody().asString());
	}

	@Then("Verify user detail is retrieved successfully")
	public void verifyUserDetailIsRetrievedSuccessfully() {
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200, "Failed to retrieve user detail");
	}
}