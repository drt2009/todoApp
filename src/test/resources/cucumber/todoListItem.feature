Feature: Todo Item

  Scenario: Create a todo item
    Given I have a todo item to submit
    And the feature flag for "create_todo_item" is turned on
    When I submit the item to the controller
    Then a 200 response is returned
    And a todo item is returned with an id
    And clean up after test

  Scenario: Get a todo item
    Given There is a todo item already created
    And the feature flag for "get_todo_item" is turned on
    When I request the todo item
    Then a 200 response is returned
    And a todo item is returned with that id
    And clean up after test

  Scenario: Get All Todo
    Given There is a todo item already created
    And There is a todo item already created
    And the feature flag for "get_all_todo_items" is turned on
    When I request to get all of the todo items
    Then a 200 response is returned
    And at least 2 todo items are returned
    And clean up after test

  Scenario: Complete a todo item
    Given There is a todo item already created
    And the feature flag for "update_todo_complete_status" is turned on
    When I complete a todo item
    Then a 200 response is returned
    And a todo item is returned with that id
    And that todo item is in completed status
    And clean up after test
