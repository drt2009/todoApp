package com.drt2009.todo.service;

import com.drt2009.todo.pojo.TodoItem;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public interface TodoService {

  TodoItem createTodoItem(TodoItem todoItem);

  TodoItem getTodoItem(int id);

  List<TodoItem> getAllTodoItems();

  TodoItem updateTodoCompleteStatus(int id, boolean status);
}
