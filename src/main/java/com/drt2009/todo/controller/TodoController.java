package com.drt2009.todo.controller;


import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.request.pojo.CreateTodoItemRequest;
import com.drt2009.todo.service.TodoService;
import com.flagsmith.FlagsmithClient;
import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  public ResponseEntity<List<TodoItem>> getAllTodoItems() {
    if (isFeatureTurnedOn("get_all_todo_items")) {
      List<TodoItem> responseTodoItem = todoService.getAllTodoItems();
      return ResponseEntity.ok(responseTodoItem);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @PostMapping()
  public ResponseEntity<TodoItem> createTodoItem(CreateTodoItemRequest createTodoItemRequest) {
    if (isFeatureTurnedOn("create_todo_item")) {
      TodoItem responseTodoItem = todoService.createTodoItem(
          TodoItem.builder().description(createTodoItemRequest.getDescription()).build());
      return ResponseEntity.ok(responseTodoItem);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<TodoItem> getTodoItem(@PathVariable int id) {
    if (isFeatureTurnedOn("get_todo_item")) {
      TodoItem responseTodoItem = todoService.getTodoItem(id);
      return ResponseEntity.ok(responseTodoItem);
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @PatchMapping("/{id}/status/{status}")
  public ResponseEntity<TodoItem> updateTodoStatus(@PathVariable int id,
      @PathVariable Boolean status) {
    if (isFeatureTurnedOn("update_todo_complete_status")) {
      TodoItem responseTodoItem = todoService.updateTodoCompleteStatus(id, status);
      return ResponseEntity.ok(responseTodoItem);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

  private boolean isFeatureTurnedOn(String featureFlag) {
    Flags flags = null;
    try {
      flags = flagsmithClient.getEnvironmentFlags();
      return flags.isFeatureEnabled(featureFlag);
    } catch (FlagsmithClientError e) {
      log.error("Issue finding info about feature flag {}", featureFlag, e);
      return false;
    }
  }
}
