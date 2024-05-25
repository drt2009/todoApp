package com.drt2009.todo.impl;

import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.repository.ItemsRepo;
import com.drt2009.todo.repository.models.Item;
import com.drt2009.todo.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

  private final ItemsRepo itemsRepo;

  @Override
  public TodoItem createTodoItem(TodoItem todoItem) {
    Item item = itemsRepo.save(Item.convertFromTodoItem(todoItem));
    return Item.convertToTodoItem(item);
  }
}
