package com.drt2009.todo.cucumber;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@CucumberContextConfiguration
@SpringBootTest(classes = SpringTestConfig.class)
@ActiveProfiles("bdd")
public class StepDefinitions {

  //Mocks
  @MockBean
  private FlagsmithClient flagsmithClient;

  @SpyBean
  private TodoController todoController;

  private TodoItem todoItem;
  private ResponseEntity<TodoItem> todoResponse;

  @Given("I have a todo item to submit")
  public void iHaveATodoItemToSubmit() {
    todoItem = TodoItem.builder().description("Test Todo Item Cucumber").build();
  }
  @Given("the feature flag for {string} is turned on")
  public void theFeatureFlagIsTurnedOn(String featureName) throws Exception {
    Flags flags = Mockito.mock(Flags.class);
    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled(featureName)).thenReturn(true);
  }
  @When("I submit the item to the controller")
  public void iSubmitTheItemToTheController() throws Exception {
    todoResponse = todoController.createTodoItem(todoItem);
  }

  @Then("a {int} response is returned")
  public void a_response_is_returned(Integer statusCode) {
    assertEquals(todoResponse.getStatusCode(),HttpStatusCode.valueOf(statusCode));
  }
  @Then("a todo item is returned with an id")
  public void a_todo_item_is_returned() {
    assertNotNull(todoResponse.getBody().getId());
    assertEquals("Test Todo Item Cucumber",todoResponse.getBody().getDescription());
  }

}
