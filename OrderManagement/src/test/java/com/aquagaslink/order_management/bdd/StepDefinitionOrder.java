package com.aquagaslink.order_management.bdd;

import com.aquagaslink.order_management.controller.dto.OrderFormDto;
import com.aquagaslink.order_management.helper.ClientOrderHelper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import org.springframework.integration.util.UUIDConverter;

import java.math.BigDecimal;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class StepDefinitionOrder {

    private Response response;
    private Response responseClient;

    private ClientOrderHelper helper = new ClientOrderHelper();

    private OrderFormDto orderValid;

    private final String ENDPOINT_API_REGISTER = "http://localhost:8080/order/create";
    private final String ENDPOINT_API_FIND_BY_ID = "http://localhost:8080/order/search/{id}";
    private final String ENDPOINT_API_LIST_ALL = "http://localhost:8080/order/list-all/{clientId}";
    private final String ENDPOINT_API_UPDATE = "http://localhost:8080/order/update/{id}";
    private final String ENDPOINT_API_UPDATE_STATUS = "http://localhost:8080/order/update-status/{id}";


    @Given("that I create a order with {string}")
    public void thatICreateAOrderWith(String code) {
        var orderCreated = helper.createOrderWithProduct(code);
        orderValid = orderCreated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderCreated)
                .when()
                .post(ENDPOINT_API_REGISTER);
        response.then().log().all();
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

    @When("I list all orders by client id")
    public void iListAllOrdersByClientId() {
        response = when().get(ENDPOINT_API_LIST_ALL, response.jsonPath().getString("clientId"));
    }

    @When("I update the order with {int} and {bigdecimal}")
    public void i_update_the_order_with_and(Integer quantity, BigDecimal price) {
        var order = response.jsonPath().getString("id");
        var orderUpdated = helper.createOrderData(quantity,price, UUIDConverter.getUUID(response.jsonPath().getString("clientId")));
        orderValid = orderUpdated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(orderUpdated)
                .when()
                .put(ENDPOINT_API_UPDATE, response.jsonPath().getString("id"));
    }


    @When("I update the order {string}")
    public void iUpdateTheOrder(String status) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status", status)
                .when()
                .patch(ENDPOINT_API_UPDATE_STATUS, response.jsonPath().getString("id"));

    }
}
