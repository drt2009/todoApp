package com.drt2009.todo.exceptions;

import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

public class ItemNotFoundException extends HttpClientErrorException {

  public ItemNotFoundException(int itemId) {
    super(HttpStatusCode.valueOf(404)
        , String.format("Requested Item Id of %s was not found", itemId));
  }

}
