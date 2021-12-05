package ru.yandex.praktikum;

import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

public class CourierSteps extends RestAssuredClient {

    @Step ("Создание курьера")
    public ValidatableResponse create(CourierRegister courier) {
        return given()
                .spec(getBaseSpec())
                .body(courier)
                .when()
                .post("/api/v1/courier")
                .then();
    }

    @Step ("Авторизация курьера")
    public  ValidatableResponse login(CourierLoginPass courierLoginPass) {
        return given()
                .spec(getBaseSpec())
                .body(courierLoginPass)
                .when()
                .post("/api/v1/courier/login")
                .then();
    }

    @Step("Удаление курьера")
    public void delete (int courierId){
        given()
                .spec(getBaseSpec())
                .when()
                .delete("/api/v1/courier/{id}", courierId)
                .then();
    }
}
