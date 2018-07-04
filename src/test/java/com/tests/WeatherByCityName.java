package com.tests;

import POJOData.weatherbycity.WeatherForCity;
import endpoints.Endpoints;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import properties.Helpers;
import properties.PropertiesCollection;
import java.util.HashMap;
import java.util.Map;

public class WeatherByCityName extends Helpers {

    //Name of response parameters
    private String nameRespParamJsonPath = "name";
    private String idRespParamJsonPath = "id";
    private String codeRespParamJsonPath = "cod";
    private String countryRespParamJsonPath = "sys.country";
    private String messageRespParamJsonPath = "message";
    //Name of request parameters
    private String qReqParamName = "q";
    private String appidReqParamName = "appid";
    //Expecting value of parameters in response
    private String nameOfCityValue = "Kiev";
    private String codeValue = "200";
    private String idValue = "703448";
    private String countryValue = "UA";
    private String messageNotAuthValue = "Invalid API key.";
    private String messageWrongCityValue = "city not found";
    private String[] excelDataParametersReq = readFromExcel("weatherDataReq", 2);
    private Map<String, String> paramValue = new HashMap<String, String>();

    @BeforeClass
    public void putData() {
        paramValue.put(qReqParamName, excelDataParametersReq[1]);
        paramValue.put(appidReqParamName, excelDataParametersReq[2]);

    }

    @Test
    public void testGetWeatherByCityName() {

        Response response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, appidReqParamName, paramValue.get(appidReqParamName), qReqParamName, paramValue.get(qReqParamName));
        response.then().contentType(PropertiesCollection.CONTENT_RESPONSE_TYPE).assertThat().statusCode(200);
        WeatherForCity weather = response.as(WeatherForCity.class, ObjectMapperType.GSON);
        /*softAssertEqualsByJsonPath(response, weather.getName(), nameOfCityValue);
        softAssertEqualsByJsonPath(response, String.valueOf(weather.getId()), idValue);
        softAssertEqualsByJsonPath(response, String.valueOf(weather.getCod()), codeValue);
        softAssertEqualsByJsonPath(response, weather.getSys().getCountry(), countryValue);*/
        softAssertEquals(weather.getName(), nameOfCityValue);
        softAssertEquals(String.valueOf(weather.getId()), idValue);
        softAssertEquals(String.valueOf(weather.getCod()), codeValue);
        softAssertEquals(weather.getSys().getCountry(), countryValue);
        assertAll();
    }

    @Test
    public void testNotAuthorizedByCityName() {
        Response response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, appidReqParamName, paramValue.get(appidReqParamName) + "e", qReqParamName, paramValue.get(qReqParamName));
        response.then()
                .contentType(PropertiesCollection.CONTENT_RESPONSE_TYPE)
                .assertThat()
                .statusCode(401);
        softAssertContainsStringJsonPath(response, messageRespParamJsonPath, messageNotAuthValue);
        softAssertEqualsByJsonPath(response, codeRespParamJsonPath, "401");
        assertAll();

    }

    @Test
    public void testWrongCityParam() {

        Response response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, qReqParamName, "Kie", appidReqParamName, paramValue.get(appidReqParamName));
        response.then()
                .contentType(PropertiesCollection.CONTENT_RESPONSE_TYPE)
                .statusCode(404);
        softAssertContainsStringJsonPath(response, messageRespParamJsonPath, messageWrongCityValue);
        softAssertEqualsByJsonPath(response, codeRespParamJsonPath, "404");
        assertAll();
    }

    @Test
    public void testManyInputData() {

        String[] qValue = readFromExcel("weatherFullData", 0);
        String[] cityResponse = readFromExcel("weatherFullData", 1);
        Response response;

        for (int i = 1; i < 7; i++) {
            response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, appidReqParamName, paramValue.get(appidReqParamName), qReqParamName, qValue[i]);
            response.then()
                    .statusCode(200);
            softAssertEqualsByJsonPath(response, nameRespParamJsonPath, cityResponse[i]);

        }
        assertAll();
    }

    @Test
    public void testCheckResponseBody() {

        Response response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, appidReqParamName, paramValue.get(appidReqParamName), qReqParamName, paramValue.get(qReqParamName));
        WeatherForCity weather = response.as(WeatherForCity.class, ObjectMapperType.GSON);
        weather.getCoord().getLat();

    }

}
