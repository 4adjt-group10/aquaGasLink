package com.aquagaslink.order_management.bdd;

import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class StepDefinitionOrder {

    private Response response;
    private Response responseClient;

    private ClientOrderHelper helper = new ClientOrderHelper();

    private OrderFormDto orderValid;

    private final String ENDPOINT_API_REGISTER = "http://localhost:8080/order/create";
    private final String ENDPOINT_API_FIND_BY_ID = "http://localhost:8081/product/find-id/{id}";


    @Given("that I create a order with {string}")
    public void thatICreateAOrderWith(String code) {
        var orderCreated = helper.createOrderWithProduct(code);
        orderValid = orderCreated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderCreated)
                .when()
                .post(ENDPOINT_API_REGISTER);

        System.out.println(response.statusCode());
    }
    @Then("the return must be {string}")
    public void the_return_must_be(String status) {
        response.then().statusCode(Integer.parseInt(status))
                .log().all();
    }

    @When("I look for a order by id")
    public void iLookForAOrderById() {
        response = when().get(ENDPOINT_API_FIND_BY_ID, response.jsonPath().getString("id"));

    }
}
