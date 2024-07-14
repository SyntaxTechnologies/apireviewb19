Feature: Api testing
  Background:
    Given a token is generated

    Scenario: create an Employee
      Given a request is prepared to create an employee
      When  A post call is made to create an employee
      Then the employee is created and the key "Message" has value "Employee Created"
      And the employee id "Employee.employee_id" is stored as a global variable


      Scenario: retrieve the created employee
        Given a request is prepared to retrieve an employee
        When a get call is made to get the employee
        Then the employee id "employee.employee_id" matches with the globally saved employee id
        And the data that is there for the created employee matches the data used to create it
        |emp_firstname|emp_lastname|emp_middle_name|emp_gender|emp_birthday|emp_status|emp_job_title|
        |moazzam        |sadiq     |ms             |Male    |1976-06-16  |permanent |QA manager   |




