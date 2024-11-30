package com.aquagaslink.order_management.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features/order.feature",
        glue = "com.aquagaslink.order_management.bdd",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberTest {
}
