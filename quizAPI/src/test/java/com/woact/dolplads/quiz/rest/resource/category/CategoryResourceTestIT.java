package com.woact.dolplads.quiz.rest.resource.category;

import com.woact.dolplads.quiz.backend.entity.Category;
import com.woact.dolplads.quiz.rest.dto.category.CategoryDto;
import com.woact.dolplads.quiz.rest.weatherObjects.WeatherData;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 14/11/2016.
 */
public class CategoryResourceTestIT {
    //JBossUtil.waitForJBoss(10);

    @Before
    public void init() {
        // RestAssured configs shared by all the tests
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/quiz/api/categories";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    private final String CATEGORIES = "http://localhost:8080/quiz/api/categories";

    @Test
    public void createAndGetCategory() throws Exception {
        int resultSize = foundCategories().length;

        CategoryDto dto = new CategoryDto(null, "some text " + new Random().nextDouble());
        Long id = postNewCategory(dto);

        Category created = Arrays.stream(foundCategories())
                .filter(category -> id.equals(category.getId())).collect(Collectors.toList()).get(0);

        assertEquals(created.getCategoryText(), dto.categoryText);
        assertEquals(resultSize + 1, foundCategories().length);
    }

    @Test
    public void updateCategory() throws Exception {
        String testText = "some text " + getARandom();
        CategoryDto dto = new CategoryDto(null, testText);
        Long id = postNewCategory(dto);
        get("/id/" + id).then().body("categoryText", is(testText));

        testText = getARandom();

        given().contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(new CategoryDto(id, testText))
                .put("/id/{id}")
                .then()
                .statusCode(204);

        get("/id/" + id).then().body("categoryText", is(testText));
    }

    @Test
    public void getById() throws Exception {
        CategoryDto dto = new CategoryDto(null, "some text " + new Random().nextDouble());
        Long id = postNewCategory(dto);
        CategoryDto created = get("/id/" + id)
                .then()
                .statusCode(200)
                .extract().as(CategoryDto.class);

        assertEquals(dto.categoryText, created.categoryText);
    }

    @Test
    public void getCategoriesAssociatedWithQuiz() throws Exception {

    }
    @Test
    public void getSubSubCategoriesAssociatedWithQuiz() throws Exception {

    }

    @Test
    public void getSubCategoriesOfParentCategory() throws Exception {

    }

    private Category[] foundCategories() {
        return given().accept(ContentType.JSON)
                .get()
                .then()
                .statusCode(200)
                .extract().as(Category[].class);
    }

    private Long postNewCategory(CategoryDto dto) {
        return given().contentType(ContentType.JSON)
                .body(dto)
                .post(CATEGORIES)
                .then()
                .statusCode(200)
                .extract().as(Long.class);
    }


    /**
     * just tested something!
     */
    private void justForTheTest() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://www.yr.no/place/Ocean/58.96455_9.87061/varsel.xml");
        target = client.target("http://api.met.no/weatherapi/oceanforecast/0.9/?lat=58.96455;lon=9.87061");
        Response response = target.request().get();

        String output = response.readEntity(String.class);
        //RootObject output = response.readEntity(RootObject.class);
        //System.out.println(output);


        System.out.println(response.getMediaType().toString());
        System.out.println(response.getLength());

        System.out.println("res from YR");

        JAXBContext jaxbContext = JAXBContext.newInstance(WeatherData.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        WeatherData x = (WeatherData) jaxbUnmarshaller.unmarshal(new URL("http://www.yr.no/place/Ocean/58.96455_9.87061/varsel.xml"));
        //System.out.println(x.getForecast().getText().getLocation().getTime().get(1).getBody());

        String soapmessageString = output;
        JSONObject soapDatainJsonObject = XML.toJSONObject(soapmessageString);
        //System.out.println(soapDatainJsonObject);
        JSONArray objects = soapDatainJsonObject.getJSONObject("mox:Forecasts").getJSONArray("mox:forecast");
        System.out.println(objects);
        //soapDatainJsonObject.getJSONArray("mox:forecast");


        //JAXBContext jaxbContext2 = JAXBContext.newInstance(RootObject.class);
        //Unmarshaller jaxbUnmarshaller2 = jaxbContext2.createUnmarshaller();
        //RootObject x2 = (RootObject) jaxbUnmarshaller2.unmarshal(new URL("http://api.met.no/weatherapi/oceanforecast/0.9/?lat=58.96455;lon=9.87061"));

    }

    public String getARandom() {
        return "" + new Random().nextDouble();
    }
}