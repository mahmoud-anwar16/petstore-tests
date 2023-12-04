# API Test Automation Framework

This framework provides automated tests for CRUD operations on the USER endpoint of the Petstore API.

## Setup Steps

1. Clone the repository.
2. Install Java and Maven.
3. Update the API base URL in `UserApiTest.java`.
4. Run `mvn clean test` to execute the tests.

## Test Data

Test data for user creation is stored in `resources/testdata/users.json`.

## Data Provider

The tests use TestNG's DataProvider to supply test data. Modify `UserApiTest.java` to add more test cases.

## Test Execution Report

After running the tests, check the `target/surefire-reports` directory for the test execution report.

## Swagger Documentation

The API is documented using Swagger. You can find more information in the Swagger documentation available
at [Petstore Swagger Documentation](http://petstore.swagger.io/v3).
