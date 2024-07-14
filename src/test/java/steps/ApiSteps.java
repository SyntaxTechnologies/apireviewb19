package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import utils.Constants;
import utils.PayloadConstants;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;

public class ApiSteps {

    String BaseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    public static String token;
    RequestSpecification request;
    Response response;
    public static String employee_id;


    @Given("a token is generated")
    public void a_token_is_generated() {
        request=given().header(Constants.HEADER_CONTENT_TYPE_KEY,Constants.HEADER_CONTENT_TYPE_VALUE)
                .body(PayloadConstants.generateTokenPayload());
        response=request.when().post(Constants.GENERATE_TOKEN_URI);
        token=response.jsonPath().getString("token");
        token="Bearer "+ token;
    }
    @Given("a request is prepared to create an employee")
    public void a_request_is_prepared_to_create_an_employee() {
        request=given().header(Constants.HEADER_CONTENT_TYPE_KEY,Constants.HEADER_CONTENT_TYPE_VALUE)
                .header(Constants.HEADER_AUTHORIZATION_KEY,token)
                .body(PayloadConstants.createEmployeePayload());
    }
    @When("A post call is made to create an employee")
    public void a_post_call_is_made_to_create_an_employee() {
        response=request.when().post(Constants.CREATE_EMPLOYEE_URI);
    }
    @Then("the employee is created and the key {string} has value {string}")
    public void the_employee_is_created_and_the_key_has_value(String key, String expectedValue) {
        String actualValue = response.jsonPath().getString(key);
        Assert.assertEquals(actualValue,expectedValue);

    }
    @Then("the employee id {string} is stored as a global variable")
    public void the_employee_id_is_stored_as_a_global_variable(String key) {
        employee_id=response.jsonPath().getString(key);
        System.out.println("the employee id is "+employee_id);
    }

    //......................................................................................

    @Given("a request is prepared to retrieve an employee")
    public void a_request_is_prepared_to_retrieve_an_employee() {
        request=given().header(Constants.HEADER_CONTENT_TYPE_KEY,Constants.HEADER_CONTENT_TYPE_VALUE)
                .header(Constants.HEADER_AUTHORIZATION_KEY,token)
                .queryParam("employee_id",employee_id);
    }
    @When("a get call is made to get the employee")
    public void a_get_call_is_made_to_get_the_employee() {
        response=request.when().get(Constants.GET_ONE_EMPLOYEE_URI);
        response.prettyPrint();

    }
    @Then("the employee id {string} matches with the globally saved employee id")
    public void the_employee_id_matches_with_the_globally_saved_employee_id(String key) {
        String actualEmp_id = response.jsonPath().getString(key);
        System.out.println(actualEmp_id);
        System.out.println(employee_id);
        Assert.assertEquals(actualEmp_id,employee_id);
    }

    @Then("the data that is there for the created employee matches the data used to create it")
    public void the_data_that_is_there_for_the_created_employee_matches_the_data_used_to_create_it(io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> expectedData = dataTable.asMaps();
        Map <String, String> actualData=response.jsonPath().get("employee");

        for (Map <String,String>map:expectedData){
            Set<String> keys = map.keySet();
            for(String key:keys){
                String expectedvalue = map.get(key);
                String actualValue = actualData.get(key);
                Assert.assertEquals(expectedvalue,actualValue);

            }


        }
    }



}
