package com.woact.dolplads.quiz.rest.resource.category;

import com.javaee2.dolplads.utils.JBossUtil;
import com.woact.dolplads.quiz.backend.entity.Category;
import com.woact.dolplads.quiz.rest.dto.category.CategoryDto;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.put;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 14/11/2016.
 */
public class CategoryResourceTestIT {
    private final String CATEGORIES = "http://localhost:8080/quiz/api/categories";

    @BeforeClass
    public static void init() {
        JBossUtil.waitForJBoss(10);
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/quiz/api/categories";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }


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
        int size = foundCategories().length;
        double rand = new Random().nextDouble();
        CategoryDto dto = new CategoryDto(null, "sometext " + rand);
        Long id = postNewCategory(dto);

        assertEquals(size + 1, foundCategories().length);

        CategoryDto found = given().accept(ContentType.JSON)
                .get(id.toString())
                .then()
                .statusCode(200)
                .extract().as(CategoryDto.class);

        rand = new Random().nextDouble();
        found.categoryText = "" + rand;

        Map<String, String> car = new HashMap<>();
        car.put("id", "" + found.id);
        car.put("categoryText", found.categoryText);

        given().contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(car)
                .put("/id/{id}")
                .then()
                .statusCode(204);


        assertEquals(found.categoryText, "" + rand);
        //was the PUT fine?
        get("" + id).then().body("categoryText", is(found.categoryText));

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
                .post()
                .then()
                .statusCode(200)
                .extract().as(Long.class);
    }
}