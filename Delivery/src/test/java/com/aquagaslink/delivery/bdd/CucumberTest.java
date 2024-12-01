package com.aquagaslink.delivery.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features/delivery.feature",
        glue = "com.aquagaslink.delivery.bdd",
        plugin = {"html", "html:target/cucumber-reports"}
)
public class CucumberTest {
}
