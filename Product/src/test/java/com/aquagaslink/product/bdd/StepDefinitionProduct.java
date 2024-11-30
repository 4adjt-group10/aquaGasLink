package com.aquagaslink.product.bdd;

import com.aquagaslink.product.controller.dto.ProductCadasterDto;
import com.aquagaslink.product.controller.dto.ProductFormDto;
import com.aquagaslink.product.helper.ProductHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.TypeReference;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;

public class StepDefinitionProduct {

    private Response response;

    private ProductHelper helper = new ProductHelper();

    private ProductFormDto productValid;

    private final String ENDPOINT_API_REGISTER = "http://localhost:8081/product/register";
    private final String ENDPOINT_API_REGISTER_PRODUCTS = "http://localhost:8081/product/register-products";
    private final String ENDPOINT_API_FIND_BY_ID = "http://localhost:8081/product/find-id/{id}";
    private final String ENDPOINT_API_FIND_BY_NAME = "http://localhost:8081/product/find-name/{name}";
    private final String ENDPOINT_API_FIND_BY_PRODUCT_CODE = "http://localhost:8081/product/find-product/{productcode}";
    private final String ENDPOINT_API_LIST_ALL = "http://localhost:8081/product/find-all";
    private final String ENDPOINT_API_DELETE = "http://localhost:8081/product/{id}";

    @Given("that I create some products with {string} and {string}")
    public void thatICreateSomeProductsWithAnd(String name, String code)  {
        var productCreated = helper.createProductDtoWith(name, code);
        productValid = productCreated;
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(productCreated)
                .when()
                .post(ENDPOINT_API_REGISTER);

        System.out.println(response.statusCode());
    }
    @Then("the return must be {string}")
    public void the_return_must_be(String status) {
        response.then().statusCode(Integer.parseInt(status))
                .log().all();
    }

    @Given("that I create some products with {string} and {string} and {string} and {string}")
    public void thatICreateSomeProductsWithAndAndAnd(String product1, String code1, String product2, String code2) {

        List<ProductFormDto> list = new ArrayList<>();
        list.add(helper.createProductDtoWith(product1,code1));
        list.add(helper.createProductDtoWith(product2,code2));
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(list)
                .when()
                .post(ENDPOINT_API_REGISTER_PRODUCTS);

        System.out.println(response.statusCode());
    }


    @When("I look for a product by id")
    public void iLookForAProductById() {
        response = when().get(ENDPOINT_API_FIND_BY_ID, response.jsonPath().getString("id"));

    }

    @When("I look for a product by name")
    public void iLookForAProductByName() {
        response = when().get(ENDPOINT_API_FIND_BY_NAME, response.jsonPath().getString("name"));

    }

    @When("I look for a product by productCode")
    public void iLookForAProductByProductCode() {
        response = when().get(ENDPOINT_API_FIND_BY_PRODUCT_CODE, response.jsonPath().getString("productCode"));

    }

    @When("I list all products")
    public void iListAllProducts() {
        response = when().get(ENDPOINT_API_LIST_ALL);

    }

    @When("I delete the product")
    public void iDeleteTheProduct() {
        response = when().delete(ENDPOINT_API_DELETE, response.jsonPath().getString("id"));
    }
}
