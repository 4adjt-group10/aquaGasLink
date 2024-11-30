package com.aquagaslink.client.bdd;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features="src/test/resources/features/client.feature",
        glue = "com.aquagaslink.client.bdd",
        plugin = {"html", "html:target/cucumber-reports"}
)
public class CucumberTest {
}
