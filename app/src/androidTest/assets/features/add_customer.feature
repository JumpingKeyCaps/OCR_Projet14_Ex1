Feature: Add new customer
  As a user
  I want to add a new customer
  So that I can manage my customers efficiently

  @test
  Scenario: Adding a valid customer
    Given I am on the Add Customer screen
    When I enter "John Doe" as the name and "john.doe@example.com" as the email
    And I press the save button
    Then I should see a confirmation message "Customer added successfully"

  @test
  Scenario: Adding a customer with an invalid email
    Given I am on the Add Customer screen
    When I enter "Jane Doe" as the name and "invalid-email" as the email
    And I press the save button
    Then I should see an error message "Invalid email format"

  @test
  Scenario: Adding a customer with empty fields
    Given I am on the Add Customer screen
    When I enter "" as the name and "" as the email
    And I press the save button
    Then I should see an error message "Please fill in all fields"