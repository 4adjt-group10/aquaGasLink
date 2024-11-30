package com.aquagaslink.client.bdd;

import com.aquagaslink.client.controller.clientDTO.ClientDTOForm;
import com.aquagaslink.client.helper.ClientHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class StepDefinitionClient {

    private Response response;

    private ClientHelper helper = new ClientHelper();

    private ClientDTOForm clientValid;

    private final String ENDPOINT_API_REGISTER = "http://localhost:8082/api/client";
    private final String ENDPOINT_API_FIND_BY_ID = "http://localhost:8082/api/client/{id}";
    private final String ENDPOINT_API_LIST_ALL = "http://localhost:8082/api/client/all";
    private final String ENDPOINT_API_DELETE = "http://localhost:8082/api/client/{id}";
    private final String ENDPOINT_API_UPDATE = "http://localhost:8082/api/client/{id}";


    @Given("that I create a client with {string}")
    public void thatICreateAClientWith(String cpf) {
        var clientCreated = helper.createClient(cpf);
        clientValid = clientCreated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clientCreated)
                .when()
                .post(ENDPOINT_API_REGISTER);

    }
    @Then("the return must be {string}")
    public void the_return_must_be(String status) {
        response.then().statusCode(Integer.parseInt(status))
                .log().all();
    }


    @When("I look for a client by id")
    public void iLookForAClientById() {
        response = when().get(ENDPOINT_API_FIND_BY_ID, response.jsonPath().getString("id"));

    }

    @When("I look for all clients")
    public void iLookForAllClients() {
        response = when().get(ENDPOINT_API_LIST_ALL);

    }

    @When("I delete the client")
    public void iDeleteTheClient() {
        response = when().delete(ENDPOINT_API_DELETE, response.jsonPath().getString("id"));

    }

    @When("I update the client with {string}")
    public void iUpdateTheClientWith(String name) {
        var cpf = response.jsonPath().getString("cpf");
        var clientUpdated = helper.createClientData(cpf,name);
        clientValid = clientUpdated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(clientUpdated)
                .when()
                .put(ENDPOINT_API_UPDATE, response.jsonPath().getString("id"));
    }

}
