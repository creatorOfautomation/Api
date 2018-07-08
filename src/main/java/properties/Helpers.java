package properties;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.json.simple.JSONObject;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static io.restassured.RestAssured.given;

public class Helpers extends BaseClass {

    private SoftAssert softAssert = new SoftAssert();
    private XSSFWorkbook workbook;
    private Logger log = Logger.getLogger(Helpers.class);
    private String breakLine = System.lineSeparator();
    private JSONObject reqParam = new JSONObject();
    //  private RequestSpecification request = RestAssured.given();


    public Response getResponse(String url, String param1, String value1, String param2, String value2, String param3, String value3, String param4, String value4) {
        log.debug("Try to make get request :" + url + " with parameters " + param1 + " " + value1 + " " + param2 + " " + value2
                + " " + param3 + " " + value3 + " " + param4 + " " + value4);
        return given()
                .param(param1, value1)
                .param(param2, value2)
                .param(param3, value3)
                .param(param4, value4)
                .get(url);

    }

    public Response getResponse(String url, String param1, String value1, String param2, String value2, String param3, String value3) {
        log.debug("Try to make get request :" + url + " with parameters " + param1 + " " + value1 + " " + param2 + " " + value2
                + " " + param3 + " " + value3);
        return given()
                .param(param1, value1)
                .param(param2, value2)
                .param(param3, value3)
                .get(url);
    }

    @Step("Getting response of Endpoint: {url} with param and value: {param1} : {value1} AND    {param2} : {value2}")
    public Response getResponse(String url, String param1, String value1, String param2, String value2) {

        log.debug("Try to make get request :" + url + " with parameters " + param1 + " " + value1 + " " + param2 + " " + value2);
        Response response = given()
                .param(param1, value1)
                .param(param2, value2)
                .get(url);
        log.debug(response.then().log().all());
        log.debug("The response is " + response.getBody());

        return response;

    }

    public Response getResponse(String url, String param1, String value1) {
        log.debug("Try to make get request :" + url + " with parameters " + param1 + " " + value1);
        return given()
                .param(param1, value1)
                .get(url);

    }

    public Response getResponse(String url) {

        log.debug("Try to make get request :" + url + " without parameters ");
        Response response = given()
                .get(url);
        log.debug("The response is " + response.getBody());
        return response;

    }

    @Step("Making assertion: Checking Parameter: {jsonPath} Expected: {expectedValue} ")
    public void softAssertEqualsByJsonPath(final Response response, final String jsonPath, final String expectedValue) {

        softAssert.assertEquals(response.path(jsonPath).toString().trim(), expectedValue.trim());
    }

    @Step("Making assertion: Checking Parameter: {jsonPath} Expected: {expectedValue} ")
    public void softAssertContainsStringJsonPath(final Response response, final String jsonPath, final String expectedValue) {

        String actualResult = response.path(jsonPath).toString().trim();

        softAssert.assertTrue(actualResult.contains(expectedValue), "ERROR! Failed assertion. " + breakLine + "Expected: " +
                expectedValue + breakLine + " but it does not contain " + breakLine + "Actual : " + actualResult);

    }

    public void softAssertEquals(final String actValue, String expValue) {
        softAssert.assertEquals(actValue.trim(), expValue.trim());
    }

    public void assertAll() {
        softAssert.assertAll();
    }

    @Step
    public void logAllure(String logMessage) {
        //Method for log to allure
    }

    public String[] readFromExcel(final String fileName, final int cell) {
        FileInputStream excelFile = null;
        try {

            log.debug("Try to load ExcelFile for read data");
            excelFile = new FileInputStream(PropertiesCollection.EXCEL_DATA_PATH + fileName + ".xlsx");

        } catch (FileNotFoundException e) {
            log.error("ERROR to load Excel file! File not found!!! " + e.getMessage());

        }
        if (excelFile != null) {
            log.debug("Excel file successfully loaded");
        }
        try {

            workbook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            log.error("ERROR! IOException occured!!!" + e.getMessage());
        }
        XSSFSheet sheet = workbook.getSheetAt(0);
        int rows = sheet.getLastRowNum() + 1;
        String[] data = new String[rows];
        try {
            try {
                for (int i = 0; i < rows; i++) {

                    data[i] = sheet.getRow(i).getCell(cell).getStringCellValue();
                }

            } catch (IndexOutOfBoundsException e) {
                log.error("ERROR! Index of bound Exception occurred while trying to read data from Excel file " + fileName + " " + e.getMessage());
            }


        } catch (NullPointerException e) {
            log.error("ERROR! Got NullPointerException. There are no data in Excel file");
        }
        return data;

    }

    public Helpers setJsonParam(String key, String value) {
        log.debug("Try to put json param. The key is : " + key + " The value is: " + value);
        reqParam.put(key, value);
        return this;
    }

    public Helpers setJsonParam(String key, int value) {
        log.debug("Try to put json param. The key is : " + key + " The value is: " + value);
        reqParam.put(key, value);
        return this;
    }

    public String putParam() {
        return reqParam.toJSONString();
    }

    @Step("Make POST query with url {url}")
    public Response post(String url, RequestSpecification request) {

        log.debug("Try to make request on url: " + url + " Request is: " + request);
        return request.post(url);
    }


}
