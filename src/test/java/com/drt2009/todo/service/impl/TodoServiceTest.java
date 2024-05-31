package com.drt2009.todo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.drt2009.todo.exceptions.ItemNotFoundException;
import com.drt2009.todo.impl.TodoServiceImpl;
import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.repository.ItemsRepo;
import com.drt2009.todo.repository.models.Item;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class TodoServiceTest {
  @Mock
  private ItemsRepo itemsRepo;

  @InjectMocks
  private TodoServiceImpl todoService;

  @Test
  void createTodoItem_happyPath(){
    TodoItem testItem = TodoItem.builder().description("Test Todo Item").build();
    TodoItem expected = TodoItem.builder().id(1).description("Test Todo Item").build();
    Item returnItem = Item.builder().id(1).description("Test Todo Item").build();

    when(itemsRepo.save(any())).thenReturn(returnItem);

    TodoItem actual = todoService.createTodoItem(testItem);

    assertEquals(expected,actual);
  };

  @Test
  void getTodoItem_happyPath(){
    TodoItem expected = TodoItem.builder().id(1).description("Test Todo Item").build();
    Item returnItem  = Item.builder().id(1).description("Test Todo Item").build();

    when(itemsRepo.findById(1)).thenReturn(Optional.of(returnItem));

    TodoItem actual = todoService.getTodoItem(1);

    assertEquals(expected,actual);
  }

  @Test
  void getTodoItem_itemNotFound(){
    when(itemsRepo.findById(1)).thenReturn(Optional.empty());
    ItemNotFoundException exception = assertThrows(ItemNotFoundException.class, ()-> todoService.getTodoItem(1));
    assertEquals("404 Requested Item Id of 1 was not found", exception.getMessage());

  }


}