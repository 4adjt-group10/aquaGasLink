package com.aquagaslink.product.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features/product.feature",
        glue = "com.aquagaslink.product.bdd",
        plugin = {"pretty", "html:target/cucumber-reports"}
)
public class CucumberTest {
}
