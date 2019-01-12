package properties;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.assertj.core.api.SoftAssertions;
import org.json.simple.JSONObject;
import org.testng.asserts.SoftAssert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;


public class Helpers extends BaseClass {

    private SoftAssert softAssert = new SoftAssert();
    private XSSFWorkbook workbook;
    private Logger log = Logger.getLogger(Helpers.class);
    private String breakLine = System.lineSeparator();

    @Step("Making assertion: Checking Parameter: {jsonPath} Expected: {expectedValue} ")
    public Helpers softAssertEqualsByJsonPath(final Response response, final String jsonPath, final String expectedValue) {

        softAssert.assertEquals(response.path(jsonPath).toString().trim(), expectedValue.trim());
        return this;
    }

    @Step("Making assertion: Checking Parameter: {jsonPath} Expected: {expectedValue} ")
    public Helpers softAssertContainsStringJsonPath(final Response response, final String jsonPath, final String expectedValue) {

        String actualResult = response.path(jsonPath).toString().trim();

        softAssert.assertTrue(actualResult.contains(expectedValue), "ERROR! Failed assertion. " + breakLine + "Expected: " +
                expectedValue + breakLine + " but it does not contain " + breakLine + "Actual : " + actualResult);
        return this;

    }

    public Helpers softAssertEquals(final String actValue, String expValue, SoftAssertions assertions) {

        assertions.assertThat(actValue.trim()).isEqualTo(expValue.trim());
        return this;
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
            log.error("ERROR! to load Excel file! File not found!!! " + e.getMessage());

        }
        if (excelFile != null) {
            log.debug("Excel file successfully loaded");
        }
        try {

            workbook = new XSSFWorkbook(excelFile);
        } catch (IOException e) {
            log.error("ERROR! IOException occurred!!!" + e.getMessage());
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


    @Step("Make POST query with url {url}")
    public Response post(String url, RequestSpecification request) {

        log.debug("Try to make request on url: " + url + " Request is: " + request);
        log.debug(request.log().all());
        return request.post(url);
    }

    @Step("Make PUT query with url {url}")
    public Response put(String url, RequestSpecification request) {

        log.debug("Try to make request on url: " + url + " Request is: " + request);
        log.debug(request.log().all());
        return request.put(url);
    }


    public String randomPureString(int length) {
        final String data = "qwertyuiopasdfghjklzxcvbnm";

        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i <= length; i++)


            sb.append(data.charAt(random.nextInt(data.length())));
        return sb.toString();

    }

    public String getUnicId() {

        return "" + System.currentTimeMillis();
    }

    public void putParam(String key, String value, JSONObject jsonObject) {
        log.debug(String.format("Put key: '%s' value: '%s'", key, value));
        jsonObject.put(key, value);

    }

    public String toJSON(JSONObject jsonObject) {

        String string = jsonObject.toJSONString();
        log.debug("Convert params to JSON: " + string);

        return string;
    }

    public static void main(String[] args) {
        Helpers helpers = new Helpers();
        System.out.println(helpers.getUnicId());


    }
}
