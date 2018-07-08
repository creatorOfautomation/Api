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

public class WeatherByCityId extends Helpers {


    private String idReqParamName = "id";
    private String appidReqParamName = "appid";
    private Map<String, String> paramValue = new HashMap<>();
    private String[] excelData = readFromExcel("weatherDataReq", 5);
    //Expecting value of parameters in response
    private String couunryValue = "UA";
    private String nameValue = "Lutsk";
    private String idValue = "702569";
    private String messageValue = "city not found";
    //Output param name
    private String messageRespParamJsonPath = "message";


    @BeforeClass
    public void putDataInMap() {

        paramValue.put(idReqParamName, excelData[1]);
        paramValue.put(appidReqParamName, excelData[2]);
    }

    @Test
    public void testGetWeatherVyCityID() {

        Response response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, appidReqParamName, paramValue.get(appidReqParamName), idReqParamName, paramValue.get(idReqParamName));
        response.then().contentType(PropertiesCollection.CONTENT_RESPONSE_TYPE).assertThat().statusCode(200);
        WeatherForCity weather = response.as(WeatherForCity.class, ObjectMapperType.GSON);
        softAssertEquals(String.valueOf(weather.getSys().getCountry()), couunryValue);
        softAssertEquals(String.valueOf(weather.getName()), nameValue);
        softAssertEquals(String.valueOf(weather.getId()), idValue);
        assertAll();
    }

    @Test
    public void testWrongCityId() {
        Response response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, appidReqParamName, paramValue.get(appidReqParamName), idReqParamName, paramValue.get(idReqParamName) + "54");
        response.then().contentType(PropertiesCollection.CONTENT_RESPONSE_TYPE).statusCode(404);
        softAssertContainsStringJsonPath(response, messageRespParamJsonPath, messageValue);
        assertAll();
    }

    @Test
    public void testNotAuthorized() {
        Response response = getResponse(Endpoints.GET_WEATHER_FOR_CITY, appidReqParamName, paramValue.get(appidReqParamName) + 7,
                idReqParamName, paramValue.get(idReqParamName));
        response.then().assertThat().statusCode(401);

    }
}
