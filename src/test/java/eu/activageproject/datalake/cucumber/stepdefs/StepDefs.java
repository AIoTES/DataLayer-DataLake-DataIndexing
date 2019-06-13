package eu.activageproject.datalake.cucumber.stepdefs;

import eu.activageproject.datalake.DataIntegrationApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = DataIntegrationApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
