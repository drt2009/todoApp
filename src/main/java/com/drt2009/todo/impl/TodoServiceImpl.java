package com.drt2009.todo.impl;

import com.drt2009.todo.exceptions.ItemNotFoundException;
import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.repository.ItemsRepo;
import com.drt2009.todo.repository.models.Item;
import com.drt2009.todo.service.TodoService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Streamable;
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

  @Override
  public TodoItem getTodoItem(int id) {
    Optional<Item> itemOptional = itemsRepo.findById(id);

    Item item = itemOptional.orElseThrow(() -> new ItemNotFoundException(id));

    return Item.convertToTodoItem(item);
  }

  @Override
  public List<TodoItem> getAllTodoItems() {
    List<Item> itemList = Streamable.of(itemsRepo.findAll()).toList();

    return itemList.stream().map(Item::convertToTodoItem).toList();
  }

  @Override
  public TodoItem updateTodoCompleteStatus(int id, boolean completionStatus) {
    Optional<Item> itemOptional = itemsRepo.findById(id);
    Item item = itemOptional.orElseThrow(() -> new ItemNotFoundException(id));
    item.setComplete(completionStatus);
    Item itemAfterSave = itemsRepo.save(item);
    return Item.convertToTodoItem(itemAfterSave);
  }
}
