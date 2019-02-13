package eu.activageproject.datalake.cucumber.stepdefs;

import eu.activageproject.datalake.DataIntergrationApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = DataIntergrationApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
