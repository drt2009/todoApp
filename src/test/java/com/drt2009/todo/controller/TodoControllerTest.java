package com.drt2009.todo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.service.TodoService;
import com.flagsmith.FlagsmithClient;
import com.flagsmith.models.Flags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class TodoControllerTest {

  @Mock
  private FlagsmithClient flagsmithClient;
  @Mock
  private TodoService todoService;

  @InjectMocks
  private TodoController todoController;

  @Mock
  private Flags flags;


  @Test
  void createTodoItem_Success() throws Exception {
    TodoItem input = TodoItem.builder().description("testDescription").build();
    TodoItem expectedTodo = TodoItem.builder().description("testDescription").id(1).build();

    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled("create_todo_item")).thenReturn(true);
    when(todoService.createTodoItem(input)).thenReturn(expectedTodo);

    ResponseEntity<TodoItem> actualResponse = todoController.createTodoItem(input);

    assertEquals( HttpStatusCode.valueOf(200), actualResponse.getStatusCode());
    assertEquals(expectedTodo,actualResponse.getBody());
  }

  @Test
  void createTodoItem_FeatureTurnedOff() throws Exception {
    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled("create_todo_item")).thenReturn(false);

    ResponseEntity<TodoItem> actualResponse = todoController.createTodoItem(
        TodoItem.builder().build());

    assertEquals( HttpStatusCode.valueOf(404),actualResponse.getStatusCode());
  }

  @Test
  void getTodoItem_Success() throws Exception {
    TodoItem expected = TodoItem.builder().description("testDescription").id(1).build();

    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled("get_todo_item")).thenReturn(true);
    when(todoService.getTodoItem(1)).thenReturn(expected);

    ResponseEntity<TodoItem> actualResponse = todoController.getTodoItem(1);

    assertEquals( HttpStatusCode.valueOf(200), actualResponse.getStatusCode());
    assertEquals(expected,actualResponse.getBody());
  }

  @Test
  void getTodoItem_FeatureTurnedOff() throws Exception {
    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled("get_todo_item")).thenReturn(false);

    ResponseEntity<TodoItem> actualResponse = todoController.getTodoItem(1);

    assertEquals( HttpStatusCode.valueOf(404),actualResponse.getStatusCode());
  }
}