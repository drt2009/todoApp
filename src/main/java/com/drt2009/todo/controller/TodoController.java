package com.drt2009.todo.controller;


import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.service.TodoService;
import com.flagsmith.FlagsmithClient;
import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


  @GetMapping()
  public ResponseEntity<List<TodoItem>> getAllTodoItems() throws FlagsmithClientError {
    Flags flags = flagsmithClient.getEnvironmentFlags();
    boolean isFeatureTurnedOn = flags.isFeatureEnabled("get_all_todo_items");

    if(isFeatureTurnedOn) {
      List<TodoItem> responseTodoItem = todoService.getAllTodoItems();
      return ResponseEntity.ok(responseTodoItem);
    }
    else {
      return ResponseEntity.notFound().build();
    }
  }

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

  @GetMapping("/{id}")
  public ResponseEntity<TodoItem> getTodoItem(@PathVariable int id) throws FlagsmithClientError {
    Flags flags = flagsmithClient.getEnvironmentFlags();
    boolean isFeatureTurnedOn = flags.isFeatureEnabled("get_todo_item");

    if(isFeatureTurnedOn) {
      TodoItem responseTodoItem = todoService.getTodoItem(id);
      return ResponseEntity.ok(responseTodoItem);
    }
    else {
      return ResponseEntity.notFound().build();
    }
  }

}
