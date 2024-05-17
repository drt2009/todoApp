package com.drt2009.todo.cucumber;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.drt2009.todo.controller.TodoController;
import com.drt2009.todo.pojo.TodoItem;
import com.flagsmith.FlagsmithClient;

import com.flagsmith.models.Flags;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


@ExtendWith(MockitoExtension.class)
@CucumberContextConfiguration
@SpringBootTest(classes = SpringTestConfig.class)
public class StepDefinitions {

  //Mocks
  @Mock
  private FlagsmithClient flagsmithClient;

  @InjectMocks
  private TodoController todoController;

  private TodoItem todoItem;
  private ResponseEntity response;

  @Given("I have a todo item to submit")
  public void iHaveATodoItemToSubmit() {
    todoItem = TodoItem.builder().build();
  }
  @Given("the feature flag for {string} is turned on")
  public void theFeatureFlagIsTurnedOn(String featureName) throws Exception {
    Flags flags = Mockito.mock(Flags.class);
    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled(featureName)).thenReturn(true);
  }
  @When("I submit the item to the controller")
  public void iSubmitTheItemToTheController() throws Exception {
    response = todoController.createTodoItem(todoItem);
  }
  @Then("a created response is returned")
  public void aCreatedResponseIsReturned() {
    assertEquals(response.getStatusCode(),HttpStatusCode.valueOf(201));
  }

}
