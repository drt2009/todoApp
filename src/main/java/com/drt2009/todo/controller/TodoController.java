package com.drt2009.todo.controller;


import com.flagsmith.FlagsmithClient;
import com.flagsmith.exceptions.FlagsmithClientError;
import com.flagsmith.models.Flags;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController {

  private final FlagsmithClient flagsmithClient;

  @PostMapping()
  public String createTodoItem() throws FlagsmithClientError {
    Flags flags = flagsmithClient.getEnvironmentFlags();
    boolean isFeatureTurnedOn = flags.isFeatureEnabled("create_todo_item");

    if(isFeatureTurnedOn)
      return "Hello World";
    else
      throw new ResponseStatusException(
          HttpStatus.NOT_FOUND
      );
  }
}
