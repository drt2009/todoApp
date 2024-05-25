Feature: Todo Item

  Scenario: Create a todo item
    Given I have a todo item to submit
    And the feature flag for "create_todo_item" is turned on
    When I submit the item to the controller
    Then a 200 response is returned
    And a todo item is returned with an id