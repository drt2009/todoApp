package com.drt2009.todo.service;

import com.drt2009.todo.pojo.TodoItem;
import org.springframework.stereotype.Component;

@Component
public interface TodoService {

  TodoItem createTodoItem(TodoItem todoItem);
}
