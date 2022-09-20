package com.scripted.apistepdefs;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.*;


import com.scripted.utils.WebServiceEnvironment;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.aeonbits.owner.ConfigFactory;
import org.apache.http.client.ClientProtocolException;
import org.testng.Assert;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.module.jsv.JsonSchemaValidatorSettings.settings;

import com.scripted.api.RequestParams;
import com.scripted.api.RestAssuredWrapper;
import com.scripted.dataload.ExcelConnector;
import com.scripted.dataload.PropertyDriver;
import com.scripted.dataload.TestDataFactory;
import com.scripted.dataload.TestDataObject;
import com.scripted.roi.ROIExeTime;
import org.json.JSONObject;
import io.cucumber.core.api.Scenario;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;

import static org.hamcrest.Matchers.*;

//@Listeners({ AllureListener.class })
public class stepdefinitions {
    RequestParams Attwrapper = new RequestParams();
    RestAssuredWrapper raWrapper = new RestAssuredWrapper();
    private Scenario scenario;
    public String setComplexity;
    public Collection<String> tags;
    public String filename;
    public static Map<String, String> dynamicBodyValues = new HashMap<>();
    private Map<String, String> params = new HashMap<String, String>();
    PropertyDriver propDriver = new PropertyDriver();
    WebServiceEnvironment testWebServiceEnvironment;

    @Before("@High")
    public void setComplexityHigh(Scenario scenario) {
        this.scenario = scenario;
        setComplexity = "High";
    }

    @Before("@Medium")
    public void setComplexityMedium(Scenario scenario) {
        this.scenario = scenario;
        setComplexity = "Medium";
    }

    @Before("@Low")
    public void setComplexityLow(Scenario scenario) {
        this.scenario = scenario;
        setComplexity = "Low";
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        ROIExeTime.startTime();
        this.scenario = scenario;
        tags = scenario.getSourceTagNames();
        for (String tag : tags) {
            if (tag.contains("data-")) {
                String[] tagSplit = tag.split("data-");
                filename = "./src/main/resources/WebServices/DataFiles/" + tagSplit[1] + ".xlsx";
            }
        }
        testWebServiceEnvironment = ConfigFactory.create(WebServiceEnvironment.class);
        raWrapper.setBaseURI(testWebServiceEnvironment.url());
    }

    @After
    public void afterScenario(Scenario scenario) {
        try {
            byte[] responseData = raWrapper.convertFileToByte();
            scenario.embed(responseData, "text/plain");
            String totalhours = ROIExeTime.endTime();
            // ROIAPIrequest.callROIApi(setComplexity,totalhours);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Given("I have sample {string} API")
    public void i_have_sample_api(String apiName) {
    }

    @And("I send a {string} Request with {string} and {string}")
    public void api_json_request_in_something(String strMethod, String strPropFileName, String apijsonRequest) throws IllegalArgumentException {
        Attwrapper.setJsonbody(apijsonRequest);
        raWrapper.setAPIFileProName(strPropFileName + ".properties");
        RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
        raWrapper.sendRequest(strMethod, reqSpec);
    }

    @When("I send a {string} urlencoded Request with {string} and {string}")
    public void api_urlencoded_request_in_something(String strMethod, String strPropFileName, String apiUrlencodedRequest) {
        Attwrapper.setFormParams(apiUrlencodedRequest, dynamicBodyValues);
        raWrapper.setAPIFileProName(strPropFileName + ".properties");
        RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
        raWrapper.sendRequest(strMethod, reqSpec);
    }

    @And("I define request value {string} as {string}")
    public void set_query_params(String key, String value) {
        params.put(key, value);
    }

    @When("I send a {string} Request with {string} properties")
    public void i_send_a_something_request_with_something_properties(String strMethod, String strPropFileName) {
        raWrapper.setAPIFileProName(strPropFileName + ".properties");
        RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
        raWrapper.sendRequest(strMethod, reqSpec);
    }

    @When("I send a {string} Request with {string} properties with query parameters")
    public void i_send_a_something_request_with_something_properties_with_query_param(String strMethod, String strPropFileName) {
        raWrapper.setAPIFileProName(strPropFileName + ".properties");
        RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
        raWrapper.setGetQueryParams(params);
        raWrapper.sendRequest(strMethod, reqSpec);
    }

    @Then("I should get response code {int}")
    public void i_should_get_response_code_something(int strCode) {
        raWrapper.valResponseCode(strCode);
    }

    @And("I verify schema {string}")
    public void i_verify_schema(String schemaFilePath) {
        raWrapper.getResponse().then().body(matchesJsonSchemaInClasspath(schemaFilePath).using(settings().with().checkedValidation(false)));
    }

    @And("I verify response time less than {string}")
    public void iCheckResponseTime(String responseTime) {
        raWrapper.getResponse().then()
                .time(lessThan(Long.parseLong(responseTime)));
    }

    @And("I save value of json path {string} as a variable")
    public void save_json_path_value_as_variable(String jsonPath) {
        System.out.println("jsonPath: " + jsonPath);
        JsonPath jsonPathEvaluator = raWrapper.getResponse().jsonPath();
        dynamicBodyValues.put(jsonPath, jsonPathEvaluator.get(jsonPath));
    }

    @And("I save random email value")
    public void save_random_value_as_variable() {
        Random rand = new Random();
        int randNumber = rand.nextInt(10000);
        dynamicBodyValues.put("email", "api_test" + randNumber + "@sc.com");
    }

    @And("the response should contain:")
    public void the_response_should_contain(DataTable respTable) {
        List<Map<String, String>> resplist = respTable.asMaps(String.class, String.class);
        for (Map<String, String> stringStringMap : resplist) {
            String jsonPath = stringStringMap.get("JsonPath");
            String expVal = stringStringMap.get("ExpectedVal");

            if (expVal.matches("-?(0|[1-9]\\d*)")) {
                int intExpVal = Integer.parseInt(expVal);
                raWrapper.valJsonResponseVal(jsonPath, intExpVal);
            } else {
                raWrapper.valJsonResponseVal(jsonPath, expVal);
            }
        }
    }

    @And("the response should contain expected values in {string}")
    public void the_response_should_contain_expected_values_in_something(String sheetname) {
        String key = "";
        ExcelConnector con = new ExcelConnector(filename, sheetname);
        TestDataFactory test = new TestDataFactory();
        TestDataObject obj = test.GetData(key, con);
        LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
        for (int i = 1; i < getAllData.size(); i++) {
            Map<String, String> fRow = getAllData.get(String.valueOf(i));
            String jsonPath = fRow.get("JsonPath");
            String expVal = fRow.get("ExpectedVal");
            if (expVal.matches("-?(0|[1-9]\\d*)")) {
                int intExpVal = Integer.parseInt(expVal);
                raWrapper.valJsonResponseVal(jsonPath, intExpVal);
            } else {
                raWrapper.valJsonResponseVal(jsonPath, expVal);
            }
        }
    }

    @And("the response should not contain expected element in {string}")
    public void the_response_should_contain_expected_element_in_something(String sheetname) {
        String key = "";
        boolean sflag = true;
        ExcelConnector con = new ExcelConnector(filename, sheetname);
        TestDataFactory test = new TestDataFactory();
        TestDataObject obj = test.GetData(key, con);
        LinkedHashMap<String, Map<String, String>> getAllData = obj.getTableData();
        for (int i = 1; i < getAllData.size(); i++) {
            Map<String, String> fRow = getAllData.get(String.valueOf(i));
            String jsonpath = fRow.get("Jsonpath");
            String jsonkey = fRow.get("JsonKey");
            JSONObject jobjval = raWrapper.convertStringToJson();
            boolean flag = raWrapper.valJsonEleNotExists(jobjval, jsonpath, jsonkey);
            if (!flag) {
                this.scenario.write("Element : " + jsonkey + " is exist in the response");
                sflag = false;
            }
            // raWrapper.validateJsonKeyExistence(jsonPath);
        }
        if (!sflag) {
            Assert.fail();
        }
    }

    //    @Then("I send a {string} Request with {string} properties with dynamic parameters")
//    public void updateUrlPathWithPropertiesWithDynamicParameters(String strMethod, String strPropFileName, DataTable dataTable) {
//        raWrapper.setAPIFileProName(strPropFileName + ".properties");
//
//        List<List<String>> rows = dataTable.cells();
//        for (List<String> row : rows) {
//            String key = row.get(0);
//            String value = row.get(1);
//            dynamicBodyValues.put(key, value);
//        }
//        Attwrapper.updateUrlPath(strPropFileName, dynamicBodyValues);
//        RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
//        raWrapper.sendRequest(strMethod, reqSpec);
//    }
    @Then("I send a {string} Request with {string} properties with dynamic parameters")
    public void updateUrlPathWithPropertiesWithDynamicParameters(String strMethod, String strPropFileName) {
        raWrapper.setAPIFileProName(strPropFileName + ".properties");

        Attwrapper.updateUrlPath(strPropFileName, dynamicBodyValues);
        RequestSpecification reqSpec = raWrapper.CreateRequest(Attwrapper);
        raWrapper.sendRequest(strMethod, reqSpec);
    }
}
