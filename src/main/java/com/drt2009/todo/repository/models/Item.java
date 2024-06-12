package com.drt2009.todo.repository.models;

import com.drt2009.todo.pojo.TodoItem;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//Needed for JPA findById
@NoArgsConstructor
@Entity
@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Table(name = "items")
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String description;
  @Setter
  private boolean complete;

  public static Item convertFromTodoItem(TodoItem todoItem) {
    return Item.builder().description(todoItem.getDescription()).complete(todoItem.isComplete())
        .build();
  }

  public static TodoItem convertToTodoItem(Item item) {
    return TodoItem.builder().id(item.getId()).description(item.getDescription())
        .complete(item.isComplete()).build();
  }
}
