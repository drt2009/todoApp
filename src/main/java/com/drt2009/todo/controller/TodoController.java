package com.drt2009.todo.controller;


import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.service.TodoService;
import com.flagsmith.FlagsmithClient;
import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

  private final FlagsmithClient flagsmithClient;
  private final TodoService todoService;

  @PostMapping()
  public ResponseEntity<TodoItem> createTodoItem(TodoItem todoItem) throws FlagsmithClientError {
    Flags flags = flagsmithClient.getEnvironmentFlags();
    boolean isFeatureTurnedOn = flags.isFeatureEnabled("create_todo_item");

    if(isFeatureTurnedOn) {
      TodoItem responseTodoItem = todoService.createTodoItem(todoItem);
      return ResponseEntity.ok(responseTodoItem);
    }
    else {
      return ResponseEntity.notFound().build();
    }
  }
}
