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

    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled("create_todo_item")).thenReturn(true);
    when(todoService.createTodoItem(input)).thenReturn(TodoItem.builder().description("testDescription").id(1).build());

    ResponseEntity<TodoItem> actualResponse = todoController.createTodoItem(input);

    assertEquals(actualResponse.getStatusCode(), HttpStatusCode.valueOf(200));
  }

  @Test
  void createTodoItem_FeatureTurnedOff() throws Exception {
    when(flagsmithClient.getEnvironmentFlags()).thenReturn(flags);
    when(flags.isFeatureEnabled("create_todo_item")).thenReturn(false);

    ResponseEntity<TodoItem> actualResponse = todoController.createTodoItem(
        TodoItem.builder().build());

    assertEquals(actualResponse.getStatusCode(), HttpStatusCode.valueOf(404));
  }
}