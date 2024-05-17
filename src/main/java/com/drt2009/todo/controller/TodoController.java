package com.drt2009.todo.controller;


import com.drt2009.todo.pojo.TodoItem;
import com.flagsmith.FlagsmithClient;
import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

  private final FlagsmithClient flagsmithClient;

  @PostMapping()
  public ResponseEntity<Void> createTodoItem(TodoItem todoItem) throws FlagsmithClientError {
    Flags flags = flagsmithClient.getEnvironmentFlags();
    boolean isFeatureTurnedOn = flags.isFeatureEnabled("create_todo_item");

    if(isFeatureTurnedOn)
      return ResponseEntity.status(201).build();
    else
      return ResponseEntity.notFound().build();
  }
}
