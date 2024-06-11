package com.drt2009.todo.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.drt2009.todo.exceptions.ItemNotFoundException;
import com.drt2009.todo.impl.TodoServiceImpl;
import com.drt2009.todo.pojo.TodoItem;
import com.drt2009.todo.repository.ItemsRepo;
import com.drt2009.todo.repository.models.Item;
import java.util.List;
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

  @Test
  void getAllTodoItems_happyPath(){
    TodoItem expected1 = TodoItem.builder().id(1).description("Test Todo Item").build();
    TodoItem expected2 = TodoItem.builder().id(2).description("Test Todo Item 2").build();
    List<TodoItem> expected = List.of(expected1,expected2);

    Item returnItem1  = Item.builder().id(1).description("Test Todo Item").build();
    Item returnItem2  = Item.builder().id(2).description("Test Todo Item 2").build();
    List<Item> returnList = List.of(returnItem1,returnItem2);

    when(itemsRepo.findAll()).thenReturn(returnList);

    List<TodoItem> actual = todoService.getAllTodoItems();

    assertEquals(expected,actual);
  }

}