package com.aquagaslink.delivery.bdd;

import com.aquagaslink.delivery.controller.dto.DeliveryPersonForm;
import com.aquagaslink.delivery.helper.DeliveryPersonHelper;
import com.aquagaslink.delivery.model.DeliveryPersonStatus;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.MediaType;
import org.springframework.integration.util.UUIDConverter;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class StepDefinitionDelivery {

    private Response response;
    private Response responseUpdated;


    private DeliveryPersonHelper helper = new DeliveryPersonHelper();

    private DeliveryPersonForm deliveryValid;

    private final String ENDPOINT_API_REGISTER = "http://localhost:8086/delivery-person/create";
    private final String ENDPOINT_API_DELETE = "http://localhost:8086/delivery-person/delete/";
    private final String ENDPOINT_API_FIND_BY_ID = "http://localhost:8086/delivery-person/{id}";
    private final String ENDPOINT_API_LIST_ALL = "http://localhost:8086/delivery-person/get-all";
    private final String ENDPOINT_API_READ_STATUS = "http://localhost:8086/delivery-person/get-all-by-status";
    private final String ENDPOINT_API_UPDATE_DELIVERY = "http://localhost:8086/delivery/update-status/{deliveryId}";


    @Given("that I create a delivery person with {string}, {string}, {string} and {string}")
    public void thatICreateADeliveryPersonWithAnd(String name, String email, String phone, String plate) {
        var deliveryCreated = helper.createDeliveryPerson(name, email, phone, plate);
        deliveryValid = deliveryCreated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(deliveryCreated)
                .when()
                .post(ENDPOINT_API_REGISTER);

        response.then().log().all();
    }
    @Then("the return must be {string}")
    public void the_return_must_be(String status) {
        response.then().statusCode(Integer.parseInt(status))
                .log().all();
    }


    @When("I delete the delivery person")
    public void iDeleteTheDeliveryPerson() {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post(ENDPOINT_API_DELETE+response.jsonPath().getString("id"));

    }

    @When("I read the delivery person by id")
    public void iReadTheDeliveryPersonById() {
        response = when().get(ENDPOINT_API_FIND_BY_ID, response.jsonPath().getString("id"));

    }

    @When("I read all deliveries person")
    public void iReadAllDeliveriesPerson() {
        response = when().get(ENDPOINT_API_LIST_ALL);
    }


    @When("I read all deliveries person by {string}")
    public void iReadAllDeliveriesPersonBy(String status) {
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status", status)
                .when()
                .get(ENDPOINT_API_READ_STATUS);

    }


    @When("I update the delivery {string}")
    public void iUpdateTheDelivery(String status) {
        //var delivery = response.jsonPath().getString("id");
//        var orderUpdated = helper.createOrderData(quantity,price, UUIDConverter.getUUID(response.jsonPath().getString("clientId")));
//        orderValid = orderUpdated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("status",status)
                .when()
                .put(ENDPOINT_API_UPDATE_DELIVERY, response.jsonPath().getString("id"));
    }

    @When("I get the tracking by clientId")
    public void iGetTheTrackingByClientId() {
    }
}
