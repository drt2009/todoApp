package com.drt2009.todo.cucumber;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.drt2009.todo.controller.TodoController;
import com.drt2009.todo.pojo.TodoItem;
import com.flagsmith.FlagsmithClient;

import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import java.util.List;
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

  private ResponseEntity<TodoItem> todoResponse;
  private ResponseEntity<List<TodoItem>> todoListResponse;
  private TodoItem expectedTodoItem;

  @Given("I have a todo item to submit")
  public void iHaveATodoItemToSubmit() {
    TodoItem.builder().description("Test Todo Item Cucumber").build();
  }
  @Given("the feature flag for {string} is turned on")
  public void theFeatureFlagIsTurnedOn(String featureName) throws Exception {
    Flags flags = Mockito.mock(Flags.class);
    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled(featureName)).thenReturn(true);
  }
  @Given("There is a todo item already created")
  public void thereIsATodoItemAlreadyCreated() throws Exception {
    theFeatureFlagIsTurnedOn("create_todo_item");
    expectedTodoItem = iSubmitTheItemToTheController().getBody();

  }

  @When("I submit the item to the controller")
  public ResponseEntity<TodoItem> iSubmitTheItemToTheController() throws Exception {
    todoResponse = todoController.createTodoItem(TodoItem.builder().description("Test Todo Item Cucumber").build());
    return todoResponse;
  }
  @When("I request the todo item")
  public void iRequestTheTodoItem() throws Exception {
    todoResponse = todoController.getTodoItem(todoResponse.getBody().getId());
  }

  @When("I request to get all of the todo items")
  public void iRequestToGetAllOfTheTodoItems() throws Exception {
    todoListResponse =  todoController.getAllTodoItems();
  }

  @Then("a {int} response is returned")
  public void aResponseIsReturned(Integer statusCode) {
    assertEquals(todoResponse.getStatusCode(),HttpStatusCode.valueOf(statusCode));
  }
  @Then("a todo item is returned with an id")
  public void aTodoItemIsReturned() {
    assertNotNull(todoResponse.getBody().getId());
    assertEquals("Test Todo Item Cucumber",todoResponse.getBody().getDescription());
  }
  @Then("a todo item is returned with that id")
  public void aTodoItemIsReturnedWithThatId() {
    assertEquals(expectedTodoItem,todoResponse.getBody());
  }


  @Then("at least {int} todo items are returned")
  public void atleastTodoItemsAreReturned(Integer numberOfExpectedItems) {
    assertTrue(numberOfExpectedItems <= todoListResponse.getBody().size());
  }


  @Then("clean up after test")
  public void cleanUpAfterTest() {
    todoResponse = null;
    expectedTodoItem = null;
  }

}
