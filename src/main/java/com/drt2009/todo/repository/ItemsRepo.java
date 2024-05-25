package com.drt2009.todo.repository;

import com.drt2009.todo.repository.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemsRepo extends CrudRepository<Item, Integer> {

}
