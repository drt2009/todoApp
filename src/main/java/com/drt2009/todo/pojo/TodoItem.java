package com.drt2009.todo.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
public class TodoItem {
  private Integer id;
  private String description;

}
